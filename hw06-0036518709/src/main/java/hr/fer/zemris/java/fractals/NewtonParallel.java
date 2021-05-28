package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {
	
	private static ComplexRootedPolynomial crp;
	private static ComplexPolynomial polynomial;
	private static ComplexPolynomial derived;
	private int ind;
	private int workers;
	private int tracks;
	
	public NewtonParallel() {
		this.workers = Runtime.getRuntime().availableProcessors();
		this.tracks = this.workers * 4;
		this.ind = 1;
	}
	
	public void setCRP(ComplexRootedPolynomial crp) {
		NewtonParallel.crp = crp;
		NewtonParallel.polynomial = crp.toComplexPolynom();
		NewtonParallel.derived = crp.toComplexPolynom().derive();
	}
	
	public static void calculate(double reMin, double reMax, double imMin, double imMax,
			 int width, int height, int ymin, int ymax, short[] data, AtomicBoolean cancel) {
		
		int offset = ymin * width;
		for (int y = ymin; y <= ymax; y++) {
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
	}
	
	public static boolean isNumeric(String strNum) {
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	    return true;
	}
	
	public static void getOption(String[] options, NewtonParallel np) {
		if (options[np.ind].contains("w")) {
			np.workers = NewtonParallel.getOptValue(options, np);
		} else {
			np.tracks = NewtonParallel.getOptValue(options, np);
		}
	}
	
	public static int getOptValue(String[] options, NewtonParallel np) {
		if (np.ind + 1 < options.length) {	
			np.ind++;
			return Integer.parseInt(isNumeric(options[np.ind]) ? options[np.ind++] : 
				options[np.ind - 1].substring(options[np.ind - 1].indexOf("=") + 1));
			
		} else {
			return Integer.parseInt(options[np.ind].substring(options[np.ind++].indexOf("=") + 1));
		}
	}
	
	public static void main(String[] args) {
		
		
		String[] options = new String[args.length];
		
		if (args.length > 0) options = args;
		NewtonParallel np = new NewtonParallel();
		
		if (options.length > 1) 
			NewtonParallel.getOption(options, np);
		
		if (np.ind < options.length) 
			NewtonParallel.getOption(options, np);
		
		if (np.tracks < 1 || np.workers < 1) 
			throw new IllegalArgumentException();
		
		Scanner sc = new Scanner(System.in);
		String input = new String();
		List<Complex> arguments = new ArrayList<>();
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		while (!input.equals("done")) {
			System.out.print("Root " + (arguments.size() + 1) + "> ");
			input = sc.nextLine();
			if (!input.equals("done")) arguments.add(Complex.parse(input));
		}

		sc.close();
		
		Complex[] arg2 = new Complex[arguments.size()];
		arg2 = arguments.toArray(arg2);
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(1, 0), arg2);
		np.setCRP(crp);
		
		FractalViewer.show(new MojProducer(np.workers, np.tracks));	
	}

	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			NewtonParallel.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
		}
	}
	
	
	public static class MojProducer implements IFractalProducer {
		
		private int brojDretvi;
		private int brojTraka;
		
		public MojProducer(int brojDretvi, int brojTraka) {
			this.brojDretvi = brojDretvi;
			this.brojTraka = brojTraka;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			if (brojTraka > height) brojTraka = height;
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			int brojYPoTraci = height / brojTraka;
			System.out.println("Broj dretvi je " + brojDretvi + ", a broj traka je " + brojTraka);
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[brojDretvi];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p == PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci-1;
				if(i == brojTraka - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (NewtonParallel.polynomial.order() + 1), requestNo);
		}
	}
}
