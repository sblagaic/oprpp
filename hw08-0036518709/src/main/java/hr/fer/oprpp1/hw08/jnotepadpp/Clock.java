package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class Clock extends JComponent {

	private static final long serialVersionUID = 1L;

	volatile String vrijeme;
	public static boolean finished = true;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public Clock() {
		updateTime();

		Thread t = new Thread(() -> {
			while (finished) {
				try {
					Thread.sleep(500);
				} catch (Exception ex) {
					
				}
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void updateTime() {
		vrijeme = formatter.format(LocalDateTime.now());
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(ins.left, ins.top, dim.width - ins.left - ins.right,
				dim.height - ins.top - ins.bottom);

		g.drawString(vrijeme, r.x + (r.width) / 2 + 80, r.y + r.height / 2 + 4);
	}
}
