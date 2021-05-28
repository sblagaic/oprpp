package hr.fer.oprpp1.hw08.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private String language;
	private ResourceBundle bundle;
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
		this.language = "en";
		Locale locale = Locale.forLanguageTag(this.language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", locale);
	}
	
	public static LocalizationProvider getInstance() {
		 return instance;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(this.language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.prijevodi", locale);
		fire();
	}
	
	public String getLanguage() {
		return this.language;
	}
	
	public String getString(String language) {
		return this.bundle.getString(language);
	}
	
}
