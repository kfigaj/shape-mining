/**
 * 
 */
package com.googlecode.wmhshapes.quirkfinder.algorithm.bruteforce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import sax.representation.Image;
import sax.representation.SAX;
import sax.representation.TimeSeries;
import sax.utils.SAXCreator;

import com.googlecode.wmhshapes.quirkfinder.algorithm.QuirkFinderAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.sax.representation.SAXEnhanced;
import com.googlecode.wmhshapes.quirkfinder.sax.representation.TimeSeriesDistanceMeter;

/**
 * @author Dawid Pytel
 * 
 */
public class ImprovedBruteForce implements QuirkFinderAlgorithm {

	public static final int SAX_RESULTS = 5;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.wmhshapes.quirkfinder.algorithm.QuirkFinderAlgorithm#findQuirk
	 * (java.util.Collection)
	 */
	@Override
	public Image findQuirk(Collection<Image> images) {
		Collection<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
		for (Image image : images) {
			saxes.add(new SAXEnhanced(image, SAXCreator.create(image)));
		}
		Set<SAX> quirks = findSAXQuirks(saxes);
		SAX bestSoFar = findQuirkFromQuirks(saxes, quirks);
		return ((SAXEnhanced) bestSoFar).getImage();
	}

	/**
	 * Chooses quirk from the set of quirks.
	 * 
	 * @param saxes
	 * @param quirks
	 * @return
	 */
	public SAX findQuirkFromQuirks(Collection<SAXEnhanced> saxes,
			Collection<SAX> quirks) {
		double bestSoFarDist = 0;
		SAX bestSoFar = null;
		for (SAX quirk : quirks) {
			double nearestNeighbourDist = Double.MAX_VALUE;
			TimeSeries timeSeries = quirk.getTimeSeries();
			SAX neighbour = null;
			for (SAXEnhanced sax : saxes) {
				if (sax != quirk) {
					TimeSeries timeSeries2 = sax.getTimeSeries();
					double dist = TimeSeriesDistanceMeter
							.rotationEuclideanDistance(timeSeries, timeSeries2);
					if (dist < bestSoFarDist) {
						nearestNeighbourDist = Double.MAX_VALUE; // to exclude
																	// this case
						break;
					}
					if (dist < nearestNeighbourDist) {
						nearestNeighbourDist = dist;
						neighbour = sax;
					}
				}
			}
			
			if (nearestNeighbourDist > bestSoFarDist
					&& nearestNeighbourDist != Double.MAX_VALUE) {
				bestSoFarDist = nearestNeighbourDist;
				bestSoFar = quirk;
			}
		}
		return bestSoFar;
	}

	public Set<SAX> findSAXQuirks(Collection<SAXEnhanced> saxes) {
		ResultSet results = new ResultSet();
		for (SAXEnhanced sax1 : saxes) {
			int minDist = Integer.MAX_VALUE;
			for (SAXEnhanced sax2 : saxes) {
				if (sax1 != sax2) {
					int dist = sax1.rotatedDistance(sax2);
					if (dist < minDist) {
						minDist = dist;
					}
				}
			}
			results.consider(sax1, minDist);
		}
		return results.getSAXSet();
	}
	
	@Override
	public String toString() {
		return "Improved brute force algorithm";
	}

}
