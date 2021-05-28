package hr.fer.zemris.java.gui.layouts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;

public class UnaryListener implements ActionListener {

	private IUnaryOperator op;
	private CalcModelImpl model;
	
	public UnaryListener(IUnaryOperator op, CalcModelImpl model) {
		this.op = op;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double result = op.calculate(model.getValue());
		model.setValue(Calculator.inv ? 1 / result : result);
	}
}
