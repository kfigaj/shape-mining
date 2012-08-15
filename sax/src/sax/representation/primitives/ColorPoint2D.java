package sax.representation.primitives;

import java.awt.geom.Point2D;

/**
 * Class that represents image pixel with coordinates (0,0) in top left corner
 * and color in RGB standard
 * 
 *
 */
public class ColorPoint2D extends Point2D {
	
	private double xCoordinate;
	private double yCoordinate;
	private RGBColor color;

	@Override
	public double getX() {
		return xCoordinate;
	}

	@Override
	public double getY() {
		return yCoordinate;
	}

	@Override
	public void setLocation(double xCoordinate, double yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public RGBColor getColor() {
		return color;
	}

	public void setColor(RGBColor color) {
		this.color = color;
	}

	/**
	 * RGB color structure
	 */
	public class RGBColor {
		private int red;
		private int green;
		private int blue;
		
		public RGBColor(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		public int getRed() {
			return red;
		}

		public void setRed(int red) {
			this.red = red;
		}

		public int getGreen() {
			return green;
		}

		public void setGreen(int green) {
			this.green = green;
		}

		public int getBlue() {
			return blue;
		}

		public void setBlue(int blue) {
			this.blue = blue;
		}
		
	}

}