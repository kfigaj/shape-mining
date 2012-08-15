package discordfinder.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sax.representation.primitives.Image;
import sax.utils.ImageMenager;
import sax.utils.SAXCreator;
import utils.sax.representation.SAXEnhanced;
import utils.sax.test.ImageDisplayer;


import discordfinder.algorithm.DiscordFinderAlgorithm;
import discordfinder.algorithm.bruteforce.ImprovedBruteForce;
import discordfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;

public class Test {

	public static String test1 = "test_image_1";

	public static int BRUTE = 0;
	public static int LOCAL = 1;

	public static void main(String args[]) {
		String file[] = new String[1];
		file[0] = args[0];
		int algorithmType = Integer.parseInt(args[1]);

		Test test = new Test();

		test.findQuirkInFiles(file, algorithmType);
	}

	/**
	 * Find quirks in given directories and files using ALGORITHM specified by
	 * type.
	 * 
	 * @param imageNames
	 * @param type
	 *            - algorithm type 0 - Brute 1 - Local
	 */
	public void findQuirkInFiles(String imageNames[], int type) {

		for (String imageName : imageNames) {
			try {
				File file = new File(imageName);

				if (file.isDirectory()) {
					String[] childrenFiles = file.list();
					List<Image> images = new ArrayList<Image>();
					for (String s : childrenFiles) {
						String imagePath = imageName + "/" + s;
						if (!new File(imagePath).isDirectory()) {
							images.add(ImageMenager.loadImage(imagePath));
						}
					}
					List<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
					for (Image image : images) {
						saxes.add(new SAXEnhanced(image.getName(), SAXCreator.create(image)));
					}

					if (type == BRUTE) {
						ImprovedBruteForce finder = new ImprovedBruteForce();
						findDiscord(finder, saxes);
					} else if (type == LOCAL) {
						LocalSensivityHashingAlgorithm finder = new LocalSensivityHashingAlgorithm();
						findDiscord(finder, saxes);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void findDiscord(DiscordFinderAlgorithm finder, List<SAXEnhanced> saxes) {
		long timeBefore = System.currentTimeMillis();
		Image quirk = ImageMenager.loadImage(finder.findDiscord(saxes).getImagePath());
		System.out.print(finder.toString() + ": Discord is:" + quirk.getName()
				+ " found in " + (System.currentTimeMillis() - timeBefore)
				+ "[ms]");
		ImageDisplayer.display(quirk.getBufferedImage(), finder.toString());
	}

}
