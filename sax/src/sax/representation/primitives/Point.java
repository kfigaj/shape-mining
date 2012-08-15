package sax.representation.primitives;

/**
 * Class that represents point
 *
 */
public class Point extends Pair<Integer, Integer>{

	public Point() {
		super();
	}
	
	public Point(int x, int y) {
		super(x,y);
	}
	
	public Point(Point p){
		super();
		firstElement = new Integer(p.getX());
		secondElement = new Integer(p.getY());
	}
	
	public Point getPoint(){
		return this;
	}
	
	public void setPoint(int x, int y){
		setFirstElement(x);
		setSecondElement(y);
	}
	
	public int getX(){
		return getFirstElement();
	}
	
	public int getY(){
		return getSecondElement();
	}
	
	public void setX(int x){
		setFirstElement(x);
	}
	
	public void setY(int y){
		setSecondElement(y);
	}
	
	public boolean isEqual(Point p){
		if(firstElement.equals(p.getFirstElement()) &&
			secondElement.equals(p.getSecondElement()))
			return true;
		else
			return false;
	}
}
