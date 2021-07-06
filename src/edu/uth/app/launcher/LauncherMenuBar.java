package edu.uth.app.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.commons.io.FileUtils;
import edu.uth.swing.GMenuBar;

@SuppressWarnings("serial")
public class LauncherMenuBar extends JMenuBar {
	LauncherFrame 	frame 	= null;

	public LauncherMenuBar(LauncherFrame frame, int iApp) {
		this.frame 		= frame;
		JMenu menuFile = new JMenu("   File");
		JMenu menuUtil		= new JMenu("   Utility");
		JMenu menuTool		= new JMenu("   Tools");
		JMenu menuHelp  	= new JMenu("   Help");

		setFileMenu(frame, menuFile);
		setUtilMenu(frame, menuUtil);
		setToolMenu(frame, menuTool);
		setHelpMenu(frame, menuHelp);

		add(menuFile);
		add(menuHelp);
	}

	public void setFileMenu(LauncherFrame frame, JMenu jmenu) {
		JMenuItem jMenuItem    	= null;
		jMenuItem    	= new JMenuItem("Exit");
		jMenuItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK) );
		jmenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				frame.exit();
				System.exit(0);
			}
		});
	}

	public void setToolMenu(LauncherFrame frame, JMenu jMenu) {
		JMenuItem jMenuItem    	= null;

		jMenuItem  	= new JMenuItem("Generate License");
		jMenuItem.setToolTipText("Generate a license request");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				
			}
		});

		jMenuItem  	= new JMenuItem("Clean Temporary Folders");
		jMenuItem.setToolTipText("junk files will be deleted");
		//_menuUtil.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String 	tmpPath = System.getProperty("java.io.tmpdir");
				File directory= new File(tmpPath);
				File [] list = directory.listFiles();
				for (File file : list) {
					if(file.isDirectory()) {
						String name = file.getName();
						if(name.contains("mpz")) {
							try {
								FileUtils.forceDelete(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							//System.out.println(name);
						}
					}
				}
			}
		});



		jMenuItem    	= new JMenuItem("ColorMap Gallery");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String aTitle = new String("ColorMap Gallery");
			}
		});

		jMenuItem    	= new JMenuItem("ColorUtil Gallery");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String aTitle = new String("ColorUtil Gallery");
//				ColorGalleryDialog dialog = new ColorGalleryDialog(frame, aTitle, false, 1);
//				dialog.showDialog();
			}
		});

	}

	
	public void setUtilMenu(LauncherFrame frame, JMenu jMenu) {
		JMenuItem jMenuItem  	= null;

		jMenuItem  	= new JMenuItem("File Type Converter");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			}
		});
		jMenu.addSeparator();

		jMenuItem  	= new JMenuItem("Data Conditioning");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			}
		});
		jMenu.addSeparator();

		jMenuItem  	= new JMenuItem("Data Volume Viewer");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			}
		});
		jMenu.addSeparator();

		
		jMenuItem    	= new JMenuItem("Make Movie");
		//jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				String aTitle = new String("Make Movie");
				//				MovieDialog dialog = new MovieDialog(frame, aTitle, false, frame.getFolderMoviePath());
				//				dialog.showDialog();
			}
		});
	}

	public void setHelpMenu(LauncherFrame frame, JMenu jMenu) {
		JMenuItem jMenuItem  	= null;
		//		jMenuItem  	= new JMenuItem("Online Doc");
		//		jMenuItem.setToolTipText("get documents and training materials");
		//		_menuHelp.add(jMenuItem);
		//		jMenuItem.addActionListener( new ActionListener() {
		//			public void actionPerformed( ActionEvent e ) {
		//				String folder = "\\\\corp.halliburton.com\\Team\\Pinnacle\\HOU\\Development\\AdvProcessing\\UCDM_App\\doc";
		//				File directory = new File(folder);
		//				try {
		//					Desktop.getDesktop().open(directory);
		//				} catch (IOException e1) {
		//					//e1.printStackTrace();
		//					JOptionPane.showMessageDialog(null, "\nDocuments at: \n "+folder,
		//							"Info", JOptionPane.INFORMATION_MESSAGE);
		//				} catch (java.lang.IllegalArgumentException e1) {
		//					//e1.printStackTrace();
		//					JOptionPane.showMessageDialog(null, "\nDocuments at: \n "+folder,
		//							"Info", JOptionPane.INFORMATION_MESSAGE);
		//				}
		////				File[] contents = directory.listFiles();
		////				for ( File f : contents) {
		////				  System.out.println(f.getAbsolutePath());
		////				}
		//			}
		//		});
		//		_menuHelp.addSeparator();

		jMenuItem  	= new JMenuItem("Web Page");
		jMenuItem.setToolTipText("get documents and training materials");
		//jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
			}
		});
		jMenu.addSeparator();


		jMenuItem  	= new JMenuItem("Update Software...");
		jMenuItem.setToolTipText("automatically update all modules");
		//_menuHelp.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				//updateSoftware();
				//				String aTitle = new String("Update Software");
				//				ServerDialog dialog = new ServerDialog(_frame, aTitle);
				//				dialog.setLauncherFrame(_frame);
				//				dialog.showDialog();
			}
		});
		//_menuHelp.addSeparator();

		jMenuItem  	= new JMenuItem("About...");
		jMenuItem.setToolTipText("About");
		jMenu.add(jMenuItem);
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				JOptionPane.showMessageDialog(null, "\nRADx-Rad Toolbox "+LauncherApp.VERSION,
						"Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

}

