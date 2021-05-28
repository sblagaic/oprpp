package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
	
	private String query;
	private QueryLexer lexer;
	private TokenType prevType;
	List<Token> tokens = new ArrayList<>();
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator compOperator;
	
	private IFieldValueGetter getterType(String attribute) {
		if (attribute.equals("jmbag")) {
			return FieldValueGetters.JMBAG; 
		} else if (attribute.equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		} else { 
			return FieldValueGetters.LAST_NAME;
		}
	}
	
	/**
	 * @param op operator from the query
	 * @return the appropriate ComparisonOperator based on the given parameter
	 */
	private IComparisonOperator operatorType(String operator) {
		switch (operator) {
			case "<": 	return ComparisonOperators.LESS;
			
			case "<=": 	return ComparisonOperators.LESS_OR_EQUALS;
			
			case ">": 	return ComparisonOperators.GREATER;
			
			case ">=": 	return ComparisonOperators.GREATER_OR_EQUALS;
			
			case "=": 	return ComparisonOperators.EQUALS;
			
			case "!=": 	return ComparisonOperators.NOT_EQUALS;
			
			default: 	return ComparisonOperators.LIKE;
		}
	}
	
	/**
	 * Checks if the left and right side of comparison operator are 
	 * attribute and literal, respectively
	 * @param type 
	 */
	private void isValid(TokenType type) {
		if (type == TokenType.OPERATOR) {
			if (prevType != TokenType.ATTRIBUTE) {
				throw new IllegalArgumentException();
			}
		} else if (type == TokenType.LITERAL) {
			if (prevType != TokenType.OPERATOR) {
				throw new IllegalArgumentException();
			}
		}
	}
	
	/** Constructor which accepts a string that contains a query,
	 * creates an instance of lexer and initialises it with obtained text
	 * @param query string that contains an SQL query
	 */
	public QueryParser(String query) {
		this.query = query;
		lexer = new QueryLexer(query);
	}
	
	/**
	 * @return true if query was of the form jmbag="xxx"
	 */
	public boolean isDirectQuery() {	
		return fieldGetter == FieldValueGetters.JMBAG && 
				compOperator == ComparisonOperators.EQUALS && tokens.size() == 3;
	}
	
	/**
	 * @return the string which was given in equality comparison in direct query
	 * @throws IllegalStateException if the query was not a direct one
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException();
		}
		return stringLiteral;
	}
	
	/**
	 * Parses the given tokens, creates conditional expressions and adds them in a list
	 * @return a list of conditional expressions from a query
	 */
	public List<ConditionalExpression> getQuery() {
		if (tokens.size() == 0) {
			while (lexer.nextToken().getType() != TokenType.LINE) {
				tokens.add(lexer.getToken());
			}
		}
		List<ConditionalExpression> list = new ArrayList<>();
	
		for (Token token : tokens) {
			isValid(token.getType());
			
			if (token.getType() == TokenType.ATTRIBUTE) {
				fieldGetter = getterType(token.getValue().toString());
				
			} else if (token.getType() == TokenType.OPERATOR) {
				compOperator = operatorType(token.getValue().toString());
				
			} else if (token.getType() == TokenType.LITERAL) {
				if (token.getValue().toString().indexOf('*') != token.getValue().toString().lastIndexOf('*')) {
					throw new IllegalStateException();
				}
				stringLiteral = token.getValue().toString();
				list.add(new ConditionalExpression(fieldGetter, stringLiteral, compOperator));
				
			} else {
				continue;	
			} 
			
			prevType = token.getType();
		}
		
		return list;
	}
}
