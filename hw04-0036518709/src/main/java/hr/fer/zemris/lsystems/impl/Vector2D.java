package hr.fer.zemris.lsystems.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * Creates an instance of a two-dimensional vector
	 * @param x x parameter of a vector
	 * @param y y parameter of a vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return returns x parameter of vector
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * @return returns y parameter of vector
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Adds the given vector to the existing one
	 * @param offset 2D vector which is added to the existing one
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Creates a new 2D vector by adding the given vector to the existing one
	 * @param offset 2D vector which is added to the existing one
	 * @return returns new 2D vector 
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotates the existing vector by the given number of degrees
	 * @param angle angle by which the vector is rotated
	 */
	public void rotate(double angle) {
		this.x = cos(angle * PI/180) * this.x - sin(angle * PI/180) * this.y;
		this.y = sin(angle * PI/180) * this.x + cos(angle * PI/180) * this.y;
	}
	
	/**
	 * Creates a new 2D vector by rotating the existing vector 
	 * by the given number of degrees
	 * @param angle angle by which the vector is rotated
	 * @return returns new 2D vector
	 */
	public Vector2D rotated(double angle) {
		double x_tmp = cos(angle * PI/180) * this.x - sin(angle * PI/180) * this.y;
		double y_tmp = sin(angle * PI/180) * this.x + cos(angle * PI/180) * this.y;
		return new Vector2D(x_tmp, y_tmp);
	}
	
	/**
	 * Changes the existing vector length by a scaler factor
	 * @param scaler factor by which the vector length is changed
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Creates a new 2D vector by changing the 
	 * existing vector length by a scaler factor
	 * @param scaler factor by which the vector length is changed
	 * @return new 2D vector scaled from the existing one by a scaler factor
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x *= scaler, this.y *= scaler);
	}
	
	/**
	 * @return returns a new 2D vector with the same 
	 * parameters as the existing one
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
