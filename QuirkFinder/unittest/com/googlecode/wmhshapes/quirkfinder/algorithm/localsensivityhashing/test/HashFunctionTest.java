package com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import sax.representation.SAX;

import com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.HashFunction;
import com.googlecode.wmhshapes.quirkfinder.sax.representation.SAXEnhanced;

public class HashFunctionTest {

	private static final int MAX_BOUNDS = 10;
	private static final int HASH_SIZE = 5;
	private HashFunction fun;

	@Before
	public void setUp() throws Exception {
		fun = new HashFunction(HASH_SIZE, MAX_BOUNDS);
	}

	@Test
	public void testValueOf() {
		SAX sax = getUniqueSax(MAX_BOUNDS);
		String hash = fun.valueOf(sax);
		assertProperHash(sax, hash);
	}

	@Test
	public void testValueOfAll() {
		SAXEnhanced sax = getUniqueSax(MAX_BOUNDS);
		Collection<String> hashes = fun.valueOfAllRotations(sax);
		assertEquals(sax.rotations().length, hashes.size());
		for (String hash : hashes) {
			assertProperHash(sax, hash);
		}
	}

	private void assertProperHash(SAX sax, String hash) {
		assertEquals(HASH_SIZE, hash.length());
		assertNoAdditionalLetters(hash, sax);
	}

	private SAXEnhanced getUniqueSax(int maxBounds) {
		SAX sax = new SAX();
		sax.setSax("ABCDEFGHIJ");
		sax.setAlphabetCount(10);
		return new SAXEnhanced(null, sax);
	}

	private void assertNoAdditionalLetters(String hash, SAX sax) {
		String saxString = sax.getSax();
		for (char c : hash.toCharArray()) {
			assertTrue(saxString.contains("" + c));
		}
	}

}
