package edu.uth.kit.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectQac extends Project {
	
	protected int fileType = 0;
	protected String folderName = null;
	private int foaType = 0;
	private int foaProjectType = 0;
	private String dataDictionaryFileName = null;
	private String metaFileName = null;
	private String loadedFileName = null;
	

	public ProjectQac(String inputFileName) {
		if (inputFileName != null) {
			setProjectName(inputFileName);
			read(inputFileName);
		}
	}
	public ProjectQac(String projectFileName, int iId, int iUnit, int fileType, String folderName,
			int foaType, int foaProjectType, String dataDictionaryFileName) {
		super(projectFileName, iId, iUnit);
		this.fileType = fileType;
		this.folderName = folderName;
		this.foaType = foaType;
		this.foaProjectType = foaProjectType;
		this.dataDictionaryFileName = dataDictionaryFileName;
	}
	
	public String getResultsCwd(String appendString) {
		Path filePath = Paths.get(folderName.trim(), getBaseName() + appendString);
		return createFolder(filePath.toString());
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
			} else if (k == 2) {
				foaType = Integer.parseInt(parseLine(line));
			} else if (k == 3) {
				foaProjectType = Integer.parseInt(parseLine(line));
			} else if (k == 4) {
				dataDictionaryFileName = parseFileName(line);
			}
			k++;
		}
	}

	protected void writeProject(BufferedWriter writer) throws IOException {
		writeLine(writer, "#File format: ");
		writeLine(writer, String.format("%d", fileType));
		writeLine(writer, "#File or directory Name: ");
		writeFileName(writer, folderName);
		writeLine(writer, "#Foa type: ");
		writeLine(writer, String.format("%d", foaType));
		writeLine(writer, "#Foa project type: ");
		writeLine(writer, String.format("%d", foaProjectType));
		writeLine(writer, "#Data Dictionary File Name: ");
		writeFileName(writer, dataDictionaryFileName);
	}
	
	public void printClass(int id) {
		System.out.println("#id: "+" "+getIId()+" "+id);
		System.out.println("#iUnit: "+" "+getIUnit());
		System.out.println("#File format: "+" "+String.format("%d", fileType));
		System.out.println("#File or directory Name: "+" "+folderName);
		System.out.println("#Foa type: "+" "+String.format("%d", foaType));
		System.out.println("#Foa project type: "+" "+String.format("%d", foaProjectType));
		System.out.println("#Data Dictionary File Name: "+" "+dataDictionaryFileName);
	}
}
