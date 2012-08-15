package sax.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sax.representation.primitives.Image;

/***
 * Image Manager - loads and saves images
 *
 */
public class ImageMenager {

	/**
	 * 
	 * @param filePath
	 * @return image
	 */
	public static Image loadImage(String filePath){
		BufferedImage tempBufferedImage;
		
		try {
			tempBufferedImage = ImageIO.read(new File(filePath));
			if(tempBufferedImage != null)
				return new Image(tempBufferedImage, filePath);
			else
				return null;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param imageToSave
	 * @param formatName
	 * @param filePath
	 */
	public static void saveImage(BufferedImage imageToSave,
			String formatName, String filePath){
		
		try {
			ImageIO.write(imageToSave, formatName, new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
