package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class RotateCommand implements Command {

	private double angle;
	
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Modifies the direction vector of the turtle 
	 * by rotating for the given number of degrees
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setDirection(state.getDirection().rotated(this.angle));
		
	}

}
