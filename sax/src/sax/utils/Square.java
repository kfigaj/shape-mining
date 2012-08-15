package sax.utils;

import java.util.HashMap;

import sax.representation.primitives.Pair;
import sax.representation.primitives.Point;
import sax.utils.MarchingSquare.Direction;
import sax.utils.TimeSeriesCreator.Boundary;

/**
 * Representation of square for marching squares algorithm 
 *
 */
public class Square {
	
private static HashMap<String , Pair<Integer,Integer>> pointIndex =
	new HashMap<String, Pair<Integer,Integer>>();
	
	/*
	 * (a,b,c,d) are placed in square
	 * point[0] - a
	 * point[1] - b
	 * point[2] - c
	 * point[3] - d
	 * 
	 * a	b
	 * c	d
	 * 
	 * 0 - pixel of background
	 * 1 - pixel of object
	 * 
	 */

	private static int A = 0;
	private static int B = 1;
	private static int C = 2;
	private static int D = 3;
	
	//which points should be added
	static {
		pointIndex.put("0000", new Pair<Integer,Integer>());
		pointIndex.put("0001", new Pair<Integer,Integer>(D, null));
		pointIndex.put("0010", new Pair<Integer,Integer>(C, null));
		pointIndex.put("0011", new Pair<Integer,Integer>(C, null));
		pointIndex.put("0100", new Pair<Integer,Integer>(B, null));
		pointIndex.put("0101", new Pair<Integer,Integer>(D, null));
		pointIndex.put("0110", new Pair<Integer,Integer>(B, C));
		pointIndex.put("0111", new Pair<Integer,Integer>(D, C));
		pointIndex.put("1000", new Pair<Integer,Integer>(A, null));
		pointIndex.put("1001", new Pair<Integer,Integer>(D, A));
		pointIndex.put("1010", new Pair<Integer,Integer>(A, null));
		pointIndex.put("1011", new Pair<Integer,Integer>(C, A));
		pointIndex.put("1100", new Pair<Integer,Integer>(B, null));
		pointIndex.put("1101", new Pair<Integer,Integer>(B, D));
		pointIndex.put("1110", new Pair<Integer,Integer>(A, B));
		pointIndex.put("1111", new Pair<Integer,Integer>());
	}

	private Point point[];
	
	public Square() {
		point = new Point[4];
		
		for(int i=0; i<4; i++)
			point[i] = new Point();
	}
	
	public Square(Point x1,
			Point x2, Point x3, Point x4){
		point = new Point[4];
		point[0] = x1;
		point[1] = x2;
		point[2] = x3;
		point[3] = x4;
	}
	
	public void setSquare(Point x1,
			Point x2, Point x3, Point x4){
		point[0] = x1;
		point[1] = x2;
		point[2] = x3;
		point[3] = x4;		
	}
	
	public Point[] getSquare(){
		return point;
	}
	
	public void move(Direction direction){
		if(direction.equals(Direction.UP)){
			for(int i=0; i<4; i++)
				point[i].setY( point[i].getY() - 1 );
		}
		else if(direction.equals(Direction.RIGHT)){
			for(int i=0; i<4; i++)
				point[i].setX( point[i].getX() + 1 );
		}		
		else if(direction.equals(Direction.DOWN)){
			for(int i=0; i<4; i++)
				point[i].setY( point[i].getY() + 1 );
		}
		else if(direction.equals(Direction.LEFT)){
			for(int i=0; i<4; i++)
				point[i].setX( point[i].getX() - 1 );
		}
	}
	
	public void addPointsToBoundary(String squareType, Boundary boundary){
		Pair<Integer,Integer> points = pointIndex.get(squareType);
		
		if(points.getFirstElement() != null &&
				!boundary.getPoints().lastElement().isEqual( // add new points only
						point[points.getFirstElement()])){
			
			boundary.add(new Point(point[points.getFirstElement()]));
			// check if boundary is closed after last point has been added
			if(boundary.getPoints().firstElement().isEqual(
					boundary.getPoints().lastElement())){
					boundary.setClosed(true);	
					return;
			}
			
			if(points.getSecondElement() != null){	
				boundary.add(new Point(point[points.getSecondElement()]));
				// check if boundary is closed after last point has been added
				if(boundary.getPoints().firstElement().isEqual(
						boundary.getPoints().lastElement())){
						boundary.setClosed(true);	
				}
			}
			
		}
	}
	
}
