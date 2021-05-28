package hr.fer.zemris.java.gui.calc;

public class LayoutSizes {
	
	public static final ILayout MAX_H = c -> {
		return c.getMaximumSize().getHeight();
	};
	
	public static final ILayout MAX_W = c -> {
		return c.getMaximumSize().getWidth();
	};
	
	public static final ILayout MIN_H = c -> {
		return c.getMinimumSize().getHeight();
	};
	
	public static final ILayout MIN_W = c -> {
		return c.getMinimumSize().getWidth();
	};
	
	public static final ILayout PREF_H = c -> {
		return c.getPreferredSize().getHeight();
	};
	
	public static final ILayout PREF_W = c -> {
		return c.getPreferredSize().getWidth();
	};
}
