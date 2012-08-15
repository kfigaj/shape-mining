package com.googlecode.wmh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sax.representation.Image;
import sax.utils.ImageMenager;

import com.googlecode.wmhshapes.quirkfinder.algorithm.QuirkFinderAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.algorithm.bruteforce.ImprovedBruteForce;
import com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;
import com.googlecode.wmhshapes.quirkfinder.utils.RandomNumbersGenerator;
import com.googlecode.wmhshapes.quirkfinder.utils.Timer;

/**
 * Effeciency tests for quirk finder algorithm.
 * 
 * @author Dawid Pytel
 * 
 */
public class WMHEffeciencyTest {

	private static final int STEP = 50;
	private static final String IMAGES_PATH = "test_image_7";

	public static void main(String[] args) {
		WMHEffeciencyTest test = new WMHEffeciencyTest();
		test.performEffeciencyTest(new LocalSensivityHashingAlgorithm());
		test.performEffeciencyTest(new ImprovedBruteForce());
	}

	public void performEffeciencyTest(QuirkFinderAlgorithm algorithm) {
		System.out
				.println("------------------------------------------------------");
		System.out.println(algorithm.toString() + " test:");
		testEffeciency(algorithm, IMAGES_PATH, STEP);
	}

	public void testEffeciency(QuirkFinderAlgorithm algorthm,
			String imagesDirPath, int step) {
		File file = new File(imagesDirPath);
		if (file.isDirectory()) {
			String[] childrenFiles = file.list();
			List<Image> images = new ArrayList<Image>();
			for (String s : childrenFiles) {
				String imagePath = imagesDirPath + "/" + s;
				if (!new File(imagePath).isDirectory()) {
					images.add(ImageMenager.loadImage(imagePath));
				}
			}

			RandomNumbersGenerator rand = new RandomNumbersGenerator();
			for (int numberOfImages = step; numberOfImages < images.size(); numberOfImages += step) {
				List<Image> chosenImages = new ArrayList<Image>();
				int[] indices = rand.getNRandomInts(numberOfImages, images
						.size());
				for (int i : indices) {
					chosenImages.add(images.get(i));
				}
				Timer t = new Timer();
				algorthm.findQuirk(chosenImages);
				System.out.println(numberOfImages + "\t" + t.elapsed());
			}
		} else {
			throw new IllegalArgumentException(imagesDirPath
					+ " is not a directory");
		}
	}
}
