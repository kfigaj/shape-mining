package sax.representation.primitives;

/**
 * Pair convenience class
 * 
 * 
 * @param <T>
 *            - first pair element type
 * @param <V>
 *            - second pair element type
 */
public class Pair<T, V> {

	protected T firstElement;
	protected V secondElement;

	public Pair() {
		firstElement = null;
		secondElement = null;
	}

	public Pair(T first, V second) {
		firstElement = first;
		secondElement = second;
	}

	public T getFirstElement() {
		return firstElement;
	}

	public void setFirstElement(T firstElement) {
		this.firstElement = firstElement;
	}

	public V getSecondElement() {
		return secondElement;
	}

	public void setSecondElement(V secondElement) {
		this.secondElement = secondElement;
	}

	@Override
	public String toString() {
		return "<" + getFirstElement().toString() + ", " + getSecondElement()
				+ ">";
	}

}
