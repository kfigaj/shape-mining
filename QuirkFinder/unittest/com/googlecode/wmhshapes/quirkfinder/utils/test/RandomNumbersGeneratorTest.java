/**
 * 
 */
package com.googlecode.wmhshapes.quirkfinder.utils.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.wmhshapes.quirkfinder.utils.RandomNumbersGenerator;

/**
 * @author Dawid Pytel
 * 
 */
public class RandomNumbersGeneratorTest {

	private static final int MAX_BOUNDS = 10;
	private static final int N = 4;
	private RandomNumbersGenerator gen;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen = new RandomNumbersGenerator();
	}

	/**
	 * Test method for
	 * {@link com.googlecode.wmhshapes.quirkfinder.utils.RandomNumbersGenerator#getNRandomInts(int, int)}
	 * .
	 */
	@Test
	public void testGetNRandomInts() {
		int[] randomInts = gen.getNRandomInts(N, MAX_BOUNDS);
		assertEquals(N, randomInts.length);
		assertLessThan(MAX_BOUNDS, randomInts);
		assertNonNegative(randomInts);
		assertUniqueNumbers(randomInts);
	}

	private void assertLessThan(int maxBounds, int[] randomInts) {
		for (int i : randomInts) {
			assertTrue(i < maxBounds);
		}
	}

	private void assertNonNegative(int[] randomInts) {
		for (int i : randomInts) {
			assertTrue(i >= 0);
		}
	}

	private void assertUniqueNumbers(int[] randomInts) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i : randomInts) {
			assertFalse(set.contains(new Integer(i)));
			set.add(i);
		}
		assertEquals(randomInts.length, set.size());
	}

}
