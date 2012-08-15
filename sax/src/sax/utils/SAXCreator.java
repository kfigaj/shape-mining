package sax.utils;

import java.util.List;

import sax.representation.primitives.Image;
import sax.representation.primitives.PAA;
import sax.representation.primitives.SAX;
import sax.representation.primitives.TimeSeries;
import sax.util.test.Settings;

/**
 * Class which creates SAX representation from image
 *
 */
public class SAXCreator {
	
	/**
	 * As a field in order to make tests easier.
	 */
	public static TimeSeriesCreator timeSeriesCreator = new TimeSeriesCreator();

	public static SAX create(Image image){
		TimeSeries timeSeries;
		try {
			timeSeries = timeSeriesCreator.create(image);
			
			if(timeSeries.getTimeSeriesArray().length == 0)
				throw new IllegalArgumentException("Wrong image format: " + image.getName());
			
			return getSaxFromTimeseries(timeSeries);
		} catch(IllegalArgumentException e){
			throw e;
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
			return null;
		}

	}
	
	public static SAX getSaxFromTimeseries(TimeSeries timeSeries){
		TimeSeries normalizedTimeSeries = TimeSeries.normalize(timeSeries);
		
		TimeSeries reductedTimeSeries = TimeSeries.reduce(normalizedTimeSeries,
				Settings.TIME_SERIES_NUMBER);
		
		SAX sax = null;
		
		if(Settings.USE_RECONSTRUCTION_ERROR){
			PAA[] paa = PAACreator.createAll(reductedTimeSeries,
					Settings.PAA_ELEMENTS_NUMBER);
			
			sax = discretizationWithError(paa, Settings.ALPHABET_NUMBER);
		}else{
			PAA paa = PAACreator.create(reductedTimeSeries,
					Settings.PAA_ELEMENTS_NUMBER);
					
			sax = discretization(paa, Settings.ALPHABET_NUMBER);
		}
		if(sax != null) {
			sax.setTimeSeries(reductedTimeSeries);
			sax.setAlphabetCount(Settings.ALPHABET_NUMBER);
			sax.setOriginal(timeSeries);
		}	
		
		return sax;
	}
	

	public static SAX create(String imageName){
		Image newImage = ImageMenager.loadImage(imageName);	
		return create(newImage);
	}
	
	private static SAX discretization(PAA paa, int numberOfLetters){
		SAX sax = new SAX();
		String result = "";
		if(Settings.breakpoints.containsKey(numberOfLetters)){
			List<Double> breakPoints = Settings.breakpoints.get(numberOfLetters);
			double[] paaElements = paa.getElements();
			for(double element: paaElements){
				result += getLetter(breakPoints, element);
			}
			sax.setSax(result);
			return sax;
		}
		else
			return null;
	}
	
	private static SAX discretizationWithError(PAA[] paa, int numberOfLetters){
		if(Settings.breakpoints.containsKey(numberOfLetters)){
			SAX [] saxes = new SAX[Settings.TIME_SERIES_NUMBER];
			double[] errors = new double[Settings.TIME_SERIES_NUMBER];
			List<Double> breakPoints = Settings.breakpoints.get(numberOfLetters);
			Double[] errorBreakPoints = Settings.breakpoints.get(2*numberOfLetters).toArray(new Double[0]);
			int ratio = Settings.TIME_SERIES_NUMBER/Settings.PAA_ELEMENTS_NUMBER;
			
			for(int i=0; i < Settings.TIME_SERIES_NUMBER; i++){
				String result = "";
				saxes[i] = new SAX();
				double[] paaElements = paa[i].getElements();
				//create sax
				for(double element: paaElements){
					result += getLetter(breakPoints, element);
				}
				saxes[i].setSax(result);
				//count error
				Object[] timeSeriesArray = paa[i].getTimeSeries().getTimeSeriesArray();
				errors[i] = 0;
				int saxLetterNumber = 0;
				char saxLetter;
				double bValue = 0;
				
				for(int y=1; y <= Settings.PAA_ELEMENTS_NUMBER; y++){
					int xMax = ratio*y;
					saxLetter = result.charAt(y - 1);
					saxLetterNumber = saxLetter - 'A';
					bValue = errorBreakPoints[2*saxLetterNumber];
					for(int x=(ratio*(y-1))+1; x<=xMax; x++)
						errors[i] += Math.pow( ((Double)timeSeriesArray[x-1] - bValue), 2);
				}

				errors[i] = Math.sqrt(errors[i]);
			}
			//get sax with min error	
			double compareValue = Double.MAX_VALUE;
			int resultIndex=0;
			for(int i=0; i < Settings.TIME_SERIES_NUMBER; i++){
				if(errors[i] < compareValue){
					compareValue = errors[i];
					resultIndex = i;
				}
			}
			return saxes[resultIndex];
		}
		else
			return null;
	}
	
	
	
	private static char getLetter(List<Double> breakPoints, double element){
		char result = 'A';
		
		for(Double breakPoint: breakPoints){
			if(element < breakPoint){
				return result;
			}
			result++;
		}
		
		return result;
	}
}
