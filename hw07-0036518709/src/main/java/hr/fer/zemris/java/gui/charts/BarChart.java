package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
	
	private List<XYValue> values;
	private String descX;
	private String descY;
	private int yMin;
	private int yMax;
	private int yGap;
	
	public BarChart(List<XYValue> values, String descX, String descY, int yMin, int yMax, int yGap) {
		if (yMin < 0) throw new IllegalArgumentException();
		
		if (yMax <= yMin) throw new IllegalArgumentException();
		
		this.values = values;
		this.descX = descX;
		this.descY = descY;
		this.yMin = yMin;
		this.yMax = (int) Math.ceil((double) yMax / yGap) * yGap;
		this.yGap = yGap;
	}

	/**
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return the descX
	 */
	public String getDescX() {
		return descX;
	}

	/**
	 * @return the descY
	 */
	public String getDescY() {
		return descY;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the yGap
	 */
	public int getyGap() {
		return yGap;
	}	
}
