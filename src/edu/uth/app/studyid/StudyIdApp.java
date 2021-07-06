package edu.uth.app.studyid;

import javax.swing.JFrame;
import javax.swing.UIManager;

import edu.uth.resources.GhcImageLoader;



//https://securestor.uth.tmc.edu/#/favorites
//With kiteworks you can create a secure single point of access to your enterprise's content
//SecureStor@uth.tmc.edu
public class StudyIdApp {
	private int exitCode = 1; 
	private StudyIdFrame frame = null;

	public StudyIdApp(JFrame parent, int exitCode, int mode, int processID, String title, int appId) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.exitCode = exitCode;
		start(exitCode, mode, processID, title, appId);
	}

	public void start(int exitCode, int mode, int processID, String title, int appId) {
		frame = new StudyIdFrame(exitCode, title, appId);
		frame.setProcessID(processID);
		frame.setVisible(true);
		frame.setIconImage(GhcImageLoader.getImage(GhcImageLoader.STUDYID));
	}

	public void exit() {
		if (frame != null)
			frame.exit(false);
	}
}

