package discordfinder.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sax.representation.primitives.Image;
import sax.utils.ImageMenager;
import sax.utils.SAXCreator;
import utils.sax.RandomNumbersGenerator;
import utils.sax.Timer;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.DiscordFinderAlgorithm;
import discordfinder.algorithm.bruteforce.ImprovedBruteForce;
import discordfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;

/**
 * Effeciency tests for quirk finder algorithm.
 * 
 * 
 */
public class EffeciencyTest {

	private static final int STEP = 50;
	private static final String IMAGES_PATH = "test_image_7";

	public static void main(String[] args) {
		EffeciencyTest test = new EffeciencyTest();
		test.performEffeciencyTest(new LocalSensivityHashingAlgorithm());
		test.performEffeciencyTest(new ImprovedBruteForce());
	}

	public void performEffeciencyTest(DiscordFinderAlgorithm algorithm) {
		System.out
				.println("------------------------------------------------------");
		System.out.println(algorithm.toString() + " test:");
		testEffeciency(algorithm, IMAGES_PATH, STEP);
	}

	public void testEffeciency(DiscordFinderAlgorithm algorthm,
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
				List<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
				for (Image image : chosenImages) {
					saxes.add(new SAXEnhanced(image.getName(), SAXCreator.create(image)));
				}
				algorthm.findDiscord(saxes);
				System.out.println(numberOfImages + "\t" + t.elapsed());
			}
		} else {
			throw new IllegalArgumentException(imagesDirPath
					+ " is not a directory");
		}
	}
}
