package com.googlecode.wmhshapes.quirkfinder.utils;

/**
 * Character utilities.
 * 
 * @author Dawid Pytel
 * 
 */
public class CharUtils {
	public static int distance(char c1, char c2) {
		return Math.abs(c1 - c2);
	}

	public static int distance(String s1, String s2) {
		assert s1.length() == s2.length();
		assert s1.length() > 0;
		int dist = 0;
		for (int i = 0; i < s1.length(); ++i) {
			dist += distance(s1.charAt(i), s2.charAt(i));
		}
		return dist;
	}

	public static String shiftLeft(String s, int count) {
		return s.substring(count) + s.substring(0, count);
	}
}
