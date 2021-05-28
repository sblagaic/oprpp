package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element {
	
	private int value;
	
	/** Initialises the class prop
	 * @param value int value used to initialize the class prop
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * @return string representation of value property
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
