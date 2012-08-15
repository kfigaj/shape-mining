package discordfinder.algorithm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import sax.representation.primitives.SAX;
import sax.util.test.Settings;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.bruteforce.ResultSet;

public class RestultSetTest {

	private ResultSet emptyResultSet;

	private ResultSet fullResultSet;

	private List<SAXEnhanced> saxes;

	private String[] strings = new String[] { "ABCD", "ABCE", "ABCF", "ABCG",
			"DDDD", "GGGG", "IIII", "KKKK", "MMMM", "ADCR" };

	@Before
	public void setUp() throws Exception {
		saxes = new ArrayList<SAXEnhanced>();
		for (String s : strings) {
			saxes.add(new SAXEnhanced(null, s, 26));
		}
		emptyResultSet = new ResultSet();
		fullResultSet = new ResultSet();

		for (int i = 0; i < Settings.BRUTE_SAX_RESULTS; ++i) {
			fullResultSet.consider(getAnySAX(), getAnyDist());
		}

	}

	@Test
	public void testEmptyResultSet() {
		assertTrue(emptyResultSet.getSAXSet().isEmpty());
		assertTrue(emptyResultSet.getMinMaxNearestNeighbourDist() < 0);
	}

	@Test
	public void testConsiderToEmptySet() {
		SAX anySAX = getAnySAX();
		int anyDist = getAnyDist();
		emptyResultSet.consider(anySAX, anyDist);
		Set<SAX> resultSet = emptyResultSet.getSAXSet();

		assertTrue(emptyResultSet.getMinMaxNearestNeighbourDist() < anyDist);
		assertEquals(1, resultSet.size());
		assertSame(anySAX, resultSet.iterator().next());
	}

	@Test
	public void testConsiderSecondElementGreaterDist() {
		int dist1 = getAnyDist();
		ResultSet oneElementResultSet = getOneElementResultSet(dist1);
		int greaterDist = dist1 + 1;
		SAX anySAX = getAnySAX();
		oneElementResultSet.consider(anySAX, greaterDist);

		assertTrue(oneElementResultSet.getMinMaxNearestNeighbourDist() < dist1);
		Set<SAX> set = oneElementResultSet.getSAXSet();
		assertEquals(2, set.size());
		assertTrue(set.contains(anySAX));
	}

	@Test
	public void testConsiderSecondElementSmallerDist() {
		int dist1 = getAnyDist();
		ResultSet oneElementResultSet = getOneElementResultSet(dist1);
		int greaterDist = dist1 - 1;
		SAX anySAX = getAnySAX();
		oneElementResultSet.consider(anySAX, greaterDist);

		assertTrue(oneElementResultSet.getMinMaxNearestNeighbourDist() < dist1);
		Set<SAX> set = oneElementResultSet.getSAXSet();
		assertEquals(2, set.size());
		assertTrue(set.contains(anySAX));
	}

	@Test
	public void testFullSet() {
		assertTrue(fullResultSet.getMinMaxNearestNeighbourDist() >= 0);
		assertEquals(Settings.BRUTE_SAX_RESULTS, fullResultSet.getSAXSet()
				.size());
	}

	@Test
	public void testConsiderFullElementSuccessfulConsidering() {
		int nextDist = fullResultSet.getMinMaxNearestNeighbourDist() + 1;
		SAX anySAX = getAnySAX();
		fullResultSet.consider(anySAX, nextDist);

		assertEquals(Settings.BRUTE_SAX_RESULTS, fullResultSet.getSAXSet()
				.size());
		assertEquals(nextDist, fullResultSet.getMinMaxNearestNeighbourDist());
		assertTrue(fullResultSet.getSAXSet().contains(anySAX));
	}

	@Test
	public void testConsiderFullElementNegativeConsidering() {
		int nextDist = fullResultSet.getMinMaxNearestNeighbourDist() - 1;
		SAX anySAX = getAnySAX();
		fullResultSet.consider(anySAX, nextDist);

		assertEquals(Settings.BRUTE_SAX_RESULTS, fullResultSet.getSAXSet()
				.size());
		assertFalse(nextDist == fullResultSet.getMinMaxNearestNeighbourDist());
		assertFalse(fullResultSet.getSAXSet().contains(anySAX));
	}

	@Test
	public void testConsiderFullElementDoubleSuccessfulConsidering() {
		// such distance that must be considered positively
		int nextDist = Integer.MAX_VALUE;

		SAX anySAX = getAnySAX();
		SAX anySAX2 = getAnySAX();
		fullResultSet.consider(anySAX, nextDist);
		fullResultSet.consider(anySAX2, nextDist);

		assertEquals(Settings.BRUTE_SAX_RESULTS, fullResultSet.getSAXSet()
				.size());
		assertFalse(nextDist == fullResultSet.getMinMaxNearestNeighbourDist());
		assertTrue(fullResultSet.getSAXSet().contains(anySAX));
		assertTrue(fullResultSet.getSAXSet().contains(anySAX2));
	}

	private int magicSax = 0;

	private SAX getAnySAX() {
		return saxes.get(magicSax++ % saxes.size());
	}

	private int magicDist = 1;

	private int getAnyDist() {
		return 12 + magicDist++;
	}

	private ResultSet getOneElementResultSet(int dist) {
		ResultSet oneElementResultSet;
		oneElementResultSet = new ResultSet();
		oneElementResultSet.consider(getAnySAX(), dist);
		return oneElementResultSet;
	}

}
