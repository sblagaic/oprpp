package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	
	public static void main(String[] args) {	
		
		String input = new String();
		List<Complex> arguments = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		while (!input.equals("done")) {
			System.out.print("Root " + (arguments.size() + 1) + "> ");
			input = sc.nextLine();
			if (!input.equals("done"))
				arguments.add(Complex.parse(input));
		}
		
		sc.close();
		
		Complex[] arg2 = new Complex[arguments.size()];
		arg2 = arguments.toArray(arg2);
		
		ComplexRootedPolynomial rp = new ComplexRootedPolynomial(new Complex(1, 0), arg2);
		FractalViewer.show(new MojProducer(rp));
	}

	public static class MojProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial crp;
		private ComplexPolynomial polynomial;
		private ComplexPolynomial derived;
		
		public MojProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
			this.polynomial = crp.toComplexPolynom();
			this.derived = this.polynomial.derive();
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int offset = 0;
			short[] data = new short[width * height];
			for (int y = 0; y < height; y++) {
				if (cancel.get()) break;
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					double module = 0;
					int iters = 0;
					double rootThreshold = 0.001;
					int maxIter = 16 * 16;
					double convergenceThreshold = 1e-3;
					Complex zn = new Complex(cre, cim);
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(module > convergenceThreshold && iters < maxIter);
					
					int index = crp.indexOfClosestRootFor(zn, rootThreshold);
					data[offset++] = (short) (index + 1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
