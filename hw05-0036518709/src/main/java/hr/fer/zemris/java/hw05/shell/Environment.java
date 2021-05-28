package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

public interface Environment {
	
	/**
	 * Reads user input from the console
	 * @return user input as a string
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Prints the given text to the console
	 * @param text console output
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Prints the given text to the console and adds an empty line
	 * @param text console output
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * @return console commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol to the given value
	 * @param symbol value we wish to set the multiline symbol to
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * @return prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol to the given value
	 * @param symbol value we wish to set the prompt symbol to
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol to the given value
	 * @param symbol value we wish to set the morelines symbol to
	 */
	void setMorelinesSymbol(Character symbol);
}
