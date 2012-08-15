package classification.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sax.representation.primitives.Image;
import sax.representation.primitives.Pair;
import sax.utils.ImageMenager;
import sax.utils.SAXCreator;
import utils.sax.representation.SAXEnhanced;

import classification.algorithm.nearestneighbour.KNearestNeighbour;


/**
 * Simple test for KNN classification algorithm
 *
 */
public class ClassificationTest {
	
	private List<Pair<SAXEnhanced, String>> currentClassification;
	private static int KVALUE = 10;
	
	public static void main(String args[]){
		ClassificationTest test = new ClassificationTest();
		test.prepareToClassify();
		test.showClasses();
		test.classify();

	}

	private void showClasses(){
		for(Pair<SAXEnhanced, String> p : currentClassification){
			System.out.print(p.getFirstElement().getImagePath()
					+ " is in class:" + p.getSecondElement() + "\n");
		}
	}
	
	private void prepareToClassify(){
		
		currentClassification = new ArrayList<Pair<SAXEnhanced, String>>();
		
		try {
			String dir = "test/classes";
			File file = new File(dir);

			if (file.isDirectory()) {
				String[] classDirs = file.list();
				for (String newClass : classDirs) {
					String classPath = dir + "/" + newClass;
					File newClassDir = new File(classPath);
					if(newClassDir.isDirectory()){
						String[] pics = newClassDir.list();
						for (String newPic : pics) {
							String imagePath = classPath + "/" + newPic;
							if (!new File(imagePath).isDirectory()) {
								Image image = ImageMenager.loadImage(imagePath);
								currentClassification.add(new Pair<SAXEnhanced, String>(
										new SAXEnhanced(imagePath, SAXCreator.create(image)),
										newClass
										));
							}
						}
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void classify(){
	
		try {
			KNearestNeighbour classifier = new KNearestNeighbour();
			String dirToClassify = "test/toClassify";
			File file = new File(dirToClassify);

			if (file.isDirectory()) {
				String[] filesToClassify = file.list();
				for (String newPic : filesToClassify) {
					String imagePath = dirToClassify + "/" + newPic;
					if (!new File(imagePath).isDirectory()) {
						Image image = ImageMenager.loadImage(imagePath);
						List<Pair<String, Double>> result = classifier.classify(currentClassification, 
								new SAXEnhanced(imagePath, SAXCreator.create(image)), KVALUE);
						
						for(Pair<String, Double> s: result){
							System.out.print(imagePath + " is classified as: " + s.getFirstElement() +
									" with support of " + s.getSecondElement()*100 + "% from " + KVALUE + " neighbours\n");
						}
							
						System.out.println();
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
