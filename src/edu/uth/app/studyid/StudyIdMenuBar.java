package edu.uth.app.studyid;

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

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.component.RecentFileMenu;
import edu.uth.app.studyid.dialog.ProjectDialog;

@SuppressWarnings("serial")
public class StudyIdMenuBar extends JMenuBar {
	private final static int MAX_RECENT_FILES = 20;
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	private StudyIdFrame 		frame 	= null;
	private RecentFileMenu 	menuRecentFiles = null;
	private JMenuItem 	openFile 			= null;
	private boolean settingsAdded = false;
	private int mode = 3;

	StudyIdSettingPanel studyIdSettingPanel2 = null;
	StudyIdSettingPanel studyIdSettingPanel3 = null;
	
	RtbCardPanel rtbCardPanel = null;
	public StudyIdMenuBar(StudyIdFrame frame) {
		this.frame 				= frame;
		JMenu menuFile  		= new JMenu("File           ");
		JMenu menuAction		= new JMenu("   Action");
		setFileMenu(menuFile);

		add(menuFile);
		
		rtbCardPanel = new RtbCardPanel();
		Dimension dBt= null ;
		dBt = new Dimension(180,30); //630,30
		StudyIdSettingPanel studyIdSettingPanel = new StudyIdSettingPanel(frame, 0);
		studyIdSettingPanel.setMinimumSize(dBt);
		studyIdSettingPanel.setPreferredSize(dBt);
		studyIdSettingPanel.setMaximumSize(dBt);		
		rtbCardPanel.addCard(studyIdSettingPanel, RtbCardPanel.Key.INTRODUCTIONPANEL);
		
		studyIdSettingPanel = new StudyIdSettingPanel(frame, 0);
		studyIdSettingPanel.setMinimumSize(dBt);
		studyIdSettingPanel.setPreferredSize(dBt);
		studyIdSettingPanel.setMaximumSize(dBt);		
		rtbCardPanel.addCard(studyIdSettingPanel, RtbCardPanel.Key.TABLEPANEL);
	}
	
	public RtbCardPanel getRtbCardPanel() {
		return rtbCardPanel;
	}
	
	public void addSettings() {
		if(settingsAdded) {
			return;
		}
		settingsAdded = true;
		//add(Box.createHorizontalGlue());
		JPanel panel = genSettingsPanel();
		Dimension dBt= null ;
		dBt = new Dimension(800,30);
		panel.setMinimumSize(dBt);
		panel.setPreferredSize(dBt);
		panel.setMaximumSize(dBt);
		add(panel);
	}
	
	private JPanel genSettingsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		Insets insets = new Insets(1, 0, 1, 0);
		GridBagConstraints gbc;
		
		String [] moduleString = new String[]{"mode 1  ", "mode 2  "};
		int n = moduleString.length;
		ButtonGroup moduleRadioGroup = new ButtonGroup();
		JRadioButton  [] moduleRadioButton = new JRadioButton[n];
		int k = 0;
//		for(int i=0; i<n; i++) {
//			final int j = i;
//			moduleRadioButton[i] = new JRadioButton(moduleString[i], frame.getMode()==i);
//			moduleRadioButton[i].addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent event) {
//					if(((JRadioButton)event.getSource()).isSelected()) {
//						frame.setMode(j); 
//						getRtbCardPanel().showCard(j==0?
//							RtbCardPanel.Key.INTRODUCTIONPANEL:RtbCardPanel.Key.TABLEPANEL);
//					}
//				}
//			});
//			moduleRadioGroup.add(moduleRadioButton[i]);
//			gbc= new GridBagConstraints(0, k++, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
//			panel.add(moduleRadioButton[i]);
//		}
		
		Dimension dBt = new Dimension(180,30); //630,30
		rtbCardPanel.setMinimumSize(dBt);
		rtbCardPanel.setPreferredSize(dBt);
		rtbCardPanel.setMaximumSize(dBt);
		
		gbc= new GridBagConstraints(0, k++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
		panel.add(rtbCardPanel);		
		
		return panel;
	}

	public void setFrame(StudyIdFrame frame) 		{ this.frame = frame; }
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
					new FileNameExtensionFilter("(*.sig)", "sig")};
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


}
