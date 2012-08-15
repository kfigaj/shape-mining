package discordfinder.algorithm.bruteforce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sax.representation.primitives.Pair;
import sax.representation.primitives.SAX;
import sax.util.test.Settings;

public class ResultSet {
	/**
	 * Sorted list. Shape with the greatest mindist has index 0.
	 */
	private List<Pair<SAX, Integer>> results = new ArrayList<Pair<SAX, Integer>>();

	public void consider(SAX sax, int minDist) {
		// we are looking for SAX_RESULTS most different shapes
		if (minDist > getMinMaxNearestNeighbourDist()) {
			add(sax, minDist);
		}
	}

	public void add(SAX sax, int minDist) {
		add(new Pair<SAX, Integer>(sax, minDist));
	}

	public Set<SAX> getSAXSet() {
		Set<SAX> set = new HashSet<SAX>();
		for (Pair<SAX, Integer> pair : results) {
			set.add(pair.getFirstElement());
		}
		return set;
	}

	public void add(Pair<SAX, Integer> pair) {
		if (results.size() >= Settings.BRUTE_SAX_RESULTS) {
			results.remove(results.size() - 1);
		}
		safeAdd(pair);
	}

	public int getMinMaxNearestNeighbourDist() {
		if (results.size() < Settings.BRUTE_SAX_RESULTS) {
			return Integer.MIN_VALUE;
		} else {
			return results.get(results.size() - 1).getSecondElement();
		}
	}

	private void safeAdd(Pair<SAX, Integer> pair) {
		int index = Collections.binarySearch(results, pair,
				new SAXPairComparator());
		if (index < 0) {
			index = -(index + 1);
		}
		results.add(index, pair);
	}

	static class SAXPairComparator implements Comparator<Pair<SAX, Integer>> {

		@Override
		public int compare(Pair<SAX, Integer> arg0, Pair<SAX, Integer> arg1) {
			return arg1.getSecondElement() - arg0.getSecondElement();
		}

	}
}