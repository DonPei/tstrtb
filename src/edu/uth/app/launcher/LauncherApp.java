package edu.uth.app.launcher;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import edu.uth.kit.project.ProjectPreference;
import edu.uth.resources.GhcImageLoader;
import edu.uth.swing.plaf.laf.GhcLookAndFeel;

// https://github.com/leMaik
// https://github.com/leMaik/swing-material
public class LauncherApp {
	private static String OS = System.getProperty("os.name").toLowerCase();
	public static final String VERSION = "0.3.2"; // major.minor.patch
	public static final String MODULE = "LAUNCHER";

	private LauncherFrame frame = null;
	float[][] data = null;
	static public int iApp = 6;

	public LauncherApp() {
		try {
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GhcLookAndFeel.setDefaultLookAndFeel();
			increaseDefaultFont(1.2f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		start(iApp);
	}

	public static String getVersion() {
		return VERSION;
	}

	public static void increaseDefaultFont(float multiplier) {
		for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				FontUIResource fontUIResource = (FontUIResource) value;
				UIManager.put(key, fontUIResource.deriveFont(fontUIResource.getSize() * multiplier));
			}
		}
	}

	public void start(int iApp) {
		if (iApp == 1 || iApp == 6) {
			frame = new LauncherFrame(iApp);
			frame.getContentPane().setBackground(Color.white);
			frame.setVisible(true);
			frame.setIconImage(GhcImageLoader.getImage(GhcImageLoader.RADXLOGO));
		} else if (iApp == 2) {

		} else if (iApp == 3) {

		}
	}

	public void exit() {
		System.exit(0);
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0
				|| OS.indexOf("nux") >= 0
				|| OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public static void main(String[] args) {
		boolean createdNewDirectory = false;
		try {
			createdNewDirectory = ProjectPreference.setRootDirectory(".uth");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString() + " - UTH cannot run.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		checkHostMachine();
		// String libPath = System.getProperty("java.library.path");
		// loadLib(libPath);

		new LauncherApp();
	}

	private static void checkHostMachine() {
	}

}
