package discordfinder.algorithm.localsensivityhashing.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.localsensivityhashing.OptimalApproximation;
import discordfinder.algorithm.localsensivityhashing.OptimalApproximation.Loop;

public class OptimalApproximationTest {

	private OptimalApproximation approximation;

	private int[][] collisionMatrix = new int[][] { { 1, 2 }, { 3, 4 } };

	@Before
	public void setUp() throws Exception {
		List<SAXEnhanced> saxes = new ArrayList<SAXEnhanced>();
		for (int i = 0; i < collisionMatrix.length; ++i) {
			saxes.add(new TestSAXEnhanced(i));
		}
		approximation = new OptimalApproximation(saxes, collisionMatrix);
	}

	@Test
	public void testGetOuterLoop() {
		Loop outerLoop = approximation.getOuterLoop();
		Iterator<SAXEnhanced> it = outerLoop.iterator();
		assertEquals(0, ((TestSAXEnhanced)it.next()).getI());
		assertEquals(1, ((TestSAXEnhanced)it.next()).getI());
	}

	@Test
	public void testGetInnerLoop() {
		Loop outerLoop = approximation.getOuterLoop();
		Iterator<SAXEnhanced> outerIterator = outerLoop.iterator();
		outerIterator.next();
		Loop innerLoop = approximation.getInnerLoop();
		Iterator<SAXEnhanced> innerIterator = innerLoop.iterator();
		assertEquals(1, ((TestSAXEnhanced)innerIterator.next()).getI());
		assertEquals(0, ((TestSAXEnhanced)innerIterator.next()).getI());
	}
	
	@Test
	public void testGetInnerLoopSecondIteration() {
		Loop outerLoop = approximation.getOuterLoop();
		Iterator<SAXEnhanced> outerIterator = outerLoop.iterator();
		outerIterator.next();
		outerIterator.next();
		Loop innerLoop = approximation.getInnerLoop();
		Iterator<SAXEnhanced> innerIterator = innerLoop.iterator();
		assertEquals(1, ((TestSAXEnhanced)innerIterator.next()).getI());
		assertEquals(0, ((TestSAXEnhanced)innerIterator.next()).getI());
	}

	private class TestSAXEnhanced extends SAXEnhanced {
		private int i;

		public TestSAXEnhanced(int i) {
			super(null);
			this.i = i;
		}

		public int getI() {
			return i;
		}
	}

}
