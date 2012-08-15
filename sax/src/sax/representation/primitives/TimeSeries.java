package sax.representation.primitives;

import java.util.Vector;

import sax.util.test.Settings;

/**
 * Class which represents time series of Image
 * 
 * 
 */
public class TimeSeries implements Cloneable {

	private Vector<Double> timeSeries;
	private TimeSeries[] rotations = null;

	public TimeSeries() {
		timeSeries = new Vector<Double>();
		timeSeries.ensureCapacity(Settings.TIME_SERIES_NUMBER);
	}

	public Vector<Double> getTimeSeriesVector() {
		return timeSeries;
	}

	public Object[] getTimeSeriesArray() {
		return timeSeries.toArray();
	}

	public void add(Double value) {
		timeSeries.add(value);
	}

	public TimeSeries[] getRotations() {
		if (rotations == null) {
			// lazy loading
			rotations = new TimeSeries[timeSeries.size()];
			rotations[0] = this;
			for (int i = 1; i < timeSeries.size(); ++i) {
				TimeSeries rotation = new TimeSeries();
				rotation.timeSeries = new Vector<Double>(timeSeries.subList(i,
						timeSeries.size()));
				rotation.timeSeries.addAll(timeSeries.subList(0, i));
				rotations[i] = rotation;
			}
		}
		return rotations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		TimeSeries series = new TimeSeries();
		series.timeSeries = (Vector<Double>) timeSeries.clone();
		return series;
	}

	public static TimeSeries normalize(TimeSeries timeSeries) {
		double meanValue = 0;
		for (Double i : timeSeries.getTimeSeriesVector())
			meanValue += i;

		meanValue /= timeSeries.getTimeSeriesVector().size();

		double standardDeviation = 0;
		boolean isRound = false;
		
		for (Double i : timeSeries.getTimeSeriesVector()) {
			standardDeviation += Math.pow(i - meanValue, 2);
		}

		standardDeviation /= (timeSeries.getTimeSeriesVector().size() - 1);
		standardDeviation = Math.sqrt(standardDeviation);
		
		double percentStandardDeviation = standardDeviation*100/meanValue;
		
		if ( percentStandardDeviation < Settings.CIRCULAR_THRESHOLD) // do not normalize round shapes
			isRound = true;

		TimeSeries newTimeSeries = new TimeSeries();
		newTimeSeries.getTimeSeriesVector().ensureCapacity(
				timeSeries.getTimeSeriesVector().capacity());
		for (Double i : timeSeries.getTimeSeriesVector())
			if(isRound)
				newTimeSeries.add(i - meanValue);
			else
				newTimeSeries.add((i - meanValue) / standardDeviation);

		return newTimeSeries;
	}

	public static TimeSeries reduce(TimeSeries timeSeries, int numberOfElements) {
		/***
		 * reduce or extend - depends on size of timeSeries.
		 */
		assert  numberOfElements > 0;
		assert timeSeries.getTimeSeriesVector().size() > 0;
		
		TimeSeries newTimeSeries = new TimeSeries();
		if (numberOfElements == timeSeries.getTimeSeriesVector().size()) {
			newTimeSeries = timeSeries;
		}else{
			double step = (double)timeSeries.getTimeSeriesVector().size()
					/ numberOfElements;
			int y = 0;
			for (double i = 0; (int) i < timeSeries.getTimeSeriesVector().size()
					&& y < numberOfElements; i += step, y++)
				newTimeSeries.getTimeSeriesVector().add(
						timeSeries.getTimeSeriesVector().get( (int) i ));

			
		}
		
		assert newTimeSeries.getTimeSeriesVector().size() == numberOfElements;
		return newTimeSeries;
	}
}
