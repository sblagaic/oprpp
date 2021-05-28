package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

public class DrawCommand implements Command{

	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Calculates the new position of the turtle, draws a line with given color 
	 * from current to new position, and sets the turtle state to the new position
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		double x = Math.pow(state.getDirection().getX(), 2);
		double y = Math.pow(state.getDirection().getY(), 2);
		double length = Math.sqrt(x + y);
		
		Vector2D currPos = state.getPosition();
		Vector2D shift = state.getDirection().scaled(state.getShift()/length * this.step);
		Vector2D nextPos = currPos.added(shift);
		
		painter.drawLine(currPos.getX(), currPos.getY(), nextPos.getX(), nextPos.getY(), state.getColor(), 1f);
		state.setPosition(nextPos);
		
	}

}
