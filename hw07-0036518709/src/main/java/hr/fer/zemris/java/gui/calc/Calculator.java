package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.IMiscAction;
import hr.fer.zemris.java.gui.layouts.IUnaryOperator;
import hr.fer.zemris.java.gui.layouts.MiscActions;
import hr.fer.zemris.java.gui.layouts.MyListener;
import hr.fer.zemris.java.gui.layouts.Operators;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import hr.fer.zemris.java.gui.layouts.UnaryListener;
import hr.fer.zemris.java.gui.layouts.UnaryOperators;

public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private CalcModelImpl model;
	public static boolean inv = false;
	private List<DoubleBinaryOperator> binOps;
	private List<IUnaryOperator> unOps;
	private List<JButton> unBtns;
	public static Stack<String> stack;
	
	public Calculator() {
		this.model = new CalcModelImpl();
		this.binOps = new ArrayList<>();
		this.unOps = new ArrayList<>();
		this.unBtns = new ArrayList<>();
		Calculator.stack = new Stack<>();
		binOps.add((d1, d2) -> d1 / d2);
		binOps.add((d1, d2) -> d1 * d2);
		binOps.add((d1, d2) -> d1 - d2);
		binOps.add((d1, d2) -> d1 + d2);
		
		unOps.add(UnaryOperators.ONE_OVER_X);
		unOps.add(UnaryOperators.SIN);
		unOps.add(UnaryOperators.LOG);
		unOps.add(UnaryOperators.COS);
		unOps.add(UnaryOperators.LN);
		unOps.add(UnaryOperators.TAN);
		unOps.add(UnaryOperators.CTG);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	/**
	 * Creates digit buttons
	 * @param value value of digit button
	 * @param rcp button location in the window
	 * @param cp
	 */
	public void setDigit(int value, RCPosition rcp, Container cp) {
		JButton btn = new JButton(String.valueOf(value)); 
		btn.addActionListener(a -> {
			model.insertDigit(value);
		});
		btn.setFont(btn.getFont().deriveFont(20f));
		btn.setBackground(Color.LIGHT_GRAY);
		cp.add(btn, new RCPosition(rcp.getRow(), rcp.getColumn()));
	}
		
	/**
	 * Creates unary function buttons
	 * @param inv value of invert functions button
	 * @param counter
	 * @param rcp button location in the window
	 * @param cp
	 */
	public void setUnaryButtons(int counter, RCPosition rcp, Container cp) {
		JButton btn = new JButton(Operators.GEOM().get(counter));
		btn.setFont(btn.getFont().deriveFont(20f));
		btn.addActionListener(new UnaryListener(unOps.get(counter), model));
		btn.setBackground(Color.LIGHT_GRAY);
		cp.add(btn, rcp);
		unBtns.add(btn);
	}
	
	/**
	 * Sets button text for unary function buttons
	 */
	public void setUnaryBtnText() {
		for (int i = 0; i < unBtns.size(); i++) {
			unBtns.get(i).setText(inv ? Operators.GEOM_INV().get(i) : Operators.GEOM().get(i));
		}
	}
	
	/**
	 * Creates binary function buttons
	 * @param i
	 * @param op DoubleBinaryOperator which is executed on button click
	 * @param cp
	 */
	public void setBinOpButtons(int i, DoubleBinaryOperator op, Container cp) {
		JButton btn = new JButton(Operators.COMMON().get(i - 2)); 
		btn.addActionListener(a -> {
			if (model.isActiveOperandSet()) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setActiveOperand(result);
				model.freezeValue(String.valueOf(result));
				
			} else {
				model.setActiveOperand(model.getValue());
			}
			model.setPendingBinaryOperation(op);
			
		});
		btn.setFont(btn.getFont().deriveFont(20f));
		btn.setBackground(Color.LIGHT_GRAY);
		cp.add(btn, new RCPosition(i, 6));
	}
	
	/**
	 * Creates miscellaneous buttons
	 * @param name button name
	 * @param rcp button location in the window
	 * @param cp
	 * @param action action which is executed on button click
	 * @return JButton
	 */
	public JButton setMiscButtons(String name, RCPosition rcp, Container cp, IMiscAction action) {
		JButton btn = new JButton(name); 
		btn.addActionListener(new MyListener(action, model));
		btn.setFont(btn.getFont().deriveFont(20f));
		btn.setBackground(Color.LIGHT_GRAY);
		cp.add(btn, rcp);
		
		return btn;
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(0));
		
		CalcDisplay display = new CalcDisplay();
		model.addCalcValueListener(display);
		
		cp.add(display, new RCPosition(1, 1));
		
		int counter = 9;
		for (int j = 2; j <= 4; j++) {
			for (int i = 5; i >= 3; i--) {
				setDigit(counter, new RCPosition(j, i), cp);
				counter--;
			}
		}
		
		setDigit(0, new RCPosition(5, 3), cp);
		
		setMiscButtons("=", new RCPosition(1, 6), cp, MiscActions.EQUALS);
		JButton btnPot = setMiscButtons("x^n", new RCPosition(5, 1), cp, MiscActions.POT);
		setMiscButtons("+/-", new RCPosition(5, 4), cp, MiscActions.SWAP);
		setMiscButtons(".", new RCPosition(5, 5), cp, MiscActions.DECIMAL);
		setMiscButtons("clr", new RCPosition(1, 7), cp, MiscActions.CLEAR);
		setMiscButtons("reset", new RCPosition(2, 7), cp, MiscActions.RESET);
		setMiscButtons("push", new RCPosition(3, 7), cp, MiscActions.PUSH);
		setMiscButtons("pop", new RCPosition(4, 7), cp, MiscActions.POP);
		
		for (int i = 2; i <= 5; i++) {
			setBinOpButtons(i, binOps.get(i - 2), cp);
		}
		
		//UNARY OPERATORS ---------------------------------------
		counter = 0;
		for (int j = 2; j <= 4; j++) {
			for (int i = 1; i <= 2; i++) {
				setUnaryButtons(counter, new RCPosition(j, i), cp);
				counter++;
			}
		}
		
		setUnaryButtons(6, new RCPosition(5, 2), cp);
		
		JCheckBox invCheck = new JCheckBox("inv");
		invCheck.addActionListener(a -> {
			inv = invCheck.isSelected();
			setUnaryBtnText();
			btnPot.setText(inv ? "x^(1/n)" : "x^n");
		});
		invCheck.setSize(invCheck.getPreferredSize());
		invCheck.setFont(invCheck.getFont().deriveFont(20f));
		invCheck.setBackground(Color.LIGHT_GRAY);
		cp.add(invCheck, new RCPosition(5, 7));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
