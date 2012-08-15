package sax.util.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main Settings Class
 *
 */
public class Settings {
											// discord, class, 
	public static int ALPHABET_NUMBER = 7; //5, 5/7 - (min 3 - max 10)
	public static int TIME_SERIES_NUMBER = 200; //100, 250
	public static int PAA_ELEMENTS_NUMBER = 25; //10, 25/30
	public static boolean USE_RECONSTRUCTION_ERROR = false;
	public static double CIRCULAR_THRESHOLD = 8; //[%] 5-15?
	
	public static HashMap<Integer,List<Double>> breakpoints = new HashMap<Integer, List<Double>>();
	
	static{
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(-0.43); temp.add(0.43);
		breakpoints.put(3,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-0.67); temp.add(0.0); temp.add(0.67);
		breakpoints.put(4,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-0.84); temp.add(-0.25); temp.add(0.25); temp.add(0.84);
		breakpoints.put(5,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-0.97); temp.add(-0.43); temp.add(0.0); 
		temp.add(0.43); temp.add(0.97);
		breakpoints.put(6,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-1.07); temp.add(-0.57); temp.add(-0.18); 
		temp.add(0.18); temp.add(0.57); temp.add(1.07); 
		breakpoints.put(7,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-1.15); temp.add(-0.67); temp.add(-0.32); 
		temp.add(0.0); temp.add(0.32); temp.add(0.67); 
		temp.add(1.15); 
		breakpoints.put(8,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-1.22); temp.add(-0.76); temp.add(-0.43); 
		temp.add(-0.14); temp.add(0.14); temp.add(0.43);
		temp.add(0.76); temp.add(1.22);
		breakpoints.put(9,temp);
		
		temp = new ArrayList<Double>();
		temp.add(-1.28); temp.add(-0.84); temp.add(-0.52); 
		temp.add(-0.25); temp.add(0.0); temp.add(0.25);
		temp.add(0.52); temp.add(0.84); temp.add(1.28);
		breakpoints.put(10,temp);
	}
	
	/////////////////////////
	// Algorithm settings	/
	/////////////////////////
	
	// K - nearest neighbour classification algorithm
	public static int K_VALUE = 10;
	
	// Local hashing 
	public static int ITERATIONS_NUMBER = 5;
	public static int HASH_SIZE = 4;
	
	// Improved brute force 
	public static int BRUTE_SAX_RESULTS = 5;
	
	// Convenience function for receiving parameters as String for algorithm.
	public static final int NONE = 0;
	public static final int K_NEAREST_NEIGHBOUR = 1;
	public static final int LOCAL_HASHING = 2;
	public static final int IMPROVED_BRUTE_FORCE = 3;

	public static String getParametersAsString(int type){
		String result;
		result = "SAX parameters:\n";
		result += "Number of time series elmenets: " + TIME_SERIES_NUMBER + "\n";
		result += "Number of PAA elmenets: " + PAA_ELEMENTS_NUMBER + "\n";
		result += "Number of letters: " + ALPHABET_NUMBER + "\n";
		result += "Using Sax reconstruction error: " + USE_RECONSTRUCTION_ERROR + "\n";
		result += "Circular threshold: " + CIRCULAR_THRESHOLD + "[%]\n\n";
		
		if(type == K_NEAREST_NEIGHBOUR){
			result += "K Nearest Neighbour algorihtm parameters:\n";
			result += "K neighbours value: " + K_VALUE + "\n";
		}else if(type == LOCAL_HASHING){
			result += "Local hashing algorihtm parameters:\n";
			result += "Number of iteretions in algorithm: " + ITERATIONS_NUMBER + "\n";
			result += "Size of hash buckets: " + HASH_SIZE + "\n";
		}else if(type == IMPROVED_BRUTE_FORCE){
			result += "Improved Brute force algorihtm parameters:\n";
			result += "Number of elements used by brute algorithm: " + BRUTE_SAX_RESULTS + "\n";
		}
		result += "\n";
		return result;
	}
}
