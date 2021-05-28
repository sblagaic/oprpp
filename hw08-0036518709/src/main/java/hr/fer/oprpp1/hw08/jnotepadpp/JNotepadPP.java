package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.local.LocalizableAction;
import hr.fer.oprpp1.hw08.local.LocalizationProvider;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;
	private DefaultMultipleDocumentModel mdm;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	public static List<LJMenuItem> caseItems = new ArrayList<>();
	public static List<LJMenuItem> sortItems = new ArrayList<>();
	public static List<LJMenuItem> uniqueItem = new ArrayList<>();
	JPanel statusPanel = new JPanel();
	JLabel statusBar = new JLabel();
	
	public JNotepadPP() {
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepad++");
		
		initGUI();
	}
	
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);
		
		statusBar.setBorder(new EmptyBorder(2, 2, 2, 2));
		statusPanel.setLayout(new BorderLayout());
		statusPanel.add(statusBar, BorderLayout.WEST);
		statusPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
		this.getContentPane().add(statusPanel, BorderLayout.SOUTH);
		
		mdm = new DefaultMultipleDocumentModel(statusBar);
		mdm.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (mdm.getSelectedIndex() != -1) {
					Path filePath = mdm.getDocument(mdm.getSelectedIndex()).getFilePath();
					setTitle((filePath == null ? "unnamed" : filePath.toString()) + " - JNotepad++");
				}
				
				mdm.setCurrentDocument(mdm.getDocument(mdm.getSelectedIndex()));
				mdm.updateCaret();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				checkBeforeClose();
				
			}
		});
		
		this.getContentPane().add(mdm, BorderLayout.CENTER);		
		mdm.createNewDocument();
		
		createMenus();
		createToolbars();
		
	}
	
	private Action createDocumentAction = new LocalizableAction("new", flp, "control N", "newDesc") {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			mdm.createNewDocument();
			
		}
	};

	private Action openDocumentAction = new LocalizableAction("open", flp, "control O", "openDesc") {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka " + fileName.getAbsolutePath() + " ne postoji!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			openedFilePath = filePath;
			mdm.loadDocument(openedFilePath);
		}
	};
	
	private Action saveDocumentAction = new LocalizableAction("save", flp, "control S", "saveDesc") {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveDocument();
			
		}
	};
	
	private Action saveDocumentAsAction = new LocalizableAction("saveAs", flp, "control shift S", "saveAsDesc") {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveDocumentAs();
		}
	};
	
	private Action closeDocumentAction = new LocalizableAction("close", flp, "control W", "closeDesc") {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mdm.closeDocument(mdm.getCurrentDocument());
		}
	};	
	
	private Action exitAction = new LocalizableAction("exit", flp, "control E", "exitDesc") {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkBeforeClose();
			
		}
	};

	private Action copyAction = new LocalizableAction("copy", flp, "control C", "none") {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Action copy = new DefaultEditorKit.CopyAction();
			copy.actionPerformed(e);
		}
	};
	
	private Action cutAction = new LocalizableAction("cut", flp, "control X", "none") {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Action cut = new DefaultEditorKit.CutAction();
			cut.actionPerformed(e);
		}
	};
	
	private Action pasteAction = new LocalizableAction("paste", flp, "control V", "none") {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Action paste = new DefaultEditorKit.PasteAction();
			paste.actionPerformed(e);
		}
	};
	
	private Action statsAction = new LocalizableAction("stats", flp, "control T", "none") {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
			int len = doc.getLength();
			int whitespaces = 0;
			int lines = 1;
			
			char[] content = null;
			try {
				content = doc.getText(0, doc.getLength()).toCharArray();
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
			for (char c : content) {
				if (Character.isWhitespace(c))
					whitespaces++;
				
				if (c == '\n')
					lines++;
			}
			
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Your document has " + len + " character" + (len == 1 ? ", " : "s, ") + (len - whitespaces) + 
					" non-blank character" + (len - whitespaces == 1 ? " " : "s ") + "and " + lines + (lines == 1 ? " line" : " lines"), 
					"Statistics", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private void saveDocument() {
		openedFilePath = mdm.getCurrentDocument().getFilePath();
		boolean exists = false;
		
		if(openedFilePath == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
		}
		
		String filePath = openedFilePath.toString();
		Path directory = Paths.get(filePath.substring(0, filePath.lastIndexOf('\\')));
		
		if (Files.exists(openedFilePath)) 
			exists = true;
			
		byte[] podaci = mdm.getDocument(mdm.getSelectedIndex()).getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		if (!exists) {
			try {
				Files.write(openedFilePath, podaci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom zapisivanja datoteke " + openedFilePath.toFile().getAbsolutePath() + 
						".\nPažnja: nije jasno u kojem je stanju datoteka na disku!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka s tim imenom već postoji u " + directory + " direktoriju",
					"Pogreška", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		mdm.getDocument(mdm.getSelectedIndex()).setFilePath(openedFilePath);
		mdm.saveDocument(mdm.getDocument(mdm.getSelectedIndex()), openedFilePath);
	}

	private void saveDocumentAs() {
		openedFilePath = null;
		saveDocument();
	}
	
	private void checkBeforeClose() {
		if (mdm.areAnyModified()) {
			String[] options = new String[] {"Save", "Don't Save", "Cancel"};
		    int response = JOptionPane.showOptionDialog(JNotepadPP.this, "Do you want to save changes?", "JNotepad++",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, options, options[0]);
		    
		    if (response == 0) {
		    	saveDocument();
		    } else if (response == 1) {
		    	Clock.finished = false;
		    	dispose();
		    }
		    
		} else {
			Clock.finished = false;
			dispose();
		}
	}
	
	private String changeCaseTo(String text, ICaseTransform c) {
		char[] znakovi = text.toCharArray();
		for(int i = 0; i < znakovi.length; i++) {
			znakovi[i] = c.changeCase(znakovi[i]);
		}
		return new String(znakovi);
	}	
	
	private void alterContent(ICaseTransform c, String type, boolean asc) {
		Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
		JTextArea textArea = mdm.getCurrentDocument().getTextComponent();	
		
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = 0;
		if(len != 0) {
			offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		} else {
			len = doc.getLength();
		}
		try {
			String text = textArea.getSelectedText();
			if (text != null) {
				if (type.equals("case")) {
					text = changeCaseTo(text, c);
				} else if (type.equals("unique")){
					text = removeDuplicateLines(text);
				} else {
					text = sortLines(text, asc);
				}
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			}
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
		
	}
	
	private String removeDuplicateLines(String text) {
		Set<String> lines = new LinkedHashSet<String>(Arrays.asList(text.split("\\n")));
		StringBuilder sb = new StringBuilder();
		
		for (String line : lines) {
			sb.append(line).append("\n");
		}
		
		return sb.toString();
	}
	
	private String sortLines(String text, boolean ascending) {
		List<String> list = new ArrayList<>(Arrays.asList(text.split("\\n")));
		StringBuilder sb = new StringBuilder();	
		
		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator coll = Collator.getInstance(locale);
		coll.setStrength(Collator.PRIMARY);
		
		if (ascending) {
			Collections.sort(list, coll); 
		} else {
			Collections.sort(list, Collections.reverseOrder());
		}
		
		for (String line : list) {
			sb.append(line).append("\n");
		}
	
		return sb.toString();
	}
	
	private void createCaseMenuItems(LJMenu menu, String key, ICaseTransform c) {
		LJMenuItem menuItem = new LJMenuItem(key, flp);
		menuItem.addActionListener(a -> {
			alterContent(c, "case", false);
		});
		
		caseItems.add(menuItem);
		menu.add(menuItem);
	}
	
	private void createLanguageMenuItems(LJMenu menu, String key) {
		LJMenuItem menuItem = new LJMenuItem(key, flp);
		menuItem.addActionListener(a -> {
			LocalizationProvider.getInstance().setLanguage(key);
		});
		
		menu.add(menuItem);
	}
	
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		//LANGUAGE
		LJMenu languageMenu = new LJMenu("language", flp);
		menuBar.add(languageMenu);
		
		createLanguageMenuItems(languageMenu, "hr");
		createLanguageMenuItems(languageMenu, "en");
		createLanguageMenuItems(languageMenu, "de");
		
		//TOOLS
		LJMenu toolMenu = new LJMenu("tools", flp);
		menuBar.add(toolMenu);
		
		LJMenu caseMenu = new LJMenu("case", flp);
		toolMenu.add(caseMenu);

		createCaseMenuItems(caseMenu, "uppercase", CaseTypes.UPPER);
		createCaseMenuItems(caseMenu, "lowercase", CaseTypes.LOWER);
		createCaseMenuItems(caseMenu, "toggle", CaseTypes.TOGGLE);
		
		LJMenu sortMenu = new LJMenu("sort", flp);
		toolMenu.add(sortMenu);
		
		LJMenuItem ascending = new LJMenuItem("ascending", flp);
		ascending.addActionListener(a -> {
			alterContent(null, "sort", true);
		});
		sortItems.add(ascending);
		sortMenu.add(ascending);
		
		LJMenuItem descending = new LJMenuItem("descending", flp);
		descending.addActionListener(a -> {
			alterContent(null, "sort", false);
		});
		sortItems.add(descending);
		sortMenu.add(descending);
		
		LJMenuItem unique = new LJMenuItem("unique", flp);
		unique.addActionListener(a -> {
			alterContent(null, "unique", false);
		});
		toolMenu.add(unique);
		uniqueItem.add(unique);
		
		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		toolBar.add(new JButton(createDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveDocumentAsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));	
		toolBar.add(new JButton(statsAction));
		
		JComponent clock = new Clock();
		statusPanel.add(clock);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
