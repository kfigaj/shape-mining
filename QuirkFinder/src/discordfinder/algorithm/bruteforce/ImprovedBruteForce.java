package discordfinder.algorithm.bruteforce;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import sax.representation.primitives.SAX;
import sax.representation.primitives.TimeSeries;
import utils.sax.TimeSeriesDistanceMeter;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.DiscordFinderAlgorithm;

/**
 * Quirk(the most uncommon element) finder algorithm. Improved Brute Force implementation.
 * Uses sax represenation of images to determine set of probable quirks. After that brute force finds discord
 * using time series representation of images.  
 */
public class ImprovedBruteForce implements DiscordFinderAlgorithm {

	@Override
	public SAXEnhanced findDiscord(List<SAXEnhanced> saxes) {
		Set<SAX> quirks = findSAXQuirks(saxes);
		SAX quirk = findQuirkFromQuirks(saxes, quirks);
		return (SAXEnhanced) quirk;
	}

	/**
	 * Chooses quirk from the set of quirks.
	 * 
	 * @param saxes - all shapes representations
	 * @param quirks - discord candidates
	 * @return sax - founded quirk - discord
	 */
	public SAX findQuirkFromQuirks(List<SAXEnhanced> saxes,
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

	public Set<SAX> findSAXQuirks(List<SAXEnhanced> saxes) {
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
