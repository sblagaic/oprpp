package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private BarChart barChart;
	private List<XYValue> values;
	private List<Integer> yVals = new ArrayList<>();
	
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		this.values = barChart.getValues();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	
		//get longest value
		int maxDigitW = 0;
		FontMetrics fm = g.getFontMetrics();
		int yMarks[] = new int[values.size()];	
		int heights[] = new int[values.size()];
		for (XYValue val : values) {
			maxDigitW = Math.max(maxDigitW, fm.stringWidth(String.valueOf(val.getY())));
			yVals.add(val.getY());
		}
		
		//axis data
		//X-os
		int descXWid = fm.stringWidth(barChart.getDescX());
		int descXHei = fm.getHeight() + 6;
		
		//Y-os 
		int point = getHeight() - 30 - descXHei;
		int yAxisLen = point - 10;
		int descYHei = fm.stringWidth(barChart.getDescY());
		int descYStart = (yAxisLen / 2) + (descYHei / 2);
		int yAxisLeft = maxDigitW + descYHei;
		
		//mixed
		int xAxisLen = getWidth() - 10 - (yAxisLeft + 5);
		
		//Y-os opis
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform orig = g2.getTransform();
		g2.rotate(-Math.PI / 2);
		g2.setColor(Color.BLACK);
		g2.drawString(barChart.getDescY(), -descYStart, 25);
		g2.setTransform(orig);
		
		//X-os tekst
		int descXStart = (xAxisLen / 2) - (descXWid / 2) + yAxisLeft;
		g2.drawString(barChart.getDescX(), descXStart, getHeight() - 10);
		
		//Y-os brojevi
		int pointG = point;
		int gapG = yAxisLen / barChart.getyMax();
		int numGap;
		int numWid;
		int numMax = maxDigitW;
		for (int i = 0; i <= barChart.getyMax(); i++) {
			if (yVals.contains(i)) {
				yMarks[yVals.indexOf(i)] = pointG;
				heights[yVals.indexOf(i)] = (int) 1.0 * (getHeight() - 30 - descXHei) - pointG;
			}
			
			numWid = fm.stringWidth(String.valueOf(i));
			numGap = numMax - numWid;
			pointG -= gapG;
			if (i % barChart.getyGap() == 0) {
				g.drawLine(yAxisLeft + 3, point, yAxisLeft + 7, point);
				g.drawString(String.valueOf(i), yAxisLeft - 20 + numGap, point + 3);
				point -= (gapG * barChart.getyGap());
			}
		}
		
		//draw bars
		int width = (xAxisLen / values.size()) - 2;
		int x = yAxisLeft + 5;
		int count = 1;
		for (Integer mark : yMarks) {
			g.setColor(Color.ORANGE);
			g.fillRect(x, mark, width, heights[count - 1]);
			g.setColor(Color.BLACK);
			//X-os
			g.drawLine(x, getHeight() - 32 - descXHei, x, getHeight() - 28 - descXHei);		
			x += (width + 1);
			//brojevi na x-osi
			g.drawString(String.valueOf(count), x - 1 - width / 2, getHeight() - 30);
			count++;
		}
		
		g.drawLine(x - 1, getHeight() - 32 - descXHei, x - 1, getHeight() - 28 - descXHei);
		
		//ARROW
		int[] xPointsX = {yAxisLeft + 3, yAxisLeft + 7, yAxisLeft + 5};
		int[] yPointsX = {10, 10, 7};
		int[] xPointsY = {getWidth() - 10, getWidth() - 10, getWidth() - 8};
		int[] yPointsY = {getHeight() - 30 - descXHei + 2, getHeight() - 30 - descXHei - 2, getHeight() - 30 - descXHei};
		g.drawPolygon(xPointsX, yPointsX, 3);
		g.drawPolygon(xPointsY, yPointsY, 3);
		
		//OSI
		//Y-os
		g.drawLine(yAxisLeft + 5, 10, yAxisLeft + 5, getHeight() - 40);
		//X-os
		g.drawLine(10 + descYHei, getHeight() - 30 - descXHei, getWidth() - 10, getHeight() - 30 - descXHei);
		
	}

	@Override
	public Dimension getPreferredSize() {
		//return new Dimension(values.size() * 10, 50);
		return new Dimension(200, 200);

	}
	
}
