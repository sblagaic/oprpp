package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {

	private boolean editable;
	private boolean positive;
	private String digits;
	private double value;
	private String frozen;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> clc;
	
	public CalcModelImpl() {
		this.editable = true;
		this.positive = true;
		this.digits = new String();
		this.frozen = null;
		this.activeOperand = null;
		this.clc = new ArrayList<>();
	}
	
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		clc.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		clc.remove(l);
	}

	@Override
	public double getValue() {
		return positive ? this.value : this.value * -1;
	}

	@Override
	public void setValue(double value) {
		this.value = value >= 0 ? value : value * -1;
		if (value == Double.POSITIVE_INFINITY) {
			this.digits = "Infinity";
			
		} else if (value == Double.NEGATIVE_INFINITY) {
			this.digits = "-Infinity";
			
		} else if (value == Double.NaN) {
			this.digits = "NaN";
			
		} else {
			this.digits = String.valueOf(this.value);
//			if (value >= 0) {
//				positive = true;
//			} else {
//				positive = false;
//			}
			positive = value >= 0;
		}
		
		this.editable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return this.editable;
	}

	@Override
	public void clear() {
		digits = "";
		value = 0;
		positive = true;
		editable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		activeOperand = null;
		pendingOperation = null;
		digits = "";
		value = 0;
		positive = true;
		editable = true;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) throw new CalculatorInputException();
		
		positive = !positive;
		frozen = null;
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable || digits.equals("")) throw new CalculatorInputException();
		
		if (digits.indexOf(".") != -1) throw new CalculatorInputException();
		
		digits += ".";
		frozen = null;
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) throw new CalculatorInputException();

		digits += String.valueOf(digit);
		
		int ind = digits.indexOf(".");
		if (digits.length() >= 2 && ind == -1 && digits.charAt(0) == '0') {
			digits = digits.substring(1);
		}
		
		if (ind >= 2 && digits.charAt(0) == '0') 
			digits.replace(digits.substring(0, ind - 1),  "");

		if (digits.length() > 308) throw new CalculatorInputException();
		
		value = Double.parseDouble(digits);
		frozen = null;
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (this.activeOperand == null) throw new IllegalStateException();
		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.digits = "";
		this.value = 0;
		this.editable = true;
		this.positive = true;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	@Override
	public void freezeValue(String value) {
		this.frozen = value;
		notifyListeners();
	}

	@Override
	public boolean hasFrozenValue() {
		return this.frozen != null;
	}
	
	@Override
	public String toString() {
		if (hasFrozenValue()) {
			frozen = removeDouble(frozen);
			return frozen;
		} else if (digits.equals("") || value == 0) {
			return positive ? "0" : "-0";	
		} else if (digits.toLowerCase().contains("n")) {
			return digits;
		}

		digits = removeDouble(digits);
		return (positive ? "" : "-") + digits;
	}
	
	/**
	 * Notifies all listeners
	 */
	public void notifyListeners() {
		for (CalcValueListener l : clc) {
			l.valueChanged(this);
		}
	}
	
	/**
	 * Removes .0 from value
	 * @param value
	 * @return string of current value without .0
	 */
	public String removeDouble(String value) {
		int len = value.length();
		if (len >= 2) {
			if (value.substring(len - 2, len).equals(".0")) {
				value = value.substring(0, len - 2);
			}
		}
		
		return value;
	}
}
