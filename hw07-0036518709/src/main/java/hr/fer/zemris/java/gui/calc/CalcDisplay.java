package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CalcDisplay extends JLabel implements CalcValueListener {

	private static final long serialVersionUID = 1L;

	public CalcDisplay() {	
		this.setBackground(Color.YELLOW);
		this.setOpaque(true);
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		this.setFont(this.getFont().deriveFont(20f));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
		
	}

}
