package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element {
	
	private String value;
	
	/** Initialises the class prop
	 * @param value String value used to initialize the class prop
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * @return value property
	 */
	@Override
	public String asText() {
		return this.value;
	}
}
