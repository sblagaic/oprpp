package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private JTextArea textComponent;
	private List<SingleDocumentListener> listeners;
	private boolean modified;
	private Path path;
	
	public DefaultSingleDocumentModel(Path file, String content) {
		this.listeners = new ArrayList<>();
		this.modified = false;
		this.textComponent = new JTextArea();
		this.textComponent.setText(content);
		this.path = file;
		this.textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
				
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return this.textComponent;
		
	}

	@Override
	public Path getFilePath() {
		return this.path;
		
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
		
	}

	@Override
	public boolean isModified() {
		return this.modified;
		
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
		
	}

}
