package sax.representation.primitives;

import java.awt.image.BufferedImage;

/**
 * Class which represents Image
 */
public class Image {
	private BufferedImage bufferedImage;
	private int[] raster;
	
	private static int RED_MASK = 0x00FF0000;
	private static int GREEN_MASK = 0x0000FF00;
	private static int BLUE_MASK = 0x000000FF;
	private static int COLOR_MASK = 0x00FFFFFF;
	
	private static int RED_SHIFT = 16;
	private static int GREEN_SHIFT = 8;
	
	private static byte BYTE_ZERO = 0x00;
	private static int INT_ZERO = 0;
	private static int INT_ONES = 0x00FFFFFF;
	
	private String name = "";
	
	public Image() {
	}
	
	public Image(BufferedImage bufferedImage, String name) {
		this.bufferedImage = bufferedImage;
		this.name = name;
		
		if(bufferedImage!=null){
			raster = bufferedImage.getRGB(0, 0,
						bufferedImage.getWidth(),
						bufferedImage.getHeight(),
						null, 0, bufferedImage.getWidth());
		}
	}
	
	/**
	 * Add black frame to the image 
	 */
	public void addBlackFrame(){
		for(int y = 0; y < bufferedImage.getHeight(); y++ ){
			for(int x = 0; x < bufferedImage.getWidth(); x++){	
				if(y == 0 || y == bufferedImage.getHeight() - 1)
					raster[y*bufferedImage.getWidth() + x] = 0;
				else if(x == 0 || x == bufferedImage.getWidth() - 1){
					raster[y*bufferedImage.getWidth() + x] = 0;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x - horizontal coordinate 
	 * @param y - vertical coordinate
	 * @return pixel
	 */
	public int getPixel(int x, int y){
		return raster[y*bufferedImage.getWidth() + x];
	}
	
	public byte getRed(int pixelColor){
		return (byte)((pixelColor&RED_MASK) >>> RED_SHIFT);
	}
	
	public byte getGreen(int pixelColor){
		return (byte)((pixelColor&GREEN_MASK) >>> GREEN_SHIFT);
	}
	
	public byte getBlue(int pixelColor){
		return (byte)(pixelColor&BLUE_MASK);
	}
	
	public void setPixelBlue(int x, int y, byte blue){
		raster[y*bufferedImage.getWidth() + x] = 
			(raster[y*bufferedImage.getWidth() + x]&(~BLUE_MASK)) + blue;
	}


	public BufferedImage getBufferedImage() {
		bufferedImage.setRGB(0, 0, bufferedImage.getWidth(),
				bufferedImage.getHeight(), raster, 0, bufferedImage.getWidth());
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public int getHeight(){
		return bufferedImage.getHeight();
	}
	
	public int getWidth(){
		return bufferedImage.getWidth();
	}
	
	/**
	 * 
	 * @param x - horizontal coordinate 
	 * @param y - vertical coordinate
	 * @return true if is black
	 */
	public boolean isPixelBlack(int x, int y){
		int temp = getPixel(x, y);
	
		if( (temp & COLOR_MASK) == INT_ZERO)
			return true;
		else
			return false;		
	}
	
	/**
	 * 
	 * @param x - horizontal coordinate 
	 * @param y - vertical coordinate
	 * @return true if is white
	 */
	public boolean isPixelWhite(int x, int y){
		int temp = getPixel(x, y);
		
		if( (temp & COLOR_MASK) == INT_ONES)
			return true;
		else
			return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
