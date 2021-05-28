package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand {
	
	private List<String> desc = new ArrayList<>();
	
	public TreeShellCommand() {
		desc.add("The command expects a single argument:");
		desc.add("directory name and prints a tree.");
		desc.add("Each directory level shifts output two charatcers to the right.");
	}
	
	private void ispis(File path, int level, Environment env) {
		env.writeln(" ".repeat(level * 2) + path.getName());
		if(path.isDirectory()) {
			File[] children = path.listFiles();
			if(children == null) return;
			for(File d : children) {
				ispis(d, level + 1, env);
			}
		}
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String file = QuoteParser.parse(arguments);
		if (file != null) 
			ispis(new File(file), 0, env);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\ntree\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
