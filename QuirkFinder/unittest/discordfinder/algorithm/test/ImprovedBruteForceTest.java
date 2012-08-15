package discordfinder.algorithm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sax.representation.primitives.Image;
import sax.representation.primitives.SAX;
import sax.representation.primitives.TimeSeries;
import sax.util.test.Settings;
import sax.utils.SAXCreator;
import sax.utils.TimeSeriesCreator;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.bruteforce.ImprovedBruteForce;

public class ImprovedBruteForceTest {

	private List<SAXEnhanced> saxes;

	private String[] strings = new String[] { "ABCD", "ABCE", "ABCF", "ABCG",
			"ZZZZ", "YYYY", "IIII", "KKKK", "MMMM", "ADCD", "ADCE", "BBCD",
			"BDCD" };

	private int[] quirksIndices = new int[] { 4, 5, 6, 7, 8 };

	@Before
	public void setUp() throws Exception {
		saxes = createSaxes(Arrays.asList(strings));
	}

	private List<SAXEnhanced> createSaxes(Collection<String> strings) {
		List<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
		for (String s : strings) {
			SAXEnhanced sax = new SAXEnhanced(null, s, 26);
			TimeSeries timeSeries = new TimeSeries();
			for (int i = 0; i < s.length(); ++i) {
				timeSeries.add((double) s.charAt(i));
			}
			sax.setTimeSeries(timeSeries);
			saxes.add(sax);
		}
		return saxes;
	}

	@Test
	public void testFindSAXQuirk() {
		ImprovedBruteForce improvedBruteForce = new ImprovedBruteForce();
		Set<SAX> foundQuirks = improvedBruteForce.findSAXQuirks(saxes);

		assertEquals(Settings.BRUTE_SAX_RESULTS, foundQuirks.size());
		for (int i : quirksIndices) {
			SAX expectedQuirk = saxes.get(i);
			assertTrue("quirks does not contain: " + expectedQuirk, foundQuirks
					.contains(expectedQuirk));
		}
	}

	@Test
	public void testFindQuirkInQuirk() {
		List<SAXEnhanced> saxes = createSaxes(Arrays.asList("AAAD", "AAAD",
				"ABBB", "ABBB", "ABCD", "CDCC", "CDCC"));
		SAXEnhanced quirk = saxes.get(4);
		Collection<SAX> quirks = Arrays.asList((SAX) saxes.get(0),
				saxes.get(1), saxes.get(2), saxes.get(4));
		ImprovedBruteForce improvedBruteForce = new ImprovedBruteForce();
		SAX resultQuirk = improvedBruteForce.findQuirkFromQuirks(saxes, quirks);
		assertSame(quirk, resultQuirk);
	}

}
