package utils.sax;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Random numbers Generetor
 */
public class RandomNumbersGenerator {
	protected Random rand = new Random();

	/**
	 * Generate n different numbers that are less than maxBound
	 * 
	 * @param n
	 * @param maxBound
	 * @return integer array
	 */
	public int[] getNRandomInts(int n, int maxBound) {
		assert n < maxBound;

		Set<Integer> result = new HashSet<Integer>();
		while (result.size() < n) {
			int number = rand.nextInt(maxBound);
			if (!result.contains(number)) {
				result.add(number);
			}
		}
		int[] intResult = new int[n];
		int index = 0;
		for (int number : result) {
			intResult[index++] = number;
		}
		return intResult;
	}
}
