/**
 * 
 */
package com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sax.representation.Image;
import sax.utils.SAXCreator;
import sax.utils.Settings;

import com.googlecode.wmhshapes.quirkfinder.algorithm.QuirkFinderAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.sax.representation.SAXEnhanced;
import com.googlecode.wmhshapes.quirkfinder.sax.representation.TimeSeriesDistanceMeter;

/**
 * @author Dawid Pytel
 * 
 */
public class LocalSensivityHashingAlgorithm implements QuirkFinderAlgorithm {

	private static final int ITERATIONS_NUMBER = 5;
	public static int HASH_SIZE = 4;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.wmhshapes.quirkfinder.algorithm.QuirkFinderAlgorithm#findQuirk
	 * (java.util.Collection)
	 */
	@Override
	public Image findQuirk(Collection<Image> images) {
		List<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
		for (Image image : images) {
			saxes.add(new SAXEnhanced(image, SAXCreator.create(image)));
		}
		OptimalApproximation optimalApproximation = optimalApproximation(saxes);
		double bestSoFarDist = 0;
		SAXEnhanced bestSax = null;
		for (SAXEnhanced sax : optimalApproximation.getOuterLoop()) {
			double nearestNeighbourDist = Double.MAX_VALUE;
			for (SAXEnhanced neighbour : optimalApproximation.getInnerLoop()) {
				if (sax != neighbour) {
					double distance = TimeSeriesDistanceMeter
							.rotationEuclideanDistance(sax.getTimeSeries(),
									neighbour.getTimeSeries());
					if (distance < bestSoFarDist) {
						nearestNeighbourDist = Double.MIN_VALUE;
						break;
					}
					if (distance < nearestNeighbourDist) {
						nearestNeighbourDist = distance;
					}
				}
			}
			if (nearestNeighbourDist > bestSoFarDist) {
				bestSoFarDist = nearestNeighbourDist;
				bestSax = sax;
			}
		}
		return bestSax.getImage();
	}

	/**
	 * Find optimal approximation that will be used as a outer and inner loops.
	 * 
	 * @param saxes
	 * @return
	 */
	public OptimalApproximation optimalApproximation(List<SAXEnhanced> saxes) {
		int[][] collisionMatrix = new int[saxes.size()][saxes.size()];
		for (int i = 0; i < collisionMatrix.length; ++i) {
			Arrays.fill(collisionMatrix[i], 0);
		}
		for (int iteration = 0; iteration < ITERATIONS_NUMBER; ++iteration) {
			Map<String, Set<Integer>> buckets = new HashMap<String, Set<Integer>>();
			HashFunction function = new HashFunction(HASH_SIZE,
					Settings.PAA_ELEMENTS_NUMBER);
			for (int i = 0; i < saxes.size(); ++i) {
				SAXEnhanced sax = saxes.get(i);
				Collection<String> hashedRotations = function
						.valueOfAllRotations(sax);
				for (String hashedRotation : hashedRotations) {
					Set<Integer> bucketContent;
					if (!buckets.containsKey(hashedRotation)) {
						HashSet<Integer> set = new HashSet<Integer>();
						buckets.put(hashedRotation, set);
						bucketContent = set;
					} else {
						bucketContent = buckets.get(hashedRotation);
					}
					for (int index : bucketContent) {
						if (index != i) {
							++collisionMatrix[i][index];
							++collisionMatrix[index][i];
						}
					}
					bucketContent.add(i);
				}
			}
		}
		return new OptimalApproximation(saxes, collisionMatrix);
	}

	@Override
	public String toString() {
		return "Local hashing algorithm";
	}
}
