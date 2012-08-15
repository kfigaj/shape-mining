package com.googlecode.wmh;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sax.representation.Image;
import sax.utils.ImageMenager;

import com.googlecode.wmhshapes.quirkfinder.algorithm.QuirkFinderAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.algorithm.bruteforce.ImprovedBruteForce;
import com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.utils.ImageDisplayer;

public class WMHTest {

	public static String test1 = "test_image_1";
	public static String test2 = "test_image_2";
	public static String test3 = "test_image_3";
	public static String test4 = "test_image_4";
	public static String test5 = "test_image_5";

	public static int BRUTE = 0;
	public static int LOCAL = 1;

	public static void main(String args[]) {
		String file[] = new String[1];
		file[0] = args[0];
		int algorithmType = Integer.parseInt(args[1]);

		WMHTest test = new WMHTest();

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

					if (type == BRUTE) {
						ImprovedBruteForce finder = new ImprovedBruteForce();
						findQuirk(finder, images);
					} else if (type == LOCAL) {
						LocalSensivityHashingAlgorithm finder = new LocalSensivityHashingAlgorithm();
						findQuirk(finder, images);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void findQuirk(QuirkFinderAlgorithm finder, Collection<Image> images) {
		long timeBefore = System.currentTimeMillis();
		Image quirk = finder.findQuirk(images);
		System.out.print(finder.toString() + ": Discord is:" + quirk.getName()
				+ " found in " + (System.currentTimeMillis() - timeBefore)
				+ "[ms]");
		ImageDisplayer.display(quirk.getBufferedImage(), finder.toString());
	}

}
