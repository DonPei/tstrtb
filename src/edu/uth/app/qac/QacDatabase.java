package edu.uth.app.qac;

import java.io.File;

import javax.swing.JTextArea;

import org.apache.commons.io.FilenameUtils;

import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.common.table.CsvTable;
import edu.uth.kit.project.ProjectQac;
import edu.uth.radx.model.Dictionary;
import edu.uth.radx.model.Foa;
import edu.uth.radx.model.FoaProjects;
import edu.uth.radx.model.Meta;
import edu.uth.radx.model.MinimumCde;
import edu.uth.radx.model.entities.FoaProject;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class QacDatabase {
	private QacFrame 		frame 	= null;
	private FoaProjects foaProjects;
	private MinimumCde minimumCde;
	private Dictionary dictionary;
	private Meta meta;
	//private String userDictionaryFileName;
	
	public QacDatabase(QacFrame frame) {
		this.frame 				= frame;
		String resources = System.getProperty("java.resources.path");
		String history = System.getProperty("java.history.path");
		String baseFolder = FilenameUtils.concat(resources, "dataDictionary");
		//System.out.println(projectListFileName+" "+Foa.AUTOMATIC.toString());
		
		foaProjects = loadFoaProjects(FilenameUtils.concat(baseFolder, "projectList.json"));
		//minimumCde = loadMinimumCde(FilenameUtils.concat(baseFolder, "minimumCDE.json"));
		
//		userDictionaryFileName = FilenameUtils.concat(history, frame.getProject().getFoaType()+
//				"_"+frame.getProject().getFoaProjectType() + "_dictionary.csv");
//		File file = new File(userDictionaryFileName);
//		if(file.isFile()) {
//			dictionary = loadDictionary(userDictionaryFileName);
//		} else {
//			dictionary = loadDictionary(FilenameUtils.concat(baseFolder, "RADx-rad_MinimumCDE_REDCapDataDictionary_2021-06-18.csv"));
//		}
//		dictionary.setUserDictionaryFileName(userDictionaryFileName);
		
		dictionary = loadDictionary(FilenameUtils.concat(baseFolder, "RADx-rad_MinimumCDE_REDCapDataDictionary_2021-06-18.csv"));
		meta = loadMeta(FilenameUtils.concat(baseFolder, "meta_2021_6_24.csv"));
	}
	
	private FoaProjects loadFoaProjects(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) {
			return new FoaProjects(fileName);
		} else {
			return null;
		}
	}
	private MinimumCde loadMinimumCde(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) {
			return new MinimumCde(fileName);
		} else {
			return null;
		}
	}
	
	public Dictionary loadDictionary(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) {
			return new Dictionary(fileName);
		} else {
			return null;
		}
	}
	
	private Meta loadMeta(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) {
			return new Meta(fileName);
		} else {
			return null;
		}
	}
	
	public void loadDataDictionary(Foa foa) {
//		String basePath = FilenameUtils.concat(FilenameUtils.getFullPath(projectFileName), foa.toString());
//		String dataDictionaryFileName = FilenameUtils.concat(basePath, "dataDictionary.csv");
//		System.out.println(dataDictionaryFileName+" ");
	}

	public String [] genFoa() {
		if(foaProjects==null) {
			return null;
		}
		Foa [] foas = foaProjects.getFoas();
		String [] names = new String[foas.length];
    	int k = 0;
    	for (Foa foa : foas) { 
    		names[k] = foa.getDescription();
    		k++;
    	}
    	return names;
	}
	
	public String [] genProject(int index) {
		if(foaProjects==null) {
			return null;
		}
		FoaProject [] projects = this.foaProjects.getProjects()[index];
		String [] names = new String[projects.length];
    	for(int i=0; i<projects.length; i++) {
    		names[i] = projects[i].getTitle();
    	}
    	return names;
	}
}
