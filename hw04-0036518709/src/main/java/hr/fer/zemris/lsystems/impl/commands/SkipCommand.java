package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

public class SkipCommand implements Command{

	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 *	Calculates the new position of the turtle and sets the turtle state to the new position
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D pos = state.getPosition();
		Vector2D dir = state.getDirection();
		dir.scale(this.step);
		state.setPosition(pos.added(dir));
		//pos.add(dir);
		
	}

}
