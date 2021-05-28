package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void main(String[] args) {
		
		String[] expression = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		for (String elem : expression) {
			System.out.println(elem);
			if (isNumber(elem)) {
				stack.push(elem);
		
			} else {
				Object op = stack.pop();
				int second = Integer.parseInt(op.toString());
				op = stack.pop();
				int first = Integer.parseInt(op.toString());
				
				switch(elem) {
					case "+": stack.push(first + second);
							  break;
					case "-": stack.push(first - second);
					  		  break;
					case "/": stack.push(first / second);
							  if (second == 0) {
								 System.err.println("Cannot divide by zero");
							  }
					  		  break;
					case "*": stack.push(first * second);
					  		  break;
					case "%": stack.push(first % second);
							  if (second == 0) {
								  System.err.println("Cannot divide by zero");
							  }
					  		  break;
					
				}
			}
		}
		
		if (stack.size() == 1) {
			System.out.println("The expression evaluates to " + stack.pop());
		} else {
			System.err.println("Stack size is not equal to 1");
		}
		
		
	}

	public static boolean isNumber(String elem) {
		if (elem == null) {
			return false;
		}
		
		try {
			int d = Integer.parseInt(elem);
		} catch (NumberFormatException exc) {
			return false;
		}
		return true;
	}
	
}
