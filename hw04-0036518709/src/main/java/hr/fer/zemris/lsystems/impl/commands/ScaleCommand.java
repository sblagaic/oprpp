package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class ScaleCommand implements Command{

	private double factor;
	
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Updates the current state shift by scaling it with the given factor
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setShift(state.getShift() * this.factor);
	}

}
