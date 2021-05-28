package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CopyShellCommand implements ShellCommand {
	
	private List<String> desc = new ArrayList<>();
	
	public CopyShellCommand() {
		desc.add("The command expects two arguments:");
		desc.add("source file name and destination file name.");
		desc.add("It copies the source file to the destination file.");
		desc.add("The command works only with files.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] files = arguments.split("\\s+");
		String src = QuoteParser.parse(files[0]);
		String dest = QuoteParser.parse(files[1]);
		
		if (src == null || dest == null) {
			return ShellStatus.CONTINUE;
		}
		
		File sourceFile = new File(src);
		File destFile = new File(dest);
		
		if (!destFile.exists()) {
			env.writeln("The file " + destFile.getName() + " does not exist - command terminated");
			return ShellStatus.CONTINUE;
		}
		
		if (destFile.isDirectory()) {
			dest = dest + src.substring(src.lastIndexOf("\\"));
			destFile = new File(dest);
		}
		
		env.write("Is it allowed to overwrite " + destFile.getName() + " (y/n)\n" + env.getPromptSymbol() + " ");	
		if (env.readLine().equals("n")) {
			env.writeln("Command terminated");
			return ShellStatus.CONTINUE;
		}
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(destFile);
			byte[] buffer = new byte[4096];
			int len;
			 
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);     
            }		         
            
            inputStream.close();
	        outputStream.close();
	        
	        env.writeln("File " + sourceFile.getName() + " successfully copied into " + destFile.getName());
	         
		} catch (ShellIOException | IOException e) {
			e.printStackTrace();
		} 
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\ncopy\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
