package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element {
	
	private double value;
	
	/** Initialises the class prop
	 * @param value double value used to initialize the class prop
	 */
	public ElementConstantDouble(double value) {
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
