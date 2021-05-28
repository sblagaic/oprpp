package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element{
	
	private String name;
	
	/** Initialises the class prop
	 * @param name String value used to initialize the class prop
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * @return value of name property
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
