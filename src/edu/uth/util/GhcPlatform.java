package edu.uth.util;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GhcPlatform {
	private static final String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);

	/**
	 * True if current platform is running Linux.
	 */
	public static final boolean IS_LINUX = osName.contains("linux");

	/**
	 * True if current platform is running Mac OS X.
	 */
	public static final boolean IS_MAC = osName.contains("mac");

	/**
	 * True if current platform is running Windows.
	 */
	public static final boolean IS_WINDOWS = osName.contains("windows");

	/**
	 * This URL is where you go when the user takes Help action (shortcut F1)
	 */
	public static final String DOCUMENTATION_URL = "https://docs.oracle.com/javafx/index.html"; // NOI18N

	/**
	 * Javadoc home (for Inspector and CSS Analyzer properties)
	 */
	public final static String JAVADOC_HOME = "https://docs.oracle.com/javase/8/javafx/api/"; // NOI18N

	/**
	 * Requests the underlying platform to open a given file. On Linux, it runs
	 * 'xdg-open'. On Mac, it runs 'open'. On Windows, it runs 'cmd /c start'.
	 *
	 * @param path path for the file to be opened
	 * @throws IOException if an error occurs
	 */
	public static void open(String path) throws IOException {
		List<String> args = new ArrayList<>();
		if (GhcPlatform.IS_MAC) {
			args.add("open"); // NOI18N
			args.add(path);
		} else if (GhcPlatform.IS_WINDOWS) {
			args.add("cmd"); // NOI18N
			args.add("/c"); // NOI18N
			args.add("start"); // NOI18N

			if (path.contains(" ")) { // NOI18N
				args.add("\"html\""); // NOI18N
			}

			args.add(path);
		} else if (GhcPlatform.IS_LINUX) {
			// xdg-open does fine on Ubuntu, which is a Debian.
			// I've no idea how it does with other Linux flavors.
			args.add("xdg-open"); // NOI18N
			args.add(path);
		}

		if (!args.isEmpty()) {
			executeDaemon(args, null);
		}
	}

	/**
	 * Requests the underlying platform to "reveal" the specified folder. On Linux, it
	 * runs 'nautilus'. On Mac, it runs 'open'. On Windows, it runs 'explorer /select'.
	 *
	 * @param filePath path for the folder to be revealed
	 * @throws IOException if an error occurs
	 */
	public static void revealInFileBrowser(File filePath) throws IOException {
		List<String> args = new ArrayList<>();
		String path = filePath.toURI().toURL().toExternalForm();
		if (GhcPlatform.IS_MAC) {
			args.add("open"); // NOI18N
			args.add("-R"); // NOI18N
			args.add(path);
		} else if (GhcPlatform.IS_WINDOWS) {
			args.add("explorer"); // NOI18N
			args.add("/select," + path); // NOI18N
		} else if (GhcPlatform.IS_LINUX) {
			// nautilus does fine on Ubuntu, which is a Debian.
			// I've no idea how it does with other Linux flavors.
			args.add("nautilus"); // NOI18N
			// The nautilus that comes with Ubuntu up to 11.04 included doesn't
			// take a file path as parameter (you get an error popup), you must
			// provide a dir path.
			// Starting with Ubuntu 11.10 (the first based on kernel 3.x) a
			// file path is well managed.
			int osVersionNumerical = Integer.parseInt(System.getProperty("os.version").substring(0, 1)); // NOI18N
			if (osVersionNumerical < 3) {
				// Case Ubuntu 10.04 to 11.04: What you provide to nautilus is
				// the name of the directory containing the file you want to see
				// listed. See DTL-5384.
				path = filePath.getAbsoluteFile().getParent();
				if (path == null) {
					path = "."; // NOI18N
				}
			}
			args.add(path);
		} else {
			// Not Supported
		}

		if (!args.isEmpty()) {
			executeDaemon(args, null);
		}
	}

	/**
	 * Returns true if the modifier key for continuous selection is down.
	 *
	 * @param e mouse event to check (never null)
	 * @return true if the modifier key for continuous selection is down.
	 */
	public static boolean isContinuousSelectKeyDown(MouseEvent e) {
		return e.isShiftDown();
	}

	/**
	 * Returns true if the modifier key for non-continuous selection is down.
	 *
	 * @param e mouse event to check (never null).
	 * @return true if the modifier key for non-continuous selection is down.
	 */
	public static boolean isNonContinousSelectKeyDown(MouseEvent e) {
		return IS_MAC ? e.isMetaDown() : e.isControlDown();
	}

	private static void executeDaemon(List<String> cmd, File wDir) throws IOException {
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			builder = builder.directory(wDir);
			builder.start();
		} catch (RuntimeException ex) {
			throw new IOException(ex);
		}
	}

}
