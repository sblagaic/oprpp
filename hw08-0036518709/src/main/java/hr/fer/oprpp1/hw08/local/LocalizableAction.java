package hr.fer.oprpp1.hw08.local;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

public class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public LocalizableAction(String key, ILocalizationProvider lp, String keyStroke, String desc) {

		this.putValue(Action.NAME, lp.getString(key));
		this.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(keyStroke));
		this.putValue(Action.MNEMONIC_KEY, 
				KeyEvent.VK_N);
		this.putValue(Action.SHORT_DESCRIPTION, lp.getString(desc));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(key));
				putValue(Action.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(keyStroke));
				putValue(Action.MNEMONIC_KEY, 
						KeyEvent.VK_N);
				putValue(Action.SHORT_DESCRIPTION, lp.getString(desc));
			}
		});
	}
}
