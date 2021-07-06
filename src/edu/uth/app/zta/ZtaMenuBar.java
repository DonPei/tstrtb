package edu.uth.app.zta;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;


import edu.uth.app.component.RecentFileMenu;
import edu.uth.app.zta.dialog.ProjectDialog;
import edu.uth.app.zta.dialog.ZipDatabaseDialog;
import edu.uth.swing.GMenuBar;

@SuppressWarnings("serial")
public class ZtaMenuBar extends JMenuBar {
	private final static int MAX_RECENT_FILES = 20;
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	private ZtaFrame 		frame 	= null;
	private RecentFileMenu 	menuRecentFiles = null;
	private JMenuItem 	openFile 			= null;
	private boolean settingsAdded = false;

	public ZtaMenuBar(ZtaFrame frame) {
		this.frame 				= frame;
		JMenu menuFile  		= new JMenu("File           ");
		JMenu menuAction		= new JMenu("   Action");
		setFileMenu(menuFile);
		setActionMenu(menuAction);

		add(menuFile);
		//add(menuAction);
	}
	
	public void addSettings(int appId) {
		if(settingsAdded) {
			return;
		}
		settingsAdded = true;
		//add(Box.createHorizontalGlue());
		Dimension dBt= null ;
		if(appId==0) {
			dBt = new Dimension(250,30);
		} else if(appId==10) {
			dBt = new Dimension(500,30);
		}
		ZtaSettingPanel zfmSettingPanel = new ZtaSettingPanel(frame, appId);
		zfmSettingPanel.setMinimumSize(dBt);
		zfmSettingPanel.setPreferredSize(dBt);
		zfmSettingPanel.setMaximumSize(dBt);
		add(zfmSettingPanel);
	}

	public void setFrame(ZtaFrame frame) 			{ this.frame = frame; }
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
			FileNameExtensionFilter [] exts = null;
			if(frame.getAppId()==0) {
				exts = new FileNameExtensionFilter [] {
						new FileNameExtensionFilter("(*.zta)", "zta")};
			} else if(frame.getAppId()==1) {
				exts = new FileNameExtensionFilter [] {
						new FileNameExtensionFilter("(*.dts)", "dts")};
			}
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


	public void setActionMenu(JMenu jMenu) {
		JMenuItem jMenuItem = null;
		
		jMenuItem  	= new JMenuItem("Fetch Population");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String aTitle = "Fetch Population";
				ZipDatabaseDialog dialog = new ZipDatabaseDialog(frame, aTitle, false);
				dialog.showDialog();
			}
		});
		
		jMenuItem  	= new JMenuItem("Write database");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				Path filePath = Paths.get(frame.getProject().getResultsCwd(), "database.csv");
				System.out.println(filePath);
				frame.getZipDatabase().write(filePath.toString());
			}
		});
		
		
	}

}
