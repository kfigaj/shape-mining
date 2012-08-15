package utils.sax;

/**
 * Timer class
 *
 */
public class Timer {
	private long start;
	
	/**
	 * Sets start time in constructor
	 */
	public Timer() {
		start = System.currentTimeMillis();
	}
	/**
	 * Get time that elapsed from timer construction.
	 * @return time in [ms]
	 */
	public long elapsed() {
		return System.currentTimeMillis() - start;
	}
}
