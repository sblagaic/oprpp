package hr.fer.oprpp1.custom.scripting.elems;

public class ElementOperator extends Element {
	
	private String symbol;
	
	/** Initialises the class prop
	 * @param symbol String value used to initialize the class prop
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return value of symbol property
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
