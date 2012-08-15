package sax.utils;

import java.util.HashMap;

/**
 * Marching Square algorithm convenience class
 *
 */
public class MarchingSquare {
	
	private static HashMap<String , Direction> marchingSquare = new HashMap<String, Direction>();

	
	/*
	 * (a,b,c,d) are placed in square
	 * 
	 * a	b
	 * c	d
	 * 
	 * 0 - pixel of background
	 * 1 - pixel of object
	 * 
	 */
	
	/*
	 * Marching square algorithm - direction choosing
	 */
	static {
		marchingSquare.put("0000", Direction.RIGHT);
		marchingSquare.put("0001", Direction.DOWN);
		marchingSquare.put("0010", Direction.LEFT);
		marchingSquare.put("0011", Direction.LEFT);
		marchingSquare.put("0100", Direction.RIGHT);
		marchingSquare.put("0101", Direction.DOWN);
		marchingSquare.put("0111", Direction.LEFT);
		marchingSquare.put("1000", Direction.UP);
		marchingSquare.put("1010", Direction.UP);
		marchingSquare.put("1011", Direction.UP);
		marchingSquare.put("1100", Direction.RIGHT);
		marchingSquare.put("1101", Direction.DOWN);
		marchingSquare.put("1110", Direction.RIGHT);
		marchingSquare.put("1111", Direction.ERROR);
	}
	
	public static Direction whereToGo(String square, Direction previous){
		//check special cross-cases
		Direction temp;
		if("0110".equals(square)){
			if(Direction.UP.equals(previous))
				temp = Direction.LEFT;
			else
				temp = Direction.RIGHT;
		}
		else if("1001".equals(square)){
			if(Direction.RIGHT.equals(previous))
				temp = Direction.UP;
			else
				temp = Direction.DOWN;
		}
		else
			temp = marchingSquare.get(square);
		
		if(temp != null)
			return temp;
		else 
			return Direction.ERROR;
	}
	
	public static enum Direction{
		LEFT,
		RIGHT,
		UP,
		DOWN,
		ERROR
	};
}
