package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class Context {
	
	private ObjectStack<TurtleState> stack = new ObjectStack<>();
	
	/**
	 * @return current state from the stack without removing it
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes state at the top of the stack
	 * @param state state which is pushed on the stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Deletes a state from the top of the stack
	 */
	public void popState() {
		stack.pop();
	}
}
