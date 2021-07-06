package edu.uth.util;

import java.io.File;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileNameExtensionFilter;

public class GhcUtil {


	public static String[] parseMultipleFileNames(String pckFileName) {
		if (pckFileName == null || pckFileName.isEmpty()) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(pckFileName, ";");
		String[] fileName = new String[st.countTokens()];
		for (int i = 0; i < fileName.length; i++) {
			fileName[i] = st.nextToken().trim();
		}
		return fileName;
	}

	public static boolean isExtension(String test, String... exts) {
		for (int i = 0; i < exts.length; i++) {
			exts[i] = exts[i].replaceAll("\\.", "");
		}
		return (new FileNameExtensionFilter("extension test", exts)).accept(new File(test));
	}
	
	
	


}
