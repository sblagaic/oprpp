package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class SymbolShellCommand implements ShellCommand {

	private List<String> desc = new ArrayList<>();
	
	public SymbolShellCommand() {
		desc.add("The command returns the symbol of the specified symbol type");
		desc.add("The symbol for the specified command can be changed by");
		desc.add("by adding it as an additional argument");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] arg = arguments.split("\\s+");
		
		switch(arg[0]) {
			case "PROMPT": 		if (arg.length == 1) {
									env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
								} else {
									env.write("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '");
									env.setPromptSymbol(arg[1].charAt(0));
									env.writeln(env.getPromptSymbol() + "'");
								}
								break;
								
			case "MORELINES":   if (arg.length == 1) {
									env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
								} else {
									env.write("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '");
									env.setMorelinesSymbol(arg[1].charAt(0));
									env.writeln(env.getMorelinesSymbol() + "'");
								} 	
								break;
								
			case "MULTILINE":	if (arg.length == 1) { 	
									env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
								} else {
									env.write("Symbol for MULTILINE changed from '" + env.getMorelinesSymbol() + "' to '");
									env.setMultilineSymbol(arg[1].charAt(0));
									env.writeln(env.getMultilineSymbol() + "'");
								}
								break;
		
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\nsymbol\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
