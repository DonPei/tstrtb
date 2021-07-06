package edu.uth.app.zta;

import javax.swing.JFrame;
import javax.swing.UIManager;

import edu.uth.resources.GhcImageLoader;



public class ZtaApp {
	private int exitCode = 1; 
	private ZtaFrame frame = null;

	public ZtaApp(JFrame parent, int exitCode, int mode, int processID, String title, int appId) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.exitCode = exitCode;
		start(exitCode, mode, processID, title, appId);
	}

	public void start(int exitCode, int mode, int processID, String title, int appId) {
		frame = new ZtaFrame(exitCode, title, appId);
		frame.setProcessID(processID);
		frame.setVisible(true);
		if(appId==0) {
			frame.setIconImage(GhcImageLoader.getImage(GhcImageLoader.ZIPCODEAPP));
		} else if(appId==10) {
			frame.setIconImage(GhcImageLoader.getImage(GhcImageLoader.CALENDAR));
		}
	}

	public void exit() {
		if (frame != null)
			frame.exit(false);
	}
}

