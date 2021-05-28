package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class ComplexNumberTest {
	
	@Test
	public void testComplexNumberConstructor() {
		ComplexNumber num = new ComplexNumber(2, 2);
		Assertions.assertEquals(2, num.getReal());
	}
	
	@Test
	public void testFromRealFactoryMethod() {
		ComplexNumber num = ComplexNumber.fromReal(3);
		Assertions.assertEquals(3, num.getReal());
	}
	
	@Test
	public void testFromImaginaryFactoryMethod() {
		ComplexNumber num = ComplexNumber.fromImaginary(3);
		Assertions.assertEquals(3, num.getImaginary());
	}
	
	@Test
	public void testFromMagnitudeAndAngleyFactoryMethod() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(8, 0);
		//Assertions.assertEquals(0, Double.compare(8, num.getReal()));
		Assertions.assertTrue(Double.compare(0, num.getImaginary()) == 0);
	}
	
	@Test
	public void testParseFactoryMethod() {
		ComplexNumber num = ComplexNumber.parse("2+3i");
		Assertions.assertTrue(Double.compare(3, num.getImaginary()) == 0);
	}
	
	@Test
	public void testGetMagnitudeMethod() {
		ComplexNumber num = new ComplexNumber(4, 3);
		Assertions.assertTrue(Double.compare(5, num.getMagnitude()) == 0);
	}
	
	@Test
	public void testGetAngleMethod() {
		ComplexNumber num = new ComplexNumber(1, 0);
		Assertions.assertTrue(Double.compare(0, num.getAngle()) == 0);
	}
	
	@Test
	public void testAddMethod() {
		ComplexNumber first = new ComplexNumber(4, 3);
		ComplexNumber second = new ComplexNumber(3, 8);
		ComplexNumber result = first.add(second);
		Assertions.assertEquals(11, result.getImaginary());
	}
	
	@Test
	public void testSubMethod() {
		ComplexNumber first = new ComplexNumber(4, 3);
		ComplexNumber second = new ComplexNumber(3, 8);
		ComplexNumber result = first.sub(second);
		Assertions.assertEquals(1, result.getReal());
	}
	
	@Test
	public void testMulMethod() {
		ComplexNumber first = new ComplexNumber(4, 1);
		ComplexNumber second = new ComplexNumber(2, 3);
		ComplexNumber result = first.mul(second);
		Assertions.assertEquals(14, result.getImaginary());
	}
	
	@Test
	public void testDivMethod() {
		ComplexNumber first = new ComplexNumber(4, 1);
		ComplexNumber second = new ComplexNumber(1, 0);
		ComplexNumber result = first.div(second);
		Assertions.assertTrue(Double.compare(1, result.getImaginary()) == 0);
	}
	
//	@Test
//	public void testPowerMethod() {
//		ComplexNumber first = new ComplexNumber(1, 0);
//		ComplexNumber result = first.power(1);
//		Assertions.assertTrue(Double.compare(1, result.getImaginary()) == 0);
//	}
	
	@Test
	public void testPowerMethodThrowsException() {
		ComplexNumber first = new ComplexNumber(3, 3);
		Assertions.assertThrows(IllegalArgumentException.class, () -> first.power(-1));
	}
	
//	@Test
//	public void testRootMethod() {
//		ComplexNumber first = new ComplexNumber(3, 3);
//		ComplexNumber result = first.power(5);
//		Assertions.assertTrue(Double.compare(-972, result.getImaginary()) == 0);
//	}
	
	@Test
	public void testRootMethodThrowsException() {
		ComplexNumber first = new ComplexNumber(3, 3);
		Assertions.assertThrows(IllegalArgumentException.class, () -> first.root(0));
	}
	
	@Test
	public void testToStringMethod() {
		ComplexNumber num = ComplexNumber.parse("2+3i");
		Assertions.assertEquals("2.0+3.0i", num.toString());
	}
}
