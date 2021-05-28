package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.gui.calc.ILayout;
import hr.fer.zemris.java.gui.calc.LayoutSizes;

public class CalcLayout implements LayoutManager2 { 
	
	private int gap;
	private HashMap<Component, RCPosition> components = new HashMap<>();
	
	/**
	 * Sets gap variable - the desired space between
	 * rows and columns - to zero
	 */
	public CalcLayout() {
		this.gap = 0;
	}
	
	/**
	 * Sets gap variable - the desired space between
	 * rows and columns - to the given value
	 * @param gap
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public void layoutContainer(Container parent) {
		int x;
		int y;
		int hei = parent.getHeight() / 5;
		int wid = parent.getWidth() / 7;
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			y = (pos.getRow() - 1) * hei + gap;		
			x = (pos.getColumn() - 1) * wid + gap;
			
			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				c.setBounds(0, 0, wid * 5, hei);
			} else {	
				c.setBounds(x, y, wid, hei);
			}
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null) {
			throw new NullPointerException();
		}
		
		if (!(constraints instanceof String) && !(constraints instanceof RCPosition)) {
			throw new IllegalArgumentException();
		}
		
		RCPosition rcp;
		if (constraints instanceof String) {
			rcp = RCPosition.parse((String) constraints);
		} else {
			 rcp = (RCPosition) constraints;
		}
		
		if (rcp.getRow() < 1 || rcp.getRow() > 5 || rcp.getColumn() < 1 || rcp.getColumn() > 7 
				|| (rcp.getRow() == 1 && rcp.getColumn() > 1 && rcp.getColumn() < 6) || components.containsValue(rcp)) {
			throw new CalcLayoutException("Invalid argument");
		}
		components.put(comp, rcp);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSizes.PREF_W, LayoutSizes.PREF_H);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSizes.MIN_W, LayoutSizes.MIN_H);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, LayoutSizes.MAX_H, LayoutSizes.MAX_W);
	}
	
	public Dimension getLayoutSize(Container target, ILayout layoutWidth, ILayout layoutHeight) {
		double maxW = 0;
		double maxH = 0;
		
		for (Component c : components.keySet()) {	
			maxW = layoutWidth.getLayoutSize(c) > maxW ? layoutWidth.getLayoutSize(c) : maxW;
			maxH = layoutHeight.getLayoutSize(c) > maxH ? layoutHeight.getLayoutSize(c) : maxH;
		}
		
		return new Dimension((int) maxW * 7 + 6 * gap, (int) maxH * 5 + 4 * gap);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
}
