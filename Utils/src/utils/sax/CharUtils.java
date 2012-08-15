package utils.sax;

/**
 * Character utilities.
 * 
 */
public class CharUtils {
	
	/**
	 * Distance between two chars
	 * @param c1 - first char
	 * @param c2 - second char
	 * @return distance
	 */
	public static int distance(char c1, char c2) {
		return Math.abs(c1 - c2);
	}

	/**
	 * Distance between two strings
	 * @param s1 - first string
	 * @param s2 - second string
	 * @return distance
	 */
	public static int distance(String s1, String s2) {
		assert s1.length() == s2.length();
		assert s1.length() > 0;
		int dist = 0;
		for (int i = 0; i < s1.length(); ++i) {
			dist += distance(s1.charAt(i), s2.charAt(i));
		}
		return dist;
	}

	/**
	 * Shift left of string by number of letters
	 * @param s - string
	 * @param count - number of letters
	 * @return - string after shift
	 */
	public static String shiftLeft(String s, int count) {
		return s.substring(count) + s.substring(0, count);
	}
}
