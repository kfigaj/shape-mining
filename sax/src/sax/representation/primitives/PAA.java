package sax.representation.primitives;

/**
 * PAA - Piecewise Aggregate Approximation
 *
 */
public class PAA {

	private double[] elements; 
	private TimeSeries timeSeries = null;
	
	public PAA() {
	}

	public double[] getElements() {
		return elements;
	}

	public void setElements(double[] elements) {
		this.elements = elements;
	}

	public void setTimeSeries(TimeSeries timeSeries) {
		this.timeSeries = timeSeries;
	}

	public TimeSeries getTimeSeries() {
		return timeSeries;
	}
	
}
