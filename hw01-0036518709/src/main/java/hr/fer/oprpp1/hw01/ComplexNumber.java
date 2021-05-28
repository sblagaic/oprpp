package hr.fer.oprpp1.hw01;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.PI;


public class ComplexNumber {
	
	private double real;
	private double imaginary;

	/**
	 * Sets the real and imaginary part of the complex number
	 * @param real real part of the complex number
	 * @param imaginary imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	
	/**
	 * @param real real part of the complex number
	 * @return new ComplexNumber which only has a real part
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * @param imaginary imaginary part of the complex number
	 * @return new ComplexNumber which only has an imaginary part
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Creates a new complex number by multiplying its magnitude with cosine of the angle as the real part
	 * and with sinus of the angle as the imaginary part
	 * @param magnitude its distance from the origin in the complex plane  
	 * @param angle complex argument
	 * @return new ComplexNumber 
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double radians = PI / 180 * angle;
		return new ComplexNumber(magnitude * cos(radians), magnitude * sin(radians));
	}
	
	/**
	 * Extracts the real and the imaginary part (if they exist) of a complex number from the given string
	 * @param s string which specifies the complex number
	 * @return new ComplexNumber with parsed values as arguments
	 */
	public static ComplexNumber parse(String s) {
		if (s.equals("i") || s.equals("+i")) {
			return new ComplexNumber(0, 1);
		} 
		if (s.equals("-i")) {
			return new ComplexNumber(0, -1);
		}
		
		char[] input = s.toCharArray();
		int found = input.length;
		boolean has_img_part = false;
		double real = 0;
		double img = 0;
		
		if (s.contains("i")) {
			has_img_part = true;
			if (!Character.toString(s.charAt(s.length() - 1)).equals("i")) {
				throw new IllegalArgumentException("i must be at the end of the input");
			}
		}
		
		for (int i = input.length - 1; i >= 0; i--) {
			if (input[i] == '-' || input[i] == '+' || i == 0) {
				if (has_img_part) {
					img = Double.parseDouble(s.substring(i, input.length - 1));
					found = i;
					has_img_part = false;
				} else {
					real = Double.parseDouble(s.substring(0, found));
				} 
				
			}
		}
		
		return new ComplexNumber(real, img);
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
	public double getImaginary() {
		return this.imaginary;
	}
	
	/**
	 * @return magnitude of a complex number by multiplying 
	 */
	public double getMagnitude() {
		return sqrt(this.real * this.real + this.imaginary * this.imaginary);
	}
	
	/**
	 * @return angle of a complex number in radians
	 */
	public double getAngle() {
		 return atan(this.imaginary/this.real);
		
	}
	
	/**
	 * Adds two complex numbers by adding their real parts and their imaginary parts
	 * @param c ComplexNumber which is added to the existing one
	 * @return new ComplexNumber 
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/** 
	 * Subtracts two complex numbers by subtracting their real parts and their imaginary parts
	 * @param c subtrahend
	 * @return new ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Multiplies two complex numbers using normal algebra rules while applying the rule i^2 = -1
	 * @param c factor
	 * @return new ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double tmp_real = this.real * c.real - this.imaginary * c.imaginary;
		double tmp_img = this.real * c.imaginary + this.imaginary * c.real;
				
		return new ComplexNumber(tmp_real, tmp_img);
	}
	
	/** Divides two complex numbers by using conjugate function to 
	 * convert the denominator of the equation into a real number
	 * @param c divisor
	 * @return
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber tmp = c;
		tmp.imaginary = tmp.imaginary * -1;
		ComplexNumber numerator = mul(tmp);
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		return new ComplexNumber(numerator.real/denominator, numerator.imaginary/denominator);
		
	}
	
	/**
	 * Calculates the nth power of the complex number 
	 * @param n the power on which we want to potentiate a complex number
	 * @return new ComplexNumber
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Value of n must be greater or equal to zero");
		}
		
		double n_theta = n * getAngle();
		ComplexNumber real = new ComplexNumber(pow(getMagnitude(), n), 0);
		ComplexNumber angle = new ComplexNumber(cos(n_theta), sin(n_theta));		
		
		return real.mul(angle);
		//return new ComplexNumber(pow(root, n) * angle.real , real.angle.imaginary);
		
	}
	
	/** Calculates the nth roots of a complex number 
	 * @param n 
	 * @return ComplexNumber array of k solutions where k = 0,1,2 ...,n-1
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Value of n must be greater or equal to zero");
		}
		ComplexNumber[] solutions = new ComplexNumber[n];
		double ro = pow(getMagnitude(), 1/n);
		double theta = getAngle();
		
		for (int k = 0; k < n; k++) {
			ComplexNumber angle = new ComplexNumber(cos((theta + 2*k*PI)/n), sin((theta + 2*k*PI)/n));
			solutions[k] = new ComplexNumber(ro * angle.real, ro * angle.imaginary);
		}
		
		return solutions;
	}
	
	/**
	 * @return real and imaginary part of a complex number in the form x + yi
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.real != 0) {
			sb.append(this.real);
		}
		if (this.imaginary != 0) {
			if (this.imaginary > 0) {
				sb.append("+");
			} 
			sb.append(this.imaginary).append("i");
		}
		
		return sb.toString();
	}
	
	
}
