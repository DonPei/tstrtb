package edu.uth.app.qac;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.commons.io.FilenameUtils;

import edu.uth.app.launcher.LauncherApp;
import edu.uth.app.qac.validator.DataValidator;
import edu.uth.app.qac.validator.DictionaryValidator;
import edu.uth.app.qac.validator.MetaValidator;
import edu.uth.kit.project.Project;
import edu.uth.kit.project.ProjectPreference;
import edu.uth.resources.GhcImageLoader;
import edu.uth.util.IOUtil;

public class QacApp {
	private int exitCode = 1;
	private QacFrame frame = null;

	public QacApp(int toolId, String inputFileName, String dictionaryFileName) {
		if (toolId == 1) {
			process(toolId, inputFileName, null, null);
		} else if (toolId == 2) {
			int[] checkListColIndex = new int[] { 0, 3, 4, 5 };
			process(toolId, inputFileName, checkListColIndex, null);
		} else if (toolId == 3) {
			process(toolId, inputFileName, null, dictionaryFileName);
		}
	}

	public QacApp(JFrame parent, int exitCode, int toolId, int processID, String title, int appId) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.exitCode = exitCode;
		start(exitCode, toolId, processID, title, appId);
	}

	public void start(int exitCode, int toolId, int processID, String title, int appId) {
		frame = new QacFrame(exitCode, title, appId);
		frame.setProcessID(processID);
		frame.setVisible(true);
		frame.setIconImage(GhcImageLoader.getImage(GhcImageLoader.STUDYID));
	}

	public void exit() {
		if (frame != null)
			frame.exit(false);
	}

	private void process(int toolId, String inputFileName, int[] checkListColIndex, String dictionaryFileName) {
		String text = null;
		if (toolId == 1) {
			MetaValidator metaValidator = new MetaValidator(null, null);
			text = metaValidator.validate(inputFileName);
		} else if (toolId == 2) {
			DictionaryValidator dictionaryValidator = new DictionaryValidator(null, null, checkListColIndex);
			text = dictionaryValidator.validate(inputFileName);
		} else if (toolId == 3) {
			DataValidator dataValidator = new DataValidator(null, null, dictionaryFileName);
			text = dataValidator.validate(inputFileName);
		}
		System.out.println("\nValidation results:\n" + text);

		if (inputFileName != null) {

			Path folderName = Paths.get(FilenameUtils.getFullPath(inputFileName) + "results");
			Project.createFolder(folderName.toString());

			Path filePath = Paths.get(folderName.toString(), FilenameUtils.getBaseName(inputFileName) + ".txt");
			IOUtil.writeTextToFile(text, filePath.toString());
			System.out.println("Written into " + filePath.toString());
		}
	}

	public static void main(String[] args) {
		System.out.println("QA version " + LauncherApp.getVersion());
		boolean createdNewDirectory = false;
		try {
			createdNewDirectory = ProjectPreference.setRootDirectory(".uth");
		} catch (Exception e) {
			return;
		}

		int toolId = Integer.parseInt(args[0]);
		if (toolId != 1 && toolId != 2 && toolId != 3) {
			System.out.println(usage());
			return;
		}

		String inputFileName = args[1].toString();
		if (!checkFileName(inputFileName, "csv")) {
			return;
		}

		String dictionaryFileName = null;
		if (toolId == 3) {
			dictionaryFileName = args[2].toString();
			if (!checkFileName(dictionaryFileName, "csv")) {
				return;
			}
		}

		new QacApp(toolId, inputFileName, dictionaryFileName);
	}

	public static boolean checkFileName(String fileName, String extension) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("File does not exist: " + fileName);
			return false;
		}
		if (!file.isFile()) {
			System.out.println("This is not a file: " + fileName);
			return false;
		}

		if (extension != null) {
			if (!FilenameUtils.isExtension(fileName.toLowerCase(), extension)) {
				System.out.println("File extension must be csv: " + fileName);
				return false;
			}
		}
		return true;
	}

	public static String usage() {
		StringBuilder sb = new StringBuilder();
		if (LauncherApp.isWindows()) {
			sb.append("rtb.bat ");
		} else if (LauncherApp.isMac()) {
			sb.append("./rtb.sh ");
		} else if (LauncherApp.isUnix()) {
			sb.append("./rtb.sh ");
		} else if (LauncherApp.isSolaris()) {
			sb.append("./rtb.sh ");
		} else {
			return ("Your OS is not support!!");
		}

		sb.append("tool_id input_file [dictionary_file]");
		sb.append("\nwhere tool_id is 1, 2, or 3 only. ");
		sb.append("\ndictionary_file is optional when tool_id is 1 and 2, but required when tool_id is 3.");
		sb.append("\n");
		sb.append("\nExamples: ");
		sb.append("\nrtb.bat 1 examples/qa/meta_well_formed.csv");
		sb.append("\nrtb.bat 2 examples/qa/dictionary_well_formed.csv");
		sb.append(
				"\nrtb.bat 3 examples/qa/data_well_formed.csv resources/dataDictionary/RADx-rad_MinimumCDE_REDCapDataDictionary_2021-06-18.csv");
		sb.append("\n");

		return sb.toString();
	}

}
