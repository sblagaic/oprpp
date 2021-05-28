package hr.fer.zemris.math;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author sblag
 *
 */
public class ComplexRootedPolynomial {
	
	private final Complex constant;
	private final Complex[] roots;
	
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	
	/**
	 * Computes polynomial value at given point z
	 * @param z
	 * @return polynomial value in the form of a 
	 * complex number
	 */
	public Complex apply(Complex z) {
		Complex value = roots[0];
		for (int i = 1; i < roots.length; i++) {
			value = value.multiply(z.sub(roots[i]));
		}
		
		return value;
	}
	
	
	/**
	 * Converts ComplexRootedPolynomial 
	 * representation to ComplexPolynomial type
	 * @return ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(this.constant);
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(roots[i], Complex.ONE));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(constant.toString()).append(")");
		for (int i = 0; i < roots.length; i++) {
			sb.append("*(z-(").append(roots[i].toString()).append("))");
		}
		
		return sb.toString();
	}
	
	/**
	 * Finds index of closest root for given complex 
	 * number z that is within the given threshold
	 * @param z complex number whose roots are checked
	 * @param threshold
	 * @return index of closest root, 
	 * if there is no such root, returns -1
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		double min = Double.MAX_VALUE;
		int closest = 0;
		double dist = 0;
		for (int i = 0; i < roots.length; i++) {
			dist = sqrt(pow((z.getReal() - roots[i].getReal()), 2) + pow((z.getImag() - roots[i].getImag()), 2));
			if (dist < min) {
				min = dist;
				closest = i;
			}
		}
		
		return min < threshold ? closest : -1;
	}
}
