package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.calc.Calculator;

public class MiscActions {
	
	public static final IMiscAction EQUALS = model -> {
		double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
		model.setValue(result);
		model.clearActiveOperand();
		model.setPendingBinaryOperation(null);
	};
	
	public static final IMiscAction POT = model -> {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((d1, d2) -> Math.pow(d1, Calculator.inv ?  1 / d2 : d2));
		model.clear();
	};
	
	public static final IMiscAction SWAP = model -> {
		model.swapSign();
	};
	
	public static final IMiscAction DECIMAL = model -> {
		model.insertDecimalPoint();
	};
	
	public static final IMiscAction CLEAR = model -> {
		model.clear();
	};
	
	public static final IMiscAction RESET = model -> {
		model.clearAll();
	};
	
	public static final IMiscAction PUSH = model -> {
		Calculator.stack.push(model.toString());
		model.clear();
	};
	
	public static final IMiscAction POP = model -> {
		model.setValue(Double.parseDouble(Calculator.stack.pop()));
	};
}
