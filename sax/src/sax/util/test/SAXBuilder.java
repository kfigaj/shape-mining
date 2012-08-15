package sax.util.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import sax.representation.primitives.SAX;
import sax.utils.SAXCreator;

/**
 * Class that initialize the process of creation of SAX representation.
 * 
 * 
 */
public class SAXBuilder {

	private HashMap<String, SAX> saxRepresentations = new HashMap<String, SAX>();;

	public static String test1 = "C:/Documents and Settings/Krzysztof/Pulpit/Studia/Semestr9/pdm-1/4/original/hat-5.gif";

	public static String test2 = "test_image/apple-1.gif";
	public static String test3 = "test_image/temp.bmp";
	public static String test4 = "test_image/temp3.bmp";
	public static String test6 = "test_image/camel-2.gif";

	public static String test5 = "test_image_4";
	public static String test7 = "test_image";
	

	public static void main(String args[]) {
		String names[] = new String[] { test1 };
		SAXBuilder saxBuilder = new SAXBuilder();
		saxBuilder.createSAXFromFiles(names);
		saxBuilder.displaySAX();
	}

//	public SAXBuilder() {
//		saxRepresentations = new HashMap<String, SAX>();
//	}

	public void createSAXFromFiles(String imageNames[]) {

		for (String imageName : imageNames) {
			try {
				File file = new File(imageName);

				if (file.isDirectory()) {
					String[] childrenFiles = file.list();
					for (String s : childrenFiles) {
						String imagePath = imageName + "/" + s;
						if (!new File(imagePath).isDirectory()) {
							System.out.println("Procesing file:" + imagePath);
							saxRepresentations.put(imagePath, SAXCreator
									.create(imagePath));
						}
					}
				} else {
					System.out.println("Procesing file:" + imageName);
					saxRepresentations.put(imageName, SAXCreator
							.create(imageName));
				}
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}

	}

	public void displaySAX() {
		Set<String> keySAX = saxRepresentations.keySet();
		// sort files
		ArrayList<String> list = new ArrayList<String>(keySAX);
		Collections.sort(list);
		
		// display
		for (String s : list) {
			System.out.println("SAX for: " + s + "\n"
					+ saxRepresentations.get(s).getSax());
		}
	}

	public HashMap<String, SAX> getSAXRepresentations() {
		return saxRepresentations;
	}

}
