package hr.fer.zemris.java.gui.layouts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;

public class MyListener implements ActionListener {

	private IMiscAction action;
	private CalcModelImpl model;
	
	public MyListener(IMiscAction action, CalcModelImpl model) {
		this.action = action;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		action.setAction(model);
		
	}

}
