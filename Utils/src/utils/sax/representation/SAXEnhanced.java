package utils.sax.representation;


import sax.representation.primitives.SAX;
import utils.sax.CharUtils;


/**
 * This is an enhanced version of simple SAX representation.
 * Several convenience method have been provided.
 */
public class SAXEnhanced extends SAX {
	
	private String imagePath=null;
	private String imageClass=null;

	/**
	 * Constructor
	 * @param imagePath - path to image which shape
	 */
	public SAXEnhanced(String imagePath) {
		super();
		this.imagePath = imagePath;
	}

	/**
	 * Constructor
	 * @param imagePath - path to image which shape
	 * @param sax - sax as string of letters 
	 * @param alphabetCount - number of letters used by this sax representation
	 */
	public SAXEnhanced(String imagePath, String sax, int alphabetCount) {
		super();
		setSax(sax.toUpperCase());
		setAlphabetCount(alphabetCount);
		this.imagePath = imagePath;
	}

	/**
	 * Constructor
	 * @param imagePath - path to image which shape
	 * @param sax - sax as string of letters 
	 */
	public SAXEnhanced(String imagePath, SAX sax) {
		this(imagePath, sax.getSax(), sax.getAlphabetCount());
		this.setTimeSeries(sax.getTimeSeries());
		this.setOriginal(sax.getOriginal());
	}

	/**
	 * Count simple distance between this sax and given by parameter
	 * @param sax - sax to count distance to
	 * @return distance
	 */
	public int simpleDistance(SAX sax) {
		String saxStr = getSax();
		String saxStr2 = sax.getSax().toUpperCase();

		// preconditions
		assert saxStr.length() == saxStr2.length();
		assert getAlphabetCount() == sax.getAlphabetCount();

		return CharUtils.distance(saxStr, saxStr2);
	}

	/**
	 * Count distance between this sax and given by parameter. Take rotations in consideration.
	 * @param sax - sax to count distance to
	 * @return distance
	 */
	public int rotatedDistance(SAX sax) {
		int minDist = Integer.MAX_VALUE;
		for (SAXEnhanced rotation : rotations()) {
			int dist = rotation.simpleDistance(sax);
			if (dist < minDist) {
				minDist = dist;
			}
		}
		return minDist;
	}

	/**
	 * Get sax rotations
	 * @return rotations
	 */
	public SAXEnhanced[] rotations() {
		SAXEnhanced rotations[] = new SAXEnhanced[getSax().length()];
		for (int i = 0; i < rotations.length; ++i) {
				rotations[i] = new SAXEnhanced(imagePath, CharUtils.shiftLeft(
						getSax(), i), getAlphabetCount());
		}
	
		return rotations;
	}


	@Override
	public void setSax(String sax) {
		assert sax.matches("[a-zA-Z]+");
		super.setSax(sax.toUpperCase());
	}

	@Override
	public void setAlphabetCount(int alphabetCount) {
		assert alphabetCount > 0 && alphabetCount <= 26;
		super.setAlphabetCount(alphabetCount);
	}

	public String getImagePath() {
		return imagePath;
	}

	@Override
	public String toString() {
		if(imagePath != null)
			return imagePath;
		else return "";
	}

	public void setImageClass(String imageClass) {
		this.imageClass = imageClass;
	}

	public String getImageClass() {
		return imageClass;
	}

}
