package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

public class TurtleState {
	
	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double shift;
	
	public TurtleState(Vector2D position, Vector2D direction, Color color, double shift) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.shift = shift;
	}
	
	/**
	 * @return the position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(double shift) {
		this.shift = shift;
	}

	/**
	 * @return the direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the shift
	 */
	public double getShift() {
		return shift;
	}

	public TurtleState copy() {
		return new TurtleState(new Vector2D(position.getX(), position.getY()), new Vector2D(direction.getX(), direction.getY()), this.color, this.shift);
	}
}
