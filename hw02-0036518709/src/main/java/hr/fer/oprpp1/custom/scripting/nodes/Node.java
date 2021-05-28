package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	
	private boolean firstTime = true;
	private ArrayIndexedCollection collection;
	
	/**
	 * Adds given child to an internally managed collection of children
	 * @param child child which is added to 
	 * an internally managed collection of children
	 */
	public void addChildNode(Node child) {
		if (firstTime) {
			collection = new ArrayIndexedCollection();
			collection.add(child);
			firstTime = false;
		} else {
			collection.add(child);
		}
	}
	
	/**
	 * @return number of (direct) children
	 */
	public int numberOfChildren() {
		return collection.size();
	}
	
	
	/**
	 * Retrieves a child at the given position in the collection
	 * @param index position at which we wish to retrieve a child
	 * @return selected child
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater than size 
	 */
	public Node getChild(int index) {
		if (index >= 0 && index < collection.size()) {
			return (Node) collection.get(index);
		} else {
			throw new IndexOutOfBoundsException("Index must be greater than zero and smaller than size");
		}
	}
	
	
	
	
}
