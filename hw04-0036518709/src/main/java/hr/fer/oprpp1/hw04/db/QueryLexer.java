package hr.fer.oprpp1.hw04.db;

public class QueryLexer {
	
	private char[] query;
	private Token token;
	private int currentIndex;
	
	/** Initialises char[] query with the given text
	 * and sets currentIndex variable to 0
	 * @param query text which the lexer uses
	 */
	public QueryLexer(String query) {
		this.query = query.toCharArray();
		this.currentIndex = 0;
	}
	
	/** 
	 * Groups one or more consecutive characters from query into a token 
	 * @return returns new token of appropriate type
	 */
	public Token nextToken() {
		
		// 0 for words, 1 for literals
		int state = -1;
		String curr = new String();
		for (int i = currentIndex; i < query.length; i++) {
			
			if (Character.isLetter(query[i]) || Character.isDigit(query[i]) || query[i] == '*') {
				if (state != 1) state = 0;
				curr += Character.toString(query[i]);
				
			} else if (query[i] == '<' || query[i] == '>' || query[i] == '=' || query[i] == '!') {
				if (state == 0) {
					currentIndex = i;
					return this.token = new Token(TokenType.ATTRIBUTE, curr);
				}  
		
				if (query[i + 1] == '=') {
					currentIndex = i + 2;
					curr += Character.toString(query[i]) + Character.toString(query[i + 1]);
				} else {
					curr += Character.toString(query[i]);
					currentIndex = i + 1;
				}
				return this.token = new Token(TokenType.OPERATOR, curr);
				
			} else if (query[i] == '\"') {
				if (state == 1) {
					currentIndex = i + 1;
					return this.token = new Token(TokenType.LITERAL, curr.substring(1, curr.length()));
				}
				state = 1;
				curr += Character.toString(query[i]);
			}
			
			if (curr.equals("jmbag") || curr.equals("firstName") || curr.equals("lastName")) {
				currentIndex = i + 1;
				return this.token = new Token(TokenType.ATTRIBUTE, curr);
				
			} else if (curr.equalsIgnoreCase("AND")) {
				currentIndex = i + 1;
				return this.token = new Token(TokenType.AND, curr);
				
			} else if (curr.equalsIgnoreCase("LIKE")) {
				currentIndex = i + 1;
				return this.token = new Token(TokenType.OPERATOR, curr);
				
			} else if (curr.equalsIgnoreCase("exit")) {
				return this.token = new Token(TokenType.EOF, curr);
				
			}
		}
		
		return this.token = new Token(TokenType.LINE, "end_line");
	}
	
	/**
	 * @return returns current token
	 */
	public Token getToken() {
		return this.token;
	}
}
