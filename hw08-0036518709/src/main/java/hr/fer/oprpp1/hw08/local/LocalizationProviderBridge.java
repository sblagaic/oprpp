package hr.fer.oprpp1.hw08.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private boolean connected;
	private ILocalizationListener listener;
	private ILocalizationProvider parent;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.parent = provider;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	public void connect() {
		if (!connected) {
			parent.addLocalizationListener(listener);
			connected = true;
		}
	}
	
	public void disconnect() {
		if (connected) {
			parent.removeLocalizationListener(listener);
			connected = false;
		}
	}
	
	public String getString(String language) {
		//return this.bundle.getString(language);
		return parent.getString(language);
	}
}
