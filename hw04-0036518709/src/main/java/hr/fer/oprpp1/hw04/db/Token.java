package hr.fer.oprpp1.hw04.db;

public class Token {
	
	private TokenType type;
	private Object value;

	/** 
	 * Sets type and value of a token
	 * @param type type of token we wish to set
	 * @param value value of token we wish to set
	 */
	public Token(TokenType type, Object value) {
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
	public TokenType getType() {
		return this.type;
	}
}
