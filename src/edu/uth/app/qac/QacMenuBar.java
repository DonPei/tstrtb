package edu.uth.app.qac;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.component.RecentFileMenu;
import edu.uth.app.qac.dialog.MetaFileDialog;
import edu.uth.app.qac.dialog.ProjectDialog;

@SuppressWarnings("serial")
public class QacMenuBar extends JMenuBar {
	private final static int MAX_RECENT_FILES = 20;
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	private QacFrame 		frame 	= null;
	private RecentFileMenu 	menuRecentFiles = null;
	private JMenuItem 	openFile 			= null;
	private boolean settingsAdded = false;

	RtbCardPanel menuRtbCardPanel = null;
	
	public QacMenuBar(QacFrame frame) {
		this.frame 				= frame;
		JMenu fileMenu  		= new JMenu("File      ");
		JMenu adminMenu  			= new JMenu("   Admin                    ");
		setFileMenu(fileMenu);
		setAdminMenu(adminMenu);

		add(fileMenu);
		add(adminMenu);
	}
	
	public RtbCardPanel getMenuRtbCardPanel() {
		return menuRtbCardPanel;
	}
	
	private JPanel genSettingsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		Insets insets = new Insets(1, 0, 1, 0);
		GridBagConstraints gbc;
		
		menuRtbCardPanel = new RtbCardPanel();
		QacSettingPanel studyIdSettingPanel = new QacSettingPanel(frame, 1);
		menuRtbCardPanel.addCard(studyIdSettingPanel, RtbCardPanel.Key.METAPANEL);
		
		studyIdSettingPanel = new QacSettingPanel(frame, 2);	
		menuRtbCardPanel.addCard(studyIdSettingPanel, RtbCardPanel.Key.INTRODUCTIONPANEL);
		
		studyIdSettingPanel = new QacSettingPanel(frame, 3);
		menuRtbCardPanel.addCard(studyIdSettingPanel, RtbCardPanel.Key.TABLEPANEL);
		
		if(frame.getProject().getIId()==32) {
			menuRtbCardPanel.showCard(RtbCardPanel.Key.INTRODUCTIONPANEL);
		} else if (frame.getProject().getIId()==33){
			menuRtbCardPanel.showCard(RtbCardPanel.Key.TABLEPANEL);
		} else if (frame.getProject().getIId()==31){
			menuRtbCardPanel.showCard(RtbCardPanel.Key.METAPANEL);
		}
		
		String [] moduleString = new String[]{"Meta Files  ", "Dictionary Files  ", "Data Files  "};
		int n = moduleString.length;
		ButtonGroup moduleRadioGroup = new ButtonGroup();
		JRadioButton  [] moduleRadioButton = new JRadioButton[n];
		int k = 0;
		for(int i=0; i<n; i++) {
			final int j = i+1;
			moduleRadioButton[i] = new JRadioButton(moduleString[i], frame.getMode()==j);
			moduleRadioButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if(((JRadioButton)event.getSource()).isSelected()) {
						frame.setMode(j); 
						if(j==1) {
							menuRtbCardPanel.showCard(RtbCardPanel.Key.METAPANEL);
						} else if(j==2) {
							menuRtbCardPanel.showCard(RtbCardPanel.Key.INTRODUCTIONPANEL);
						} else if(j==3) {
							menuRtbCardPanel.showCard(RtbCardPanel.Key.TABLEPANEL);
						}
					}
				}
			});
			moduleRadioGroup.add(moduleRadioButton[i]);
			gbc= new GridBagConstraints(0, k++, 1, 1, 0.01, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			panel.add(moduleRadioButton[i]);
		}
		
		gbc= new GridBagConstraints(0, k++, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		panel.add(menuRtbCardPanel);		
		
		return panel;
	}
	
	public void addSettings() {
		if(settingsAdded) {
			return;
		}
		settingsAdded = true;
		add(Box.createHorizontalGlue());
		//add(new JLabel("                                             "));
		JPanel panel = genSettingsPanel();
//		Dimension dBt = new Dimension(1900,30); //Sets the size of the button in the  JMenuBar
//		//panel.setMinimumSize(dBt);
//		panel.setPreferredSize(dBt);
//		//panel.setMaximumSize(dBt);
		add(panel);
	}

	public void setFrame(QacFrame frame) 		{ this.frame = frame; }
	public JMenuItem getJMenuItemOpenFile() 		{ return openFile; }

	public void removeRecentFile( String filename ) { menuRecentFiles.removeFile( filename ); }
	public void addRecentFile( String filename ) 	{ menuRecentFiles.addFile( filename ); }
	public ArrayList<String> getRecentFileList() 	{ return menuRecentFiles.getFileList(); }

	public void setFileMenu(JMenu jMenu) {
		JMenuItem jMenuItem = null;
		jMenuItem  	= new JMenuItem("Open...");
		jMenuItem.setToolTipText("Open a project File");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK) );
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener(e -> {
			FileNameExtensionFilter exts[] = new FileNameExtensionFilter [] {
					new FileNameExtensionFilter("(*.qac)", "qac")};
			frame.openProjectWithExtension(exts);
		});
		openFile = jMenuItem;

		jMenuItem  	= new JMenuItem("New...");
		jMenuItem.setToolTipText("Create a new project");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK) );
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String aTitle = new String("New Project");
				ProjectDialog dialog = new ProjectDialog(frame, aTitle, true);
				dialog.showDialog();
			}
		});
		jMenu.addSeparator();

		jMenuItem  	= new JMenuItem("Save...");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK) );
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				frame.saveProject();
			}
		});

		jMenuItem  	= new JMenuItem("Save As...");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				frame.saveProjectAs();
			}
		});
		jMenu.addSeparator();

		menuRecentFiles = new RecentFileMenu("Recent", MAX_RECENT_FILES);
		menuRecentFiles.addFileMenuListener(frame);
		jMenu.add(menuRecentFiles);

		jMenuItem  	= new JMenuItem("Edit Recent");
		//jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String aTitle = new String("Edit Recent");
				//				EditRecentDialog dialog = new EditRecentDialog(_frame, aTitle,
				//						_frame.getPreferences(), _menuRecentFiles, false);
				//				dialog.showDialog();
			}
		});

		jMenu.addSeparator();

		jMenuItem  	= new JMenuItem("Exit");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK) );
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				frame.exit(false);
			}
		});
	}
	
	public void setAdminMenu(JMenu jMenu) {
		JMenuItem jMenuItem = null;
		jMenuItem  	= new JMenuItem("Create a new meta file");
		jMenuItem.setToolTipText("create a meta file from scratch");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK) );
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener(e -> {
			if(frame.getProject()==null) return;
			MetaFileDialog dialog = new MetaFileDialog(frame, "Create a Meta File", false);
			dialog.showDialog();
		});
		
		jMenuItem  	= new JMenuItem("Generate dictionary exception");
		jMenuItem.setToolTipText("Generate dictionary exception");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK) );
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener(e -> {
			if(frame.getProject()==null) return;
			MetaFileDialog dialog = new MetaFileDialog(frame, "Create a Meta File", false);
			dialog.showDialog();
		});

		
	}



}
