package com.googlecode.wmhshapes.quirkfinder.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.wmhshapes.quirkfinder.utils.CharUtils;

public class CharUtilsTest {

	@Test
	public void testDistanceCharChar() {
		assertEquals(2, CharUtils.distance('a', 'c'));
		assertEquals(2, CharUtils.distance('c', 'a'));
	}

	@Test
	public void testReveDistanceCharCharTheSameChars() {
		assertEquals(0, CharUtils.distance('a', 'a'));
	}

	@Test
	public void testDistanceStringString() {
		assertEquals(3, CharUtils.distance("abc", "ace"));
	}

	@Test
	public void testDistanceStringStringTheSameStrings() {
		assertEquals(0, CharUtils.distance("abc", "abc"));
	}

	@Test
	public void testShiftLeft() {
		assertEquals("dabc", CharUtils.shiftLeft("abcd", 3));
	}

	@Test
	public void testShiftLeftNoShift() {
		assertEquals("abcd", CharUtils.shiftLeft("abcd", 0));
	}

}
