package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {

	private List<String> desc = new ArrayList<>();
	
	public HelpShellCommand() {
		desc.add("If the command is started without arguments");
		desc.add("it lists the names of all supported commands.");
		desc.add("If it is started with a single argument");
		desc.add("it prints the name and the description");
		desc.add("of the selected command.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments != "") {
			ShellCommand com = env.commands().get(arguments);
			env.writeln(com.getCommandName());
			List<String> desc = com.getCommandDescription();
			for (String d : desc) {
				env.writeln(d);
			}
			
		} else {
			SortedMap<String, ShellCommand> commands = env.commands();
			for (String com : commands.keySet()) {
				env.writeln(com);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\nhelp\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}
}
