package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CatShellCommand implements ShellCommand {
	
	private List<String> desc = new ArrayList<>();
	
	public CatShellCommand() {	
		desc.add("The command takes one or two arguments.");
		desc.add("The first argument is a path to some file and is mandatory.");
		desc.add("The second argument is a charset name that");
		desc.add("should be used to interpret chars from bytes.");
		desc.add("If not provided, a default platform charset will be used.");
		desc.add("The command opens the given file and writes its content to console.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] arg = arguments.split("\\s+");
		String path = QuoteParser.parse(arg[0]);
		if (path == null) 
			return ShellStatus.CONTINUE;
		
		Path p = Paths.get(path);
		try {
			env.writeln(Files.readString(p, arg.length > 1 ? Charset.forName(arg[1]) : Charset.defaultCharset()));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\ncat\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
