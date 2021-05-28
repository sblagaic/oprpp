package hr.fer.zemris.java.hw05.shell.commands;

public class QuoteParser {
	
	/**
	 * Checks whether the file path is correctly given,
	 * if it is removes the quotation marks from it
	 * @param argument a file-path entered by user
	 * @return null if the argument is invalid, otherwise returns 
	 * file-path without quotation marks
	 */
	public static String parse(String argument) {
		int len = argument.length();
		if (argument.lastIndexOf("\"") != len - 1 && argument.lastIndexOf("\"") != -1 && argument.charAt(len - 1) != ' ') {
			System.err.println("Invalid input");
			return null;
		}
		
		return argument.charAt(0) == '"' ? (argument.substring(1,
				argument.charAt(len - 1) != ' ' ? len - 1 : len - 2)) : argument;
		
	}
}
