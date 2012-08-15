package sax.util.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import sax.representation.primitives.Image;
import sax.representation.primitives.PAA;
import sax.representation.primitives.SAX;
import sax.representation.primitives.TimeSeries;
import sax.utils.ImageMenager;
import sax.utils.PAACreator;
import sax.utils.TimeSeriesCreator;

public class SAXCreatorTest {

	/**
	 * As a field in order to make tests easier.
	 */
	public static TimeSeriesCreator timeSeriesCreator = new TimeSeriesCreator();

	public static SAX create(Image image){
		TimeSeries timeSeries;
		try {
			timeSeries = timeSeriesCreator.create(image);
			//
//			testDump("timeSeriesTS.txt", "", timeSeries.getTimeSeriesArray());
			
			if(timeSeries.getTimeSeriesArray().length == 0)
				System.out.print("Zly obraz: "+image.getName());
				
			TimeSeries normalizedTimeSeries = TimeSeries.normalize(timeSeries);
			
			// 
//			testDump("normalizedTS.txt", "", normalizedTimeSeries.getTimeSeriesArray());
			System.out.print("obraz - ilosc elementow: "+timeSeries.getTimeSeriesArray().length);
			TimeSeries reductedTimeSeries = TimeSeries.reduce(normalizedTimeSeries,
					Settings.TIME_SERIES_NUMBER);
			
			//
//			testDump("reducedTS.txt", "", reductedTimeSeries.getTimeSeriesArray());
			
			PAA paa = PAACreator.create(reductedTimeSeries,
					Settings.PAA_ELEMENTS_NUMBER);
			
			//
			//testDump("paa", "", paa.getElements());
			
			SAX sax = discretization(paa, Settings.ALPHABET_NUMBER);
			if(sax != null) {
				sax.setTimeSeries(reductedTimeSeries);
				sax.setAlphabetCount(Settings.ALPHABET_NUMBER);
			}
			
			
			return sax;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

	}
	
	private static void testDump(String fileName, String desc, Object[] toDump){
		if(fileName != null && toDump != null){
			File file = new File(fileName);
			Writer out;
			try {
				out = new BufferedWriter(new FileWriter(file));			
				//out.write(desc);
				
				for(int i=0; i < toDump.length; i++){
					double temp = (Double) toDump[i];
					out.write(temp+";\n");
				}
				
				out.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
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
