package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class HexdumpShellCommand implements ShellCommand {

	private List<String> desc = new ArrayList<>();
	
	public HexdumpShellCommand() {
		desc.add("The command expects a single argument:");
		desc.add("file name, and produces hex-output.");
		desc.add("On the right side of the output only");
		desc.add("a standard subset of characters is shown.");
		desc.add("For all other characters '.' is printed instead.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String arg = QuoteParser.parse(arguments);
		if (arg == null) 
			return ShellStatus.CONTINUE; 
		
		File file = new File(arg);
		if (file.isDirectory()) {
			env.writeln(file.getName() + " is a directory. Argument must be a file - command terminated");
			return ShellStatus.CONTINUE;
		}
		
		try {
			InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[16];
            int len;
            int ascii;
            int counter = 0;
            String hex = new String();
 
            while ((len = inputStream.read(buffer)) != -1) {
            	env.write(new String("0").repeat(8 - Integer.toHexString(counter).length()) 
            			+ Integer.toHexString(counter).toUpperCase() + ": ");
            	counter += 16;
            	for (int i = 0; i < len; i++) {
    				ascii = (int) buffer[i];
    				hex = Integer.toHexString(ascii).toUpperCase();
    				if (ascii < 32 || ascii > 127) {
    					buffer[i] = '.';
    					env.write(i == 7 ? "0" + hex + "|" : "0" + hex + " ");
    				} else {
    					env.write(i == 7 ? hex + "|" : hex + " ");
    				}
    			}
            	
            	if (len < 16) {
	            	for (int j = (16 - len); j > 0; j--) {		    
	            		env.write((16 - 7 == j) ? "  |" : "   ");
	            	}
            	}
            	env.writeln("| " + new String(buffer).substring(0, len));
            }
            	   
            inputStream.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {		
		return "\nhexdump\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
