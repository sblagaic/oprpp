package hr.fer.oprpp1.custom.scripting.parser;

public class TokenScript {
	
	private TypeOfToken type;
	private Object value;

	/** 
	 * Sets type and value of a token
	 * @param type type of token we wish to set
	 * @param value value of token we wish to set
	 */
	public TokenScript(TypeOfToken type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * @return returns current value of a token
	 */
	public Object getValue() {
		return this.value;
	}
	

	/**
	 * @return returns current value of a token
	 */
	public TypeOfToken getType() {
		return this.type;
	}
}
