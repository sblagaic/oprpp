package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

public class Complex {
	
	private final double real;
	private final double imaginary;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	public Complex() {
		this.real = 1;
		this.imaginary = 1;
	}
	
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}
	
	/**
	 * @return real part of the complex number
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * @return imaginary part of the complex number
	 */
	public double getImag() {
		return this.imaginary;
	}
	
	/**
	 * Calculates the module of a complex number by adding the squares
	 * of its real and imaginary parts and then taking the second 
	 * root of the sum
	 * @return module of a complex number
	 */
	public double module() {
		return sqrt(this.real * this.real + this.imaginary * this.imaginary);
	}
	
	/**
	 * @return angle of a complex number in radians
	 */
	public double angle() {
		 return atan2(this.imaginary, this.real);
		
	}
	
	/**
	 * Multiplies two complex numbers using normal algebra rules while applying the rule i^2 = -1
	 * @param c factor
	 * @return new complex number
	 */
	public Complex multiply(Complex c) {
		double tmp_real = this.real * c.real - this.imaginary * c.imaginary;
		double tmp_img = this.real * c.imaginary + this.imaginary * c.real;
				
		return new Complex(tmp_real, tmp_img);
	}
	
	/** Divides two complex numbers by using conjugate function to 
	 * convert the denominator of the equation into a real number
	 * @param c divisor
	 * @return
	 */
	public Complex divide(Complex c) {
		Complex tmp = new Complex(c.real, c.imaginary * -1);
		Complex numerator = multiply(tmp);
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		return new Complex(numerator.real/denominator, numerator.imaginary/denominator);
	}
	
	/**
	 * Adds two complex numbers by adding their real parts and their imaginary parts
	 * @param c complex number which is added to the existing one
	 * @return new complex number 
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/** 
	 * Subtracts two complex numbers by subtracting 
	 * their real parts and their imaginary parts
	 * @param c subtrahend
	 * @return new complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	
	/**
	 * @return complex number in which the real and imaginary parts
	 *  of the original complex number are multiplied by -1.
	 */
	public Complex negate() {
		return new Complex(this.real * -1, this.imaginary * -1);
	}
	
	/**
	 * Calculates the nth power of the complex number 
	 * @param n the power on which we want to potentiate a complex number
	 * @return new complex number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Value of n must be greater or equal to zero");
		}
		
		double n_theta = n * angle();
		Complex real = new Complex(pow(module(), n), 0);
		Complex angle = new Complex(cos(n_theta), sin(n_theta));		
		
		return real.multiply(angle);
	}
	
	/** Calculates the nth roots of a complex number 
	 * @param n 
	 * @return ComplexNumber array of k solutions where k = 0,1,2 ...,n-1
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Value of n must be greater or equal to zero");
		}
		List<Complex> solutions = new ArrayList<>();
		double ro = pow(module(), 1/n);
		double theta = angle();
		
		for (int k = 0; k < n; k++) {
			Complex angle = new Complex(cos((theta + 2*k*PI)/n), sin((theta + 2*k*PI)/n));
			solutions.add(new Complex(ro * angle.real, ro * angle.imaginary));
		}
		
		return solutions;
	}
	
	/**
	 * Extracts the real and the imaginary part (if they exist) of a complex number from the given string
	 * @param s string which specifies the complex number
	 * @return new ComplexNumber with parsed values as arguments
	 */
	public static Complex parse(String s) {
		if (s.equals("i")) return new Complex(0, 1);
		
		char[] input = s.toCharArray();
		int found = input.length;
		boolean has_img_part = false;
		double real = 0;
		double img = 0;
		
		if (s.contains("i")) has_img_part = true;
		
		for (int i = input.length - 1; i >= 0; i--) {
			if (input[i] == '-' || input[i] == '+' || i == 0) {
				if (has_img_part) {
					img = Double.parseDouble(String.valueOf(input[i]) + (input[i + 1] == 'i' ? 
							(i + 1 == input.length - 1 ? "1" : s.substring(i + 2)) : i + 2 == input.length - 1 ? "1" : s.substring(i + 3)));
					found = i;
					has_img_part = false;					
				} else {
					real = Double.parseDouble(s.substring(0, found));
				} 
				
			}
		}
		
		return new Complex(real, img);
	}
	
	/**
	 * @return real and imaginary part of a complex number in the form x + iy
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(real != 0 ? real : "0.0");	
		sb.append(imaginary >= 0 ? "+" : "-").append("i")
			.append(imaginary != 0 ? (imaginary > 0 ? imaginary : imaginary * -1) : "0.0");
		
		return sb.toString();
	}
}
