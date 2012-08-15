package utils.sax;

import java.util.Iterator;

import sax.representation.primitives.TimeSeries;

/**
 * Class that evaluates distances between time series.
 * 
 * 
 */
public class TimeSeriesDistanceMeter {

	/**
	 * Get euclidean distance between two time series.
	 * @param timeSeries1
	 * @param timeSeries2
	 * @return distance
	 */
	public static double euclideanDistance(TimeSeries timeSeries1,
			TimeSeries timeSeries2) {
		assert timeSeries1.getTimeSeriesVector().size() == timeSeries2
				.getTimeSeriesVector().size();
		Iterator<Double> it1 = timeSeries1.getTimeSeriesVector().iterator();
		Iterator<Double> it2 = timeSeries2.getTimeSeriesVector().iterator();
		double sum = 0;
	
		while (it1.hasNext()) {
			double d1 = it1.next();
			double d2 = it2.next();
			double delta = d1 - d2;
			sum += delta * delta;
		}
		return Math.sqrt(sum);
	}

	/**
	 * Get euclidean distance between two time series considering all rotations.
	 * @param timeSeries1
	 * @param timeSeries2
	 * @return distance
	 */
	public static double rotationEuclideanDistance(TimeSeries timeSeries1,
			TimeSeries timeSeries2) {
		assert timeSeries1.getTimeSeriesVector().size() == timeSeries2
		.getTimeSeriesVector().size();
		double minDist = Double.MAX_VALUE;
		for (TimeSeries rotation2 : timeSeries2.getRotations()) {
			double dist = euclideanDistance(timeSeries1, rotation2);
			if (dist < minDist) {
				minDist = dist;
			}
		}
		return minDist;
	}
}
