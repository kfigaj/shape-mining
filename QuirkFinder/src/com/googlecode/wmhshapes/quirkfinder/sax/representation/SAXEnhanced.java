package com.googlecode.wmhshapes.quirkfinder.sax.representation;

import com.googlecode.wmhshapes.quirkfinder.utils.CharUtils;

import sax.representation.Image;
import sax.representation.SAX;

/**
 * @author Dawid Pytel
 * 
 */
public class SAXEnhanced extends SAX {
	
	private Image image;

	public SAXEnhanced(Image image) {
		super();
		this.image = image;
	}

	public SAXEnhanced(Image image, String sax, int alphabetCount) {
		super();
		setSax(sax.toUpperCase());
		setAlphabetCount(alphabetCount);
		this.image = image;
	}

	public SAXEnhanced(Image image, SAX sax) {
		this(image, sax.getSax(), sax.getAlphabetCount());
		this.setTimeSeries(sax.getTimeSeries());
	}

	public int simpleDistance(SAX sax) {
		String saxStr = getSax();
		String saxStr2 = sax.getSax().toUpperCase();

		// preconditions
		assert saxStr.length() == saxStr2.length();
		assert getAlphabetCount() == sax.getAlphabetCount();

		return CharUtils.distance(saxStr, saxStr2);
	}

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

	// TODO: improve efficiency by caching this
	public SAXEnhanced[] rotations() {
		SAXEnhanced[] rotations = new SAXEnhanced[getSax().length()];
		for (int i = 0; i < rotations.length; ++i) {
			rotations[i] = new SAXEnhanced(image, CharUtils.shiftLeft(
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

	public Image getImage() {
		return image;
	}

	@Override
	public String toString() {
		return getSax();
	}

}
