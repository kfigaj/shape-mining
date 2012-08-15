package com.googlecode.wmhshapes.quirkfinder.utils;

public class Timer {
	private long start;
	
	public Timer() {
		start = System.currentTimeMillis();
	}
	
	public long elapsed() {
		return System.currentTimeMillis() - start;
	}
}
