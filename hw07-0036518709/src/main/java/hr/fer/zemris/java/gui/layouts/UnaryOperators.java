package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.calc.Calculator;

public class UnaryOperators {
	
	public static final IUnaryOperator ONE_OVER_X = value -> {
		return Calculator.inv ? value : (1 / value); 
	};

	public static final IUnaryOperator SIN = value -> {
		return Math.sin(value); 
	};
	
	public static final IUnaryOperator COS = value -> {
		return Math.cos(value); 
	};
	
	public static final IUnaryOperator TAN = value -> {
		return Math.tan(value); 
	};
	
	public static final IUnaryOperator CTG = value -> {
		return 1 / Math.tan(value);
	};
					
	public static final IUnaryOperator LOG = value -> {
		return Math.log(value); 
	};
	
	public static final IUnaryOperator LN = value -> {
		return Math.log10(value); 
	};
}
