package hr.fer.oprpp1.hw08.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	public AbstractLocalizationProvider() {
		// TODO Auto-generated constructor stub
	}
	
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
