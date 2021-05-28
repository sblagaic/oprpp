package hr.fer.oprpp1.math;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2DTest {
	
	@Test
	public void testVector2DDefaultConstructor() {
		Vector2D vector = new Vector2D(2, 3);
		Assertions.assertEquals(3, vector.getY());
	}
	
	@Test
	public void testAddMethod() {
		Vector2D vector = new Vector2D(2, 3);
		vector.add(new Vector2D(2, 2));
		Assertions.assertEquals(5, vector.getY());
	}
	
	@Test
	public void testAddedMethod() {
		Vector2D vector = new Vector2D(2, 3);
		Vector2D result = vector.added(new Vector2D(2, 4));
		Assertions.assertEquals(7, result.getY());
	}
	
	@Test
	public void testRotateMethod() {
		Vector2D vector = new Vector2D(2, 0);
		vector.rotate(90);
		long expected = 0;
		long result = (long) vector.getX();
		Assertions.assertTrue(Objects.equals(expected, result));
	}
	
	@Test
	public void testRotatedMethod() {
		Vector2D vector = new Vector2D(2, 0);
		Vector2D resultVector = vector.rotated(90);
		long expected = 0;
		long result = (long) resultVector.getX();
		Assertions.assertTrue(Objects.equals(expected, result));
	}
	
	@Test
	public void testScaleMethod() {
		Vector2D vector = new Vector2D(2, 3);
		vector.scale(1.5);
		Assertions.assertEquals(4.5, vector.getY());
	}
	
	@Test
	public void testScaledMethod() {
		Vector2D vector = new Vector2D(2, 3);
		Vector2D result = vector.scaled(1.5);
		Assertions.assertEquals(4.5, result.getY());
	}
	
	@Test
	public void testCopyMethod() {
		Vector2D vector = new Vector2D(2, 3);
		Vector2D result = vector.copy();
		Assertions.assertEquals(3, result.getY());
	}
	
}
