package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class EchoNode extends Node {

	private Element[] elements;

	/** Initialises the class prop
	 * @param name String text used to initialize the class prop
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * @return value of elements property
	 */
	public Element[] getElements() {
		return elements;
	}
	
	public String toString() {
		String curr = new String();
		curr = "{$ =";
		for (int i = 0; i < this.elements.length; i++) {
			if (this.elements[i] != null) {
				curr += " " +  this.elements[i].asText();
			}
		}
		
		curr += " $}";
		
		return curr;
	}
	
	
}
