package hr.fer.zemris.math;

public class ComplexDemo {

	public static void main(String[] args) {
//		Complex c1 = new Complex(2, 3);
		Complex c2 = Complex.parse("-i");
//		Complex c3 = c1.add(Complex.fromMagnitudeAndAngle(2, 1.57))
//		.divide(c2).power(3).root(2).get(1);
//		System.out.println(c3);
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		
		System.out.println(c2);
	}
}
//-0.7506551793206863-0.6606941817202785i