package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {
	
	private List<String> desc = new ArrayList<>();
	
	public CharsetsShellCommand() {
		desc.add("Lists the names of supported charsets for your Java platform.");
		desc.add("A single charset name is written per line.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for (String ch : Charset.availableCharsets().keySet()) {
			env.writeln(ch);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\ncharsets\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
