package edu.uth.kit.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
// @NoArgsConstructor
// @AllArgsConstructor
@ToString
public class ProjectZta extends Project {

	protected int fileType = 0;
	protected String folderName = null;
	protected String fileName = null;
	protected String [] header = null;
	protected List<String[]> rows = null;

	public ProjectZta(String inputFileName) {
		if (inputFileName != null) {
			setProjectName(inputFileName);
			read(inputFileName);
		}
	}

	public ProjectZta(String projectFileName, int iId, int iUnit, int fileType, String folderName) {
		super(projectFileName, iId, iUnit);
		this.fileType = fileType;
		this.folderName = folderName;
	}
	
	public String getResultsCwd(String appendString) {
		Path filePath = Paths.get(folderName.trim(), getBaseName() + appendString);
		return createFolder(filePath.toString());
	}

	public void readCsvFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			this.fileName = fileName;
			CsvParserSimple obj = new CsvParserSimple();
			obj.setHeaderEnabled(true);
			try {
				rows = obj.readFile(file, 0);
				header = obj.getHeader();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}

	public String toLineString(String [] s) {
		 StringBuilder sb = new StringBuilder(s[0]);
		 for(int i=1; i<s.length; i++) {
			 sb.append(","+s[i]);
		 }
		 return sb.toString();
	}
	
	public String toDataSummary() {
		String [] header = getHeader();
		List<String[]> rows = getRows();
		StringBuilder sb = new StringBuilder("Number of column: "+header.length+"\n");
		sb.append("Number of row: "+rows.size()+"\n");
		
		int n = 5;
		sb.append("The first "+n+" rows: "+"\n");
		sb.append(toLineString(header)+"\n");
		for(int i=0; i<n; i++) {
			sb.append(toLineString(rows.get(i))+"\n");
		}
		
		sb.append("The last "+n+" rows: "+"\n");
		sb.append(toLineString(header)+"\n");
		for(int i=rows.size()-n; i<rows.size(); i++) {
			sb.append(toLineString(rows.get(i))+"\n");
		}
		
		 return sb.toString();
	}

	protected void readProject(BufferedReader reader) throws IOException {
		String line = null;
		int k = 0;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.isEmpty() || line.startsWith("#")) {
				continue;
			}

			if (k == 0) {
				fileType = Integer.parseInt(parseLine(line));
			} else if (k == 1) {
				folderName = parseFileName(line);
			}
			k++;
		}
	}

	protected void writeProject(BufferedWriter writer) throws IOException {
		writeLine(writer, "#File format: ");
		writeLine(writer, String.format("%d", fileType));
		writeLine(writer, "#File or directory Name: ");
		writeFileName(writer, folderName);
	}
}
