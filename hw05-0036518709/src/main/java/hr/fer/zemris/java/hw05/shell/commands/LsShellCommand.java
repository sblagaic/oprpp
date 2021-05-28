package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class LsShellCommand implements ShellCommand {
	
	private List<String> desc = new ArrayList<>();
	
	public LsShellCommand() {
		desc.add("The command takes a single argument – directory –");
		desc.add("and writes a directory listing (not recursive).");
		desc.add("The output consists of 4 columns.");
		desc.add("First column indicates if current object is directory (d),");
		desc.add("readable (r), writable (w) and executable (x).");
		desc.add("Second column contains object size in bytes");
		desc.add("that is right aligned and occupies 10 characters");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String arg = QuoteParser.parse(arguments);
		if (arg == null) 
			return ShellStatus.CONTINUE;
		Path p = Paths.get(arg);
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(p)) {
			 for (Path entry: stream) {
				 env.write(Files.isDirectory(entry) ? "d" : "-");
				 env.write(Files.isReadable(entry) ? "r" : "-");
				 env.write(Files.isWritable(entry) ? "w" : "-");
				 env.write(Files.isExecutable(entry) ? "x " : "- ");
				 
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 Path path = Paths.get(arg);
				 BasicFileAttributeView faView = Files.getFileAttributeView(
					 path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				 BasicFileAttributes attributes = faView.readAttributes();
				 FileTime fileTime = attributes.creationTime();
				 String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				 
				 String len = String.valueOf(Files.size(entry));
				 env.write(new String(" ").repeat(10 - len.length()));
				 env.write(Files.size(entry) + " ");
				 env.write(formattedDateTime + " ");
				 env.writeln(entry.getFileName().toString());
			 }
				 
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "\nls\n";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(desc);
	}

}
