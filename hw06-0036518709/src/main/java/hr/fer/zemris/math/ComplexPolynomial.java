package hr.fer.zemris.math;

/**
 * @author sblag
 *
 */
/**
 * @author sblag
 *
 */
public class ComplexPolynomial {
	
	private final Complex[] factors;
	
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	/**
	 * @return order of the polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Computes a new polynomial this * p
	 * @param p second polynomial factor 
	 * @return new ComplexPolynomial which the result 
	 * of polynomial multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[factors.length + p.factors.length - 1];
		for (int z = 0; z < result.length; z++) {
			result[z] = new Complex(0, 0);
		}
		
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				result[i + j] = result[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(result);
	}
	
	/** 
	 * Computes first derivative of the polynomial
	 * @return first derivative of a polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] derived = new Complex[factors.length - 1];
		for (int i = 1; i < factors.length; i++) {
			derived[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(derived);
	}
	
	
	/**
	 * Computes polynomial value at given point z
	 * @param z point in the form of a complex number 
	 * at which the polynomial is computed
	 * @return polynomial value at point z
	 */
	public Complex apply(Complex z) {
		Complex value = factors[0];
		for (int i = 1; i < factors.length; i++) {
			value = value.add(factors[i].multiply(z.power(i)));
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i > 0; i--) {
			sb.append("(").append(factors[i]).append(")*z^" + i + "+");
		}
		sb.append("(").append(factors[0]).append(")");
		
		return sb.toString();
	}
}
