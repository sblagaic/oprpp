package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
	
	
	/** Attemts to parse a number from the given string
	 * @param curr string which is uswed for number parsing
	 * @throws LexerException if the string is not a number 
	 * or if the number is too big for type Long
	 */
	public void numberParse(String curr) {
		try {
			Long.parseLong(curr);
		} catch (NumberFormatException ex) {
			throw new LexerException("");
		}
	}
	
	private char[] data; 
	private Token token; 
	private int currentIndex;
	private LexerState state;
	
	/** Initialises char[] data with the given text
	 * and sets currentIndex variable to 0
	 * @param text text which the lexer uses
	 */
	public Lexer(String text) {
		if (text.equals(null)) {
			throw new NullPointerException("Input must not be null");
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	/** 
	 * Groups one or more consecutive characters from entry text 
	 * into a token 
	 * @return returns new token of appropriate type
	 */
	public Token nextToken() {
		if (this.data.length == 0) {
			this.currentIndex++;
			return this.token = new Token(TokenType.EOF, null);
		}
		if (this.currentIndex == data.length - 1) {
			this.currentIndex += 2;
			return new Token(TokenType.EOF, null);
		}
		
//		if (this.currentIndex > this.data.length) {
//			throw new LexerException("");
//		}
		
		//0 for WORD, 1 for NUMBER, 2 for SYMBOL, 3 for whitespace, 4 for \\, 5 for something after \\
		int stanje = -1;
		String curr = new String();		
		for (int i = this.currentIndex; i < data.length; i++) {
			
		if (this.state == LexerState.BASIC) {
			//first or consecutive letter
			if (Character.isLetter(data[i]) && stanje != 1) {
				if (stanje == 4) {
					throw new LexerException("Invalid input");
				}
				stanje = 0;
				curr += Character.toString(data[i]);
				this.token = new Token(TokenType.WORD, curr);
				
			//letter after a number
			} else if (Character.isLetter(data[i]) && stanje == 1) {
				this.currentIndex = i;
				numberParse(curr); 
				return new Token(TokenType.NUMBER, Long.parseLong(curr));
				
			//first or consecutive number
			} else if (Character.isDigit(data[i]) && stanje != 0 && stanje != 4) {
				if (stanje == 5) {
					this.currentIndex = i;
					return new Token(TokenType.WORD, curr);
				}
				stanje = 1;
				curr += Character.toString(data[i]);
				numberParse(curr);
				this.token = new Token(TokenType.NUMBER, Long.parseLong(curr));
				
			//number after a letter
			} else if (Character.isDigit(data[i]) && stanje == 0) {
				this.currentIndex = i;
				return new Token(TokenType.WORD, curr); 
				
			} else if (Character.isDigit(data[i]) && stanje == 4) {
				curr += Character.toString(data[i]); 
				stanje = 5;
				
			} else if (data[i] == '\\') {
				if (i == data.length - 1) {
					throw new LexerException("Invalid input");
				}
				if (stanje == 4) {
					curr += Character.toString(data[i]);
				}
				stanje = 4;
				
			} else if (Character.isWhitespace(data[i])) {
				this.token = new Token(TokenType.EOF, null);
				this.currentIndex = i + 1;
				if (stanje == 0 || stanje == 4 || stanje == 5) {
					return new Token(TokenType.WORD, curr);
				} else if (stanje == 1) {
					numberParse(curr);
					return new Token(TokenType.NUMBER, Long.parseLong(curr));
				} 
				stanje = 3;
			//symbol
			} else {
				if (stanje == 0 || stanje == 4 || stanje == 5) {
					this.currentIndex = i;
					return new Token(TokenType.WORD, curr);
				} else if (stanje == 1) {
					this.currentIndex = i;
					numberParse(curr);
					return new Token(TokenType.NUMBER, Long.parseLong(curr));
				} 
				if (data[i] == '#') {
					if (this.state == LexerState.BASIC) {
						setState(LexerState.EXTENDED);
					} else {
						setState(LexerState.BASIC);
					}
				}
				this.currentIndex = i + 1;
				return new Token(TokenType.SYMBOL, data[i]);
			}
		//EXTENDED
		} else {
			if (data[i] == '#') {
				if (stanje == 0) {
					this.currentIndex = i;
					return new Token(TokenType.WORD, curr);
				} else {
					if (this.state == LexerState.BASIC) {
						setState(LexerState.EXTENDED);
					} else {
						setState(LexerState.BASIC);
					}
					this.currentIndex = i + 1;
					return new Token(TokenType.SYMBOL, data[i]);
				}
				
			} else if (Character.isWhitespace(data[i])) {
				this.token = new Token(TokenType.EOF, null);
				if (stanje == 0) {
					this.currentIndex = i + 1;
					return new Token(TokenType.WORD, curr);
				}
			} else {
				curr += Character.toString(data[i]);
				stanje = 0;
			}
		}
		}
		this.currentIndex = data.length - 1;
		return this.token;
	}
	
	/**
	 * @return returns current token
	 */
	public Token getToken() {
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
