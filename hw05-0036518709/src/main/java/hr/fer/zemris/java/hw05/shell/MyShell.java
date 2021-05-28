package hr.fer.zemris.java.hw05.shell;

public class MyShell {
	
	public static void main(String[] args) {
		
		ShellEnvironment env = new ShellEnvironment();
		ShellStatus status = ShellStatus.CONTINUE;
		env.writeln("Welcome to MyShell v 1.0");
		
		while (status == ShellStatus.CONTINUE) {
			
			env.write(env.getPromptSymbol() + " ");
			String[] arguments = env.readLine().split("\\s+", 2);
			String arg1 = new String();
		
			if (arguments.length > 1) {
				int len = arguments[1].length();
				arg1 = arguments[1];
				if (arguments[1].charAt(len - 1) == env.getMorelinesSymbol()) 
					arg1 = len > 1 ? arguments[1].substring(0, len - 2) : "";
				
				while (arguments[1].charAt(arguments[1].length() - 1) == env.getMorelinesSymbol()) {
					env.write(env.getMultilineSymbol() + " ");
					arg1 += env.readLine();
					char c = arg1.charAt(arg1.length() - 1);
					
					arguments[1] = String.valueOf(c);
					if (c == env.getMorelinesSymbol()) 
						arg1 = arg1.substring(0, arg1.length() - 2);
				}
			} 

			ShellCommand com = env.commands().get(arguments[0]);
			status = com.executeCommand(env, arguments.length > 1 ? arg1 : "");
		}
	}
}
