package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.TokenScript;
import hr.fer.oprpp1.custom.scripting.parser.TypeOfToken;

public class LexerScript {
	
	private char[] data;
	private TokenScript token;
	private int currentIndex;
	private LexerState state;
	
	/** Initialises char[] data with the given text
	 * and sets currentIndex variable to 0
	 * @param text text which the lexer uses
	 */
	public LexerScript(String text) {
		if (text.equals(null)) {
			throw new NullPointerException("Input must not be null");
		}
		if (text.isBlank()) {
			this.token = new TokenScript(TypeOfToken.EOF, null);
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/** 
	 * Groups one or more consecutive characters from entry text 
	 * into a token 
	 * @return returns new token of appropriate type
	 */
	public TokenScript nextToken() {
		
		String curr = new String();
		//0 for text, 1 for tag
		int stanje = -1;
		
		if (this.currentIndex == data.length - 2) {
			return this.token = new TokenScript(TypeOfToken.EOF, null);
		}
		for (int i = this.currentIndex; i < data.length; i++) {
			if (data[i] == '{' && data[i + 1] == '$' && data[i - 1] != '\\') {
				if (stanje == 0) {
					this.currentIndex = i;
					return this.token = new TokenScript(TypeOfToken.TEXT, curr);
				}
				i += 2;
				while (Character.isWhitespace(data[i])) {
					i++;
				}
				//for tag
				if (data[i] == 'F' || data[i] == 'f') {
					setState(LexerState.TAG);
					this.currentIndex = i + 3;
					return this.token = new TokenScript(TypeOfToken.FOR, "FOR");
				} else if (data[i] == '=') {
					setState(LexerState.TAG);
					this.currentIndex = i + 1; 
					return this.token = new TokenScript(TypeOfToken.EQUALS, "=");
				} else if (data[i] == 'E' || data[i] == 'E') {
					setState(LexerState.TAG);
					this.currentIndex = i + 3; 
					return this.token = new TokenScript(TypeOfToken.END, "END");
				}
				
			} else {
				if (this.state == LexerState.TAG) {
					if (Character.isWhitespace(data[i])) {
						if (stanje == 1) {
							this.currentIndex = i + 1;
							return this.token = new TokenScript(TypeOfToken.CONTENT, curr);
						}
						
					} else if(data[i] == '$') {
						if (stanje == 1) {
							this.currentIndex = i;
							return this.token = new TokenScript(TypeOfToken.CONTENT, curr);
						}
						i += 1;
						setState(LexerState.TEXT);
					} else {
						stanje = 1;
						curr += Character.toString(data[i]);
					}
					
				} else {
					stanje = 0;
					curr += Character.toString(data[i]);
				}
				
			}
			if (i == data.length - 1 && stanje == 0) {
				this.currentIndex = data.length - 2;
				return this.token = new TokenScript(TypeOfToken.TEXT, curr);
			}
			
		}
		return this.token;
		
	}
	
	/**
	 * @return returns current token
	 */
	public TokenScript getToken() {
		return this.token;
	}
	
	/** 
	 * Sets the state of lexer
	 * @param state lexer state we wish to set
	 * @throws NullPointerException if the given state is null
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException("State must not be null");
		}
		this.state = state;
	}
	
}
