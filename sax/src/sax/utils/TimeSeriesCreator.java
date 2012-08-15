package sax.utils;

import java.util.Vector;

import sax.representation.primitives.Image;
import sax.representation.primitives.Pair;
import sax.representation.primitives.Point;
import sax.representation.primitives.TimeSeries;
import sax.utils.MarchingSquare.Direction;

/**
 * Creates TimeSeries from shape images.
 * 
 */
public class TimeSeriesCreator {

	private int imageWidth;
	private int imageHeight;
	private Point firstObjectPixel;

	public TimeSeriesCreator() {
	}

	public TimeSeries create(Image image) throws Exception {

		Boundary imageBoundary = createBoundary(image);
		Pair<Double, Double> central = countCentralPoint(image);
		TimeSeries timeSeries = createTimeSeries(imageBoundary, central);
		assert timeSeries.getTimeSeriesVector().size() > 0 : "Empty vector for image "
				+ image.getName();
		return timeSeries;
	}

	public Boundary createBoundary(Image image) throws Exception {
		// algorithm assumes that the object is not connected to the image frame
		// thus we have to assure it
		image.addBlackFrame();

		imageWidth = image.getWidth() - 1;
		imageHeight = image.getHeight() - 1;
		firstObjectPixel = null;
		Boundary boundary = new Boundary();

		for (int y = 1; y < imageHeight; y++) {
			for (int x = 1; x < imageWidth; x++) {
				// remember this pixel to make further operation efficient
				if (image.isPixelWhite(x, y)) {
					boundary.add(new Point(x, y));
					firstObjectPixel = new Point(x, y);
					String type;
					Direction direction = null;

					// Marching Square algorithm
					Square square = new Square(new Point(x - 1, y), new Point(
							x, y), new Point(x - 1, y + 1), new Point(x, y + 1));
					do {
						// check type of boundary
						type = getSquareType(square, image);
						// add necessary points do boundary
						square.addPointsToBoundary(type, boundary);
						// move square
						direction = MarchingSquare.whereToGo(type, direction);


						if (!direction.equals(Direction.ERROR))
							square.move(direction);
						else
							throw new Exception("Time series cannot be done from this image");
					} while (!boundary.isClosed()); // when boundary is closed
					return boundary;
				}// if
			}// for
		}// for
		return boundary;
	}

	private String getSquareType(Square square, Image image) {
		String type = "";
		Point[] points = square.getSquare();

		for (int i = 0; i < 4; i++) {
			type += getPixelType(points[i].getX(), points[i].getY(), image);
		}
		return type;
	}

	private String getPixelType(int x, int y, Image image) {
		if (image.isPixelWhite(x, y))
			return "1";
		else
			return "0";
	}

	private Pair<Double, Double> countCentralPoint(Image image) {
		if (firstObjectPixel == null)
			return null;

		int y = firstObjectPixel.getY();
		int x = firstObjectPixel.getX();

		int M00, M10, M01;
		M00 = M10 = M01 = 0;

		for (; y < imageHeight; y++) {
			for (; x < imageWidth; x++) {
				// count moments
				if (image.isPixelWhite(x, y)) {
					M00 += 1;
					M10 += x;
					M01 += y;
				}
			}
			x = 0;
		}

		// count central point
		if (M00 != 0) {
			Pair<Double, Double> central = new Pair<Double, Double>();
			Double xCentral = new Double(M10 / M00);
			Double yCentral = new Double(M01 / M00);
			central.setFirstElement(xCentral);
			central.setSecondElement(yCentral);
			return central;
		} else
			return null;
	}

	private TimeSeries createTimeSeries(Boundary boundary,
			Pair<Double, Double> central) {
		TimeSeries timeSeries = new TimeSeries();

		for (Point p : boundary.getPoints()) {
			timeSeries.add(countDistance(p, central));
		}
		return timeSeries;
	}

	private Double countDistance(Point p, Pair<Double, Double> central) {
		double xPart = Math.pow(Math.abs(p.getX() - central.getFirstElement()),
				2);
		double yPart = Math.pow(
				Math.abs(p.getY() - central.getSecondElement()), 2);
		return Math.sqrt(xPart + yPart);
	}

	// boundary class
	public class Boundary {
		private Vector<Point> points;
		private boolean closed;

		public Boundary() {
			points = new Vector<Point>();
			closed = false;
		}

		public void add(Point point) {
			points.add(point);
		}

		public Vector<Point> getPoints() {
			return points;
		}

		public boolean isClosed() {
			return closed;
		}

		public void setClosed(boolean closed) {
			this.closed = closed;
		}
	}

}
