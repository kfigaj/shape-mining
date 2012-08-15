package sax.utils;

import sax.representation.primitives.PAA;
import sax.representation.primitives.TimeSeries;
import sax.util.test.Settings;

/**
 * Creates PAA representation from timeseries
 */
public class PAACreator {

	/**
	 * Create paa from timeseries.
	 * @param timeSeries - from which paa is created
	 * @param numberOfElements - in paa
	 * @return paa
	 */
	public static PAA create(TimeSeries timeSeries, int numberOfElements){
		double[] paaArray = new double[numberOfElements];
		
		Object[] timeSeriesArray = timeSeries.getTimeSeriesArray();
		int ratio = Settings.TIME_SERIES_NUMBER/numberOfElements;
		double sumRatio =(double)numberOfElements/Settings.TIME_SERIES_NUMBER;
		
		for(int y=1; y <= numberOfElements; y++){
			int xMax = ratio*y;
			double sum = 0;
			for(int x=(ratio*(y-1))+1; x<=xMax; x++)
				sum += (Double)timeSeriesArray[x-1];
			
			paaArray[y-1] = sumRatio*sum;
		}
		
		PAA paa = new PAA();
		paa.setElements(paaArray);
		return paa;
	}
	
	/**
	 * Create all possible PAA representations from timeseries rotations.
	 * 
	 * @param timeSeries - from which paa is created
	 * @param numberOfElements - in paa
	 * @return paa
	 */
	public static PAA[] createAll(TimeSeries timeSeries, int numberOfElements){
		PAA [] paa = new PAA[Settings.TIME_SERIES_NUMBER];
		
		TimeSeries[] timeSeriesRotation = timeSeries.getRotations();
		int ratio = Settings.TIME_SERIES_NUMBER/numberOfElements;
		double sumRatio = (double)numberOfElements/Settings.TIME_SERIES_NUMBER;
		
		for(int i=0; i < Settings.TIME_SERIES_NUMBER; i++){
			double[] paaArray = new double[numberOfElements];
			Object[] timeSeriesArray = timeSeriesRotation[i].getTimeSeriesArray();
			
			for(int y=1; y <= numberOfElements; y++){
				int xMax = ratio*y;
				double sum = 0;
				for(int x=(ratio*(y-1))+1; x<=xMax; x++)
					sum += (Double)timeSeriesArray[x-1];
				
				paaArray[y-1] = sumRatio*sum;
			}
			
			paa[i] = new PAA();
			paa[i].setElements(paaArray);
			paa[i].setTimeSeries(timeSeriesRotation[i]);
		}
		return paa;
	}
}
