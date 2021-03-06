package discordfinder.algorithm.localsensivityhashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import sax.representation.primitives.SAX;
import utils.sax.RandomNumbersGenerator;
import utils.sax.representation.SAXEnhanced;


/**
 * Rotation Invariant Hash function for LSH discord finding algorithm 
 */
public class HashFunction {
	private static RandomNumbersGenerator gen = new RandomNumbersGenerator();
	private int[] indices;
	private int maxBound;

	public HashFunction(int hashSize, int maxBound) {
		this.maxBound = maxBound;
		indices = gen.getNRandomInts(hashSize, maxBound);
		Arrays.sort(indices);
	}

	public String valueOf(SAX sax) {
		StringBuilder result = new StringBuilder();
		String saxString = sax.getSax();
		if (saxString.length() < maxBound) {
			throw new IllegalArgumentException("Wrong sax size");
		}
		for (int index : indices) {
			result.append(saxString.charAt(index));
		}
		return result.toString();
	}
	
	public Collection<String> valueOfAllRotations(SAXEnhanced sax) {
		ArrayList<String> values = new ArrayList<String>();
		for (SAXEnhanced rotation : sax.rotations()) {
			values.add(valueOf(rotation));
		}
		return values;
	}
}
