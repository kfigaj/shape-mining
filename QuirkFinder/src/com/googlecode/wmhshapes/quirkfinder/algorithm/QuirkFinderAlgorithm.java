package com.googlecode.wmhshapes.quirkfinder.algorithm;

import java.util.Collection;

import sax.representation.Image;

/**
 * Defines template of the algorithm.
 * 
 * @author Dawid Pytel
 * 
 */
public interface QuirkFinderAlgorithm {

	/**
	 * Finds the most unusual shape out of given images.
	 * 
	 * @param images
	 * @return
	 */
	Image findQuirk(Collection<Image> images);
}
