package classification.algorithm.nearestneighbour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sax.representation.primitives.Pair;
import utils.sax.representation.SAXEnhanced;

/**
 * K Nearest Neighbour Algorithm
 */
public class KNearestNeighbour {
	private Map<SAXEnhanced, Pair<Integer,String>> helper;
	private List<SAXEnhanced> currentNeighbors;
	private int kValue;
	
	/**
	 * Function that classifies sax representation of shape with KNN algorithm
	 * @param classification - list of pairs - SAXENhanced and String with class -
	 *  it represents current classification
	 * @param toClassify - sax representation of image to classify
	 * @param k - number of neighbours to take into consideration
	 * @return - list of pairs of strings - classes and double which shows
	 * support for this class from neighbours  
	 */
	public List<Pair<String, Double>> classify(List<Pair<SAXEnhanced, String>> classification,
			SAXEnhanced toClassify, int k){
		if(classification == null || classification.isEmpty() ||
				toClassify == null || k < 1 )
			return null;
		
		kValue = k;
		int distance;
		helper = new HashMap<SAXEnhanced, Pair<Integer,String>>();
		currentNeighbors = new ArrayList<SAXEnhanced>();
		
		for(Pair<SAXEnhanced, String> p : classification){
			SAXEnhanced saxFromList = p.getFirstElement();
			
			if(saxFromList != toClassify && saxFromList != null){
				distance = toClassify.rotatedDistance(saxFromList);
				conditionalPutInList(saxFromList, p.getSecondElement(), distance);
			}
		}
		
		return chooseClass();
	}
	
	private void conditionalPutInList(SAXEnhanced sax, String classOfObject ,int distance){
		if(currentNeighbors.isEmpty()){
			currentNeighbors.add(sax);
			helper.put(sax, new Pair<Integer, String>(distance, classOfObject));
			return;
		} 
		
		int i = 0;
		for( ; i < currentNeighbors.size(); i++){
			if(distance < helper.get(currentNeighbors.get(i)).getFirstElement()){
				currentNeighbors.add(i, sax);
				helper.put(sax, new Pair<Integer, String>(distance, classOfObject));
				
				if(currentNeighbors.size() > kValue){
					SAXEnhanced toRemove = currentNeighbors.remove(currentNeighbors.size()-1);
					helper.remove(toRemove);
				}
				return;
			}
		}	
		
		if( i < kValue){ //insert at the end of not full list
			currentNeighbors.add(i, sax);
			helper.put(sax, new Pair<Integer, String>(distance, classOfObject));
		}
		
	}
	
	private List<Pair<String, Double>> chooseClass(){
		Map<String, Integer> results = new HashMap<String, Integer>();
		
		for(SAXEnhanced s : currentNeighbors){
			if(results.containsKey(helper.get(s).getSecondElement())){
				int newValue = results.get(helper.get(s).getSecondElement())+1;
				results.put(helper.get(s).getSecondElement(), newValue);
			}else{
				results.put(helper.get(s).getSecondElement(), 1);
			}
		}
		
		List<Pair<String, Double>> resultClasses = new ArrayList<Pair<String,Double>>();
		String newClass = null;		

		while( !results.isEmpty() ){
			newClass = ResultComparator.findBestResult(results);
			resultClasses.add(new Pair<String,Double>(newClass, 
					new Double((double)results.get(newClass)/kValue)) );
			results.remove(newClass);
		}
		
		return resultClasses;
		
	}
	
	private static class ResultComparator{
		
		public static String findBestResult(Map<String, Integer> results){
			int neighbourCounter = Integer.MIN_VALUE;
			String resultClass = null;
			
			for(String temp : results.keySet()){
				if(results.get(temp) > neighbourCounter){
					neighbourCounter = results.get(temp);
					resultClass = temp;
				}
			}
				
			return resultClass;
		}
	}

}
