package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class TextNode extends Node {

	private String text;
	ArrayIndexedCollection collection;
	
	/** Initialises the class prop
	 * @param name String text used to initialize the class prop
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * @return value of name property
	 */
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
