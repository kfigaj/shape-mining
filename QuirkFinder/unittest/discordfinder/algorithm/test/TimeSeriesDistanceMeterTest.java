package discordfinder.algorithm.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import sax.representation.primitives.TimeSeries;
import utils.sax.TimeSeriesDistanceMeter;

public class TimeSeriesDistanceMeterTest {

	private static final double[] TIME_SERIES_ARRAY_1 = { 1, 2, 1 };

	private static final double[] TIME_SERIES_ARRAY_2 = { 2, 2, 3 };

	private static final double TIME_SERIES_DIST = Math.sqrt(5);
	
	private static final double TIME_SERIES_ROTATION_DIST = Math.sqrt(3);

	private TimeSeries timeSeries1;

	private TimeSeries timeSeries2;

	@Before
	public void setUp() throws Exception {
		timeSeries1 = createTimeSeries(TIME_SERIES_ARRAY_1);
		timeSeries2 = createTimeSeries(TIME_SERIES_ARRAY_2);
	}

	private TimeSeries createTimeSeries(double[] array) {
		TimeSeries timeSeries = new TimeSeries();
		for (double d : array) {
			timeSeries.add(d);
		}
		return timeSeries;
	}

	@Test
	public void testEuclideanDistance() {
		double dist = TimeSeriesDistanceMeter.euclideanDistance(timeSeries1,
				timeSeries2);
		assertEquals(TIME_SERIES_DIST, dist, 0.0001);
	}
	
	@Test
	public void testEuclideanDistanceZeroDistance() {
		double dist = TimeSeriesDistanceMeter.euclideanDistance(timeSeries1,
				timeSeries1);
		assertEquals(0, dist);
	}
	
	@Test
	public void testRotationEuclideanDistance() {
		double dist = TimeSeriesDistanceMeter.rotationEuclideanDistance(timeSeries1,
				timeSeries2);
		assertEquals(TIME_SERIES_ROTATION_DIST, dist, 0.0001);
	}
	
	@Test
	public void testRotationEuclideanDistanceZeroDistance() {
		double dist = TimeSeriesDistanceMeter.rotationEuclideanDistance(timeSeries1,
				timeSeries1);
		assertEquals(0, dist);
	}

}
