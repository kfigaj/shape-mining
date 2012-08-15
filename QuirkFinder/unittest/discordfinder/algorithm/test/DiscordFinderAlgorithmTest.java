/**
 * 
 */
package discordfinder.algorithm.test;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import sax.representation.primitives.Image;
import sax.representation.primitives.SAX;
import sax.representation.primitives.TimeSeries;
import sax.utils.SAXCreator;
import sax.utils.TimeSeriesCreator;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.DiscordFinderAlgorithm;
import discordfinder.algorithm.bruteforce.ImprovedBruteForce;
import discordfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;

/**
 * 
 */
@RunWith(Parameterized.class)
public class DiscordFinderAlgorithmTest {

	private static final int TIME_SERIES_NUMBER = 20;
	private static final int TIME_SERIES_SIZE = 10;
	private static final double TIME_SERIES_STD_DERIV = 30;
	private static final double TIME_SERIES_BASE = 400;
	private static final double TIME_SERIES_WORST_CASE_STD_DERIV = 230;
	private TimeSeriesCreator prvCreator;
	private Random rand = new Random();
	private TimeSeries oddTimeSeries;
	private DiscordFinderAlgorithm algorithm;

	public DiscordFinderAlgorithmTest(DiscordFinderAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	@SuppressWarnings("unchecked")
	@Parameters
	public static Collection data() {
		System.out.println("@Parameters data()");
		Object[][] data = new Object[][] { { new ImprovedBruteForce() },
				{ new LocalSensivityHashingAlgorithm() } };
		return Arrays.asList(data);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		long seed = rand.nextLong();
		rand.setSeed(seed);

		// overwrite timeSeriesCreator by test stub
		prvCreator = SAXCreator.timeSeriesCreator;
		List<TimeSeries> timeSeriesCollection = createSimilarTimeSeries(TIME_SERIES_NUMBER - 1);
		useBaseTimeSeries(timeSeriesCollection);
	}

	@After
	public void tearDown() {
		SAXCreator.timeSeriesCreator = prvCreator;
	}

	private void useBaseTimeSeries(List<TimeSeries> timeSeriesCollection) {
		oddTimeSeries = addOddTimeSeries(timeSeriesCollection);
		SAXCreator.timeSeriesCreator = new TestTimeSeriesCreator(
				timeSeriesCollection);
	}

	private List<TimeSeries> createSimilarTimeSeries(int timeSeriesNumber) {
		List<TimeSeries> seriesCollection = new ArrayList<TimeSeries>();
		TimeSeries series = createRandomTestSeries();
		series.getTimeSeriesVector().set(0,
				TIME_SERIES_BASE + TIME_SERIES_STD_DERIV);
		int size = series.getTimeSeriesVector().size();
		for (int i = 0; i < timeSeriesNumber; ++i) {
			TimeSeries similar = (TimeSeries) series.clone();
			similar.getTimeSeriesVector().set(Math.abs(rand.nextInt()) % size,
					TIME_SERIES_BASE - 0.1 * TIME_SERIES_STD_DERIV);
			seriesCollection.add(similar);
		}

		return seriesCollection;
	}

	private TimeSeries createRandomTestSeries() {
		TimeSeries series = new TimeSeries();
		for (int j = 0; j < TIME_SERIES_SIZE; ++j) {
			series.add(TIME_SERIES_BASE + TIME_SERIES_STD_DERIV
					* rand.nextGaussian());
		}
		return series;
	}

	private TimeSeries addOddTimeSeries(List<TimeSeries> seriesCollection) {
		TimeSeries oddSeries = new TimeSeries();
		for (int j = 0; j < TIME_SERIES_SIZE; ++j) {
			oddSeries.add(TIME_SERIES_BASE + j
					* TIME_SERIES_WORST_CASE_STD_DERIV);
		}
		seriesCollection.add(rand.nextInt(seriesCollection.size()), oddSeries);
		return oddSeries;
	}

	/**
	 * Test if algorithm find the most unusual shape - the odd one.
	 */
	@Test
	public void testFindQuirk() {
		List<TimeSeries> timeSeriesCollection = createSimilarTimeSeries(TIME_SERIES_NUMBER - 1);
		useBaseTimeSeries(timeSeriesCollection);
		SAXEnhanced sax = algorithm.findDiscord(createTestSaxes(TIME_SERIES_NUMBER));
		TimeSeries actualOddTimeSeries = ((SAX)sax).getOriginal();
		System.out.println("Expected: "
				+ Arrays.toString(oddTimeSeries.getTimeSeriesArray()));
		System.out.println("Actual: "
				+ Arrays.toString(actualOddTimeSeries.getTimeSeriesArray()));
		assertSame(oddTimeSeries, actualOddTimeSeries);
	}

	@Test
	public void testFindQuirkFromTheSame() {
		List<TimeSeries> timeSeriesCollection = new ArrayList<TimeSeries>();
		TimeSeries randomTestSeries = createRandomTestSeries();
		for (int i = 0; i < TIME_SERIES_NUMBER - 1; ++i) {
			timeSeriesCollection.add((TimeSeries) randomTestSeries.clone());
		}
		useBaseTimeSeries(timeSeriesCollection);
		
		SAXEnhanced sax = algorithm.findDiscord(createTestSaxes(TIME_SERIES_NUMBER));
		assertSame(oddTimeSeries, sax.getOriginal());
	}
	
	

	private List<SAXEnhanced> createTestSaxes(int timeSeriesNumber) {
		ArrayList<Image> images = new ArrayList<Image>();
		for (int i = 0; i < TIME_SERIES_NUMBER; ++i) {
			images.add(new TestImage());
		}
		List<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
		for (Image image : images) {
			saxes.add(new SAXEnhanced(image.getName(), SAXCreator.create(image)));
		}
		return saxes;
	}

	/**
	 * Class that replaces default TimeSeriesCreator. Class creates time series
	 * for stub images.
	 * 
	 * 
	 */
	private static class TestTimeSeriesCreator extends TimeSeriesCreator {

		private Iterator<TimeSeries> iterator;

		public TestTimeSeriesCreator(Collection<TimeSeries> ts) {
			this.iterator = new ArrayList<TimeSeries>(ts).iterator();
		}

		@Override
		public TimeSeries create(Image image) {
			if (iterator.hasNext()) {
				TimeSeries timeSeries = iterator.next();
				((TestImage) image).setTimeSeries(timeSeries);
				return timeSeries;
			} else {
				return null;
			}
		}
	}

	/**
	 * Stub for Image. It holds time series that is associated with this image.
	 * 
	 * @see TestTimeSeriesCreator#create(Image)
	 * 
	 * 
	 */
	private static class TestImage extends Image {
		private TimeSeries timeSeries;

		public TimeSeries getTimeSeries() {
			return timeSeries;
		}

		public void setTimeSeries(TimeSeries timeSeries) {
			this.timeSeries = timeSeries;
		}

	}

}
