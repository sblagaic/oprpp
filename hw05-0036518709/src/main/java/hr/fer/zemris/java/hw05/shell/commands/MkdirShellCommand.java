package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class MkdirShellCommand implements ShellCommand {

	private List<String> desc = new ArrayList<>();
	
	public MkdirShellCommand() {
		desc.add("The command takes a single argument: directory name,");
		desc.add("and creates the appropriate directory structure");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String arg = QuoteParser.parse(arguments);
		if (arg == null) 
			return ShellStatus.CONTINUE;
		Path p = Paths.get(QuoteParser.parse(arg));
		
		try {
			if (Files.exists(p)) {
				env.writeln(p.getFileName() + " already exists - command terminated");
				return ShellStatus.CONTINUE;
			}
			
			Files.createDirectory(p);
			env.writeln("Directory " + p.getFileName() + " successfully created");
						
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\nmkdir\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
