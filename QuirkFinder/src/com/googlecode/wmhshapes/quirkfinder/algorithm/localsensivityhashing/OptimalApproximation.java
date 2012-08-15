package com.googlecode.wmhshapes.quirkfinder.algorithm.localsensivityhashing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.googlecode.wmhshapes.quirkfinder.sax.representation.SAXEnhanced;

/**
 * @author Dawid Pytel
 * 
 */
public class OptimalApproximation {

	private List<SAXEnhanced> saxes;
	private int[][] collisionMatrix;

	public OptimalApproximation(List<SAXEnhanced> saxes, int[][] collisionMatrix) {
		assert saxes.size() == collisionMatrix.length;
		this.saxes = saxes;
		this.collisionMatrix = collisionMatrix;
	}

	public interface Loop extends Iterable<SAXEnhanced> {

	}

	private Iterator<SAXCollisions> outerLoopIterator;

	private SAXCollisions currentOuterSAX;

	public Loop getOuterLoop() {
		final SAXCollisions[] indices = new SAXCollisions[saxes.size()];
		for (int i = 0; i < collisionMatrix.length; ++i) {
			int max = 0;
			for (int collisions : collisionMatrix[i]) {
				if (collisions > max) {
					max = collisions;
				}
			}
			indices[i] = new SAXCollisions(saxes.get(i), max, i);
		}
		Arrays.sort(indices, new Comparator<SAXCollisions>() {

			@Override
			public int compare(SAXCollisions collisions1,
					SAXCollisions collisions2) {
				return collisions1.collisions - collisions2.collisions;
			}

		});
		outerLoopIterator = Arrays.asList(indices).iterator();
		return new Loop() {
			@Override
			public Iterator<SAXEnhanced> iterator() {
				return new Iterator<SAXEnhanced>() {

					@Override
					public boolean hasNext() {
						return outerLoopIterator.hasNext();
					}

					@Override
					public SAXEnhanced next() {
						currentOuterSAX = outerLoopIterator.next();
						return currentOuterSAX.sax;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}

		};
	}

	public Loop getInnerLoop() {
		int currentSaxIndex = currentOuterSAX.index;
		final SAXCollisions[] collisions = new SAXCollisions[saxes.size()];
		for (int i = 0; i < collisionMatrix[currentSaxIndex].length; ++i) {
			collisions[i] = new SAXCollisions(saxes.get(i),
					collisionMatrix[currentSaxIndex][i], i);
		}
		Arrays.sort(collisions, new Comparator<SAXCollisions>() {

			@Override
			public int compare(SAXCollisions collisions1,
					SAXCollisions collisions2) {
				return collisions2.collisions - collisions1.collisions;
			}

		});
		
		return new Loop() {

			@Override
			public Iterator<SAXEnhanced> iterator() {
				return new Iterator<SAXEnhanced>() {
					
					private Iterator<SAXCollisions> collisionIt = Arrays.asList(collisions).iterator();
					
					@Override
					public boolean hasNext() {
						return collisionIt.hasNext();
					}

					@Override
					public SAXEnhanced next() {
						return collisionIt.next().sax;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
					
				};
			}
		};
	}

	private static class SAXCollisions {
		private SAXEnhanced sax;
		private int collisions;
		private int index;

		public SAXCollisions(SAXEnhanced sax, int maxCollisions, int index) {
			super();
			this.sax = sax;
			this.collisions = maxCollisions;
			this.index = index;
		}

	}

}