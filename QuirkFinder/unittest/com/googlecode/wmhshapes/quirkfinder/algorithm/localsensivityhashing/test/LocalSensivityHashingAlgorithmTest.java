package com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sax.representation.TimeSeries;
import sax.utils.Settings;

import com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.OptimalApproximation;
import com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.OptimalApproximation.Loop;
import com.googlecode.wmhshapes.quirkfinder.sax.representation.SAXEnhanced;

public class LocalSensivityHashingAlgorithmTest {

	private LocalSensivityHashingAlgorithm algorithm;

	private List<SAXEnhanced> saxes;

	private String[] strings = new String[] { "ABCD", "ABCE", "ABCF", "ABCG",
			"ZZZZ", "YYYY", "IIII", "KKKK", "MMMM", "ADCD", "ADCE", "BBCD",
			"BDCD" };

	private int[] quirksIndices = new int[] { 4, 5, 6, 7, 8 };

	@Before
	public void setUp() throws Exception {
		saxes = createSaxes(Arrays.asList(strings));
		algorithm = new LocalSensivityHashingAlgorithm();
		Settings.PAA_ELEMENTS_NUMBER = 4;
		LocalSensivityHashingAlgorithm.HASH_SIZE = 2;
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
	public void testOptimalApproximation() {
		OptimalApproximation optimalApproximation = algorithm
				.optimalApproximation(saxes);
		asserProperSize(optimalApproximation);
		assertQuirksFirst(optimalApproximation);
	}

	private void asserProperSize(OptimalApproximation optimalApproximation) {
		int outerLoopSize = 0;
		for (@SuppressWarnings("unused")
		SAXEnhanced sax : optimalApproximation.getOuterLoop()) {
			++outerLoopSize;
		}
		assertEquals(saxes.size(), outerLoopSize);
	}

	private void assertQuirksFirst(OptimalApproximation optimalApproximation) {
		List<SAXEnhanced> quirks = new ArrayList<SAXEnhanced>();
		for (int quirk : quirksIndices) {
			quirks.add(saxes.get(quirk));
		}
		Loop outerLoop = optimalApproximation.getOuterLoop();
		Iterator<SAXEnhanced> it = outerLoop.iterator();
		for (int i = 0; i < quirks.size(); ++i) {
			SAXEnhanced sax = it.next();
			assertTrue(quirks.contains(sax));
		}
	}

}
