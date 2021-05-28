package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("PrimDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	static class PrimListModel<T> implements ListModel<T> {
		private List<T> elementi = new ArrayList<>();
		private List<ListDataListener> promatraci = new ArrayList<>();
		private int curr = 2;
		
		@Override
		public void addListDataListener(ListDataListener l) {
			promatraci.add(l);
		}
		@Override
		public void removeListDataListener(ListDataListener l) {
			promatraci.remove(l);
		}
		
		@Override
		public int getSize() {
			return elementi.size();
		}
		
		@Override
		public T getElementAt(int index) {
			return elementi.get(index);
		}
		
		public void add(T element) {
			int pos = elementi.size();
			elementi.add(element);
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener l : promatraci) {
				l.intervalAdded(event);
			}
		}
		
		public void remove(int pos) {
			elementi.remove(pos);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pos, pos);
			for(ListDataListener l : promatraci) {
				l.intervalRemoved(event);
			}
		}
		
		public boolean getPrime(int n) {
		       if (n == 2) {
		    	   return true;
		           
		       } else if (n % 2 == 0) {
		    	   return false;
		       }
		       
		       for (int i = 3; i <= (int) Math.ceil(Math.sqrt(n)); i += 2) {
		    	   if (n % i == 0) return false;
		       }
		       
		       return true;
		}
		
		public int next() {
			while (!getPrime(curr)) {
				curr++;
				getPrime(curr);
			}
			
			return curr++;
		}
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel<Integer> model = new PrimListModel<>();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		model.add(1);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		JButton sljedeci = new JButton("Sljedeći");
		bottomPanel.add(sljedeci);
		
		sljedeci.addActionListener(e -> {
			model.add(model.next());
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
