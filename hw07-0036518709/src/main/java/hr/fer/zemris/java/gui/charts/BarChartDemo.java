package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<XYValue> values = new ArrayList<>();
	private String path;
	private BarChart model;
	
	public BarChartDemo(String path, String[] lines) {
		this.setSize(200, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		setData(path, lines);
		initGUI();
	}
	
//	public void drawComp(JFrame frame, JLabel title) {
//		this.add(frame);
//		this.add(title);
//	}
	
	public void setData(String path, String[] lines) {
		String[] coord = lines[2].split("\\s+");
		for (String str : coord) {
			String[] xy = str.split(",");
			values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		
		this.model = new BarChart(values, lines[0], lines[1], 
				Integer.parseInt(lines[3]), Integer.parseInt(lines[4]), Integer.parseInt(lines[5]));

		this.path = path;
	}
	
	public void initGUI() {
		BarChartComponent chart = new BarChartComponent(model);
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(chart, BorderLayout.CENTER);
		
		JLabel title = new JLabel();
		title.setText(path);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(title, BorderLayout.NORTH);
		
	}
		
	public static void main(String[] args) {

		BufferedReader br = null;
		int i = 0;
		String[] lines = new String[6];
		String path = args[0];
		try {
			br = new BufferedReader(new FileReader(path));
			
			while (i < 6) {
				lines[i] = br.readLine();
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new BarChartDemo(path, lines);		
	}
}
