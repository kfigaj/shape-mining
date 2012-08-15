package sax.representation.primitives;

/**
 * 
 * Class which is a simple SAX representation of an image
 *
 */
public class SAX {
	
	private String sax;
	private int alphabetCount;
	private TimeSeries timeSeries; // normalized and reduced
	private TimeSeries original; // created from image file - before normalization and reduction.


	public SAX() {
	}

	/**
	 * Get sax string
	 * @return sax
	 */
	public String getSax() {
		return sax;
	}

	/**
	 * Set sax as string of letters 
	 * @param sax
	 */
	public void setSax(String sax) {
		this.sax = sax;
	}

	/**
	 * Get number of letters used by this sax representation
	 * @return number of letters
	 */
	public int getAlphabetCount() {
		return alphabetCount;
	}

	/**
	 * Set number of letters used by this sax representation
	 * @param alphabetCount - number of letters
	 */
	public void setAlphabetCount(int alphabetCount) {
		this.alphabetCount = alphabetCount;
	}

	/**
	 * Get time series normalized and reduced 
	 * @return time series
	 */
	public TimeSeries getTimeSeries() {
		return timeSeries;
	}

	/**
	 *  set time series - normalized and reduced
	 * @param timeSeries
	 */
	public void setTimeSeries(TimeSeries timeSeries) {
		this.timeSeries = timeSeries;
	}
	
	/**
	 * Get original time series - before normalization and reduction
	 * @return original time series
	 */
	public TimeSeries getOriginal() {
		return original;
	}

	/**
	 * Set original time series - before normalization and reduction
	 * @param original - time series
	 */
	public void setOriginal(TimeSeries original) {
		this.original = original;
	}
	
	/**
	 * Update sax parameters
	 * @param sax - sax
	 * @param alphabetCount - number of letters
	 * @param timeSeries - time series after normalization and reduction 
	 */
	public void updateSax(String sax, int alphabetCount, TimeSeries timeSeries){
		this.sax = sax;
		this.alphabetCount = alphabetCount;
		this.timeSeries = timeSeries;
		
	}

}
