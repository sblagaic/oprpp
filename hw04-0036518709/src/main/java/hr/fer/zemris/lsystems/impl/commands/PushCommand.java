package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class PushCommand implements Command {

	/**
	 * Copies the state from the top of the stack 
	 * and pushes its copy to the top of the stack 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		ctx.pushState(state.copy());
		
		
	}

}
