package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Element;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	private JTextArea currTextArea;
	private List<MultipleDocumentListener> listeners;
	private JLabel statusBar;

	public DefaultMultipleDocumentModel(JLabel statusBar) {
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.currTextArea = new JTextArea();
		this.statusBar = statusBar;
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		current = new DefaultSingleDocumentModel(null, "");
		int icnIndex = documents.size();
		addSingleListener(icnIndex);
		addCaretListener();
		documents.add(current);
		this.addTab("unnamed", getIcon("icons/red_save.png"), new JScrollPane(current.getTextComponent()), "(unnamed)");
		this.setSelectedIndex(documents.indexOf(current));
		
		return current;
	}

	public void setCurrentDocument(SingleDocumentModel model) {
		this.current = model;
	}
	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.current;

	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null)
			throw new IllegalArgumentException();

		try {
			String text = Files.readString(path, Charset.defaultCharset());
			currTextArea.setText(text);
			current = new DefaultSingleDocumentModel(path, text);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		addSingleListener(documents.size());
		addCaretListener();
		documents.add(current);
		this.addTab(new File(path.toString()).getName(), getIcon("icons/green_save.png"), 
				new JScrollPane(current.getTextComponent()), current.getFilePath().toString());
		this.setSelectedIndex(documents.indexOf(current));
		
		return current;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		this.current.setModified(false);
		int index = documents.indexOf(model);

		this.setIconAt(index, getIcon("icons/green_save.png"));
		this.setTitleAt(index, model.getFilePath().getFileName().toString());
		this.setToolTipTextAt(index, model.getFilePath().toString());
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		this.removeTabAt(index);
		current = documents.get(index == documents.size() - 1 ? index - 1 : index + 1);
		documents.remove(model);
		setSelectedIndex(documents.indexOf(current));
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();

	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
		
	}

	public void addSingleListener(int index) {
		current.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				changeIcon(index);
				
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void addCaretListener() {
		current.getTextComponent().addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				updateCaret();
			}
		});
	}
	
	public void updateCaret() {
		JTextArea textArea = current.getTextComponent();
		Element root = textArea.getDocument().getDefaultRootElement();
		int pos = textArea.getCaretPosition();
		int row = root.getElementIndex(pos);
		int col = pos - root.getElement(row).getStartOffset();
		
		statusBar.setText("length: " + textArea.getDocument().getLength() + "               				"
				+ "Ln:" + (row + 1) + " Col:" + (col + 1) + " Sel:" + (textArea.getSelectionEnd() - textArea.getSelectionStart()));
		
		enableOrDisable(JNotepadPP.caseItems, textArea);
		enableOrDisable(JNotepadPP.sortItems, textArea);
		enableOrDisable(JNotepadPP.uniqueItem, textArea);
	}
	
	public void enableOrDisable(List<LJMenuItem> items, JTextArea textArea) {
		for (LJMenuItem item : items) {
			item.setEnabled(!(textArea.getSelectedText() == null));
		}
	}
	
	public ImageIcon getIcon(String path) {
		byte[] bytes = null;
		InputStream is = this.getClass().getResourceAsStream(path);
		
		if (is == null)
			throw new IllegalArgumentException();
		
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return new ImageIcon(bytes);
	}
	
	public void changeIcon(int index) {
		this.setIconAt(index, getIcon("icons/red_save.png"));
	}

	public boolean areAnyModified() {
		for (SingleDocumentModel model : documents) {
			if (model.isModified()) 
				return true;
		}
		
		return false;
	}
	
}
