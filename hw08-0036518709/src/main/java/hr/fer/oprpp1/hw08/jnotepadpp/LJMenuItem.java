package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JMenuItem;

import hr.fer.oprpp1.hw08.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.local.ILocalizationProvider;

public class LJMenuItem extends JMenuItem {

	private static final long serialVersionUID = 1L;
	private String title;
	
	public LJMenuItem(String key, ILocalizationProvider lp) {
		title = lp.getString(key);
		setText(title);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				title = lp.getString(key);
				setText(title);
			}
		});
	}
}
