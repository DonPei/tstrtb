package edu.uth.radx.model;

import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.FoaProject;
import edu.uth.radx.model.entities.FoaProjectData;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Getter
@Setter
public class FoaProjects {
	
	private Foa [] foas;
	private FoaProject [][] projects;
	
	public FoaProjects(String fileName) {
		read(fileName);
	}
	
//	private void read1(String fileName) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try {
//        	FoaProjectData projectData = objectMapper.readValue(new File(fileName), FoaProjectData.class);
//        	projects = new FoaProject[Foa.values().length][];
//        	foas = new Foa[Foa.values().length];
//        	int k = 0;
//        	for (Foa foa : Foa.values()) { 
//        		projects[k] = objectMapper.readValue(projectData.getArrayNode(k).toString(), FoaProject[].class);
//        		foas[k] = foa;
//        		k++;
//        	}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private void read(String fileName) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
        	
        	JsonNode root = objectMapper.readTree(new File(fileName));
        	projects = new FoaProject[Foa.values().length][];
        	foas = new Foa[Foa.values().length];
        	int k = 0;
        	for (Foa foa : Foa.values()) { 
        		foas[k] = foa;
        		//System.out.println(foa.name());
        		projects[k] = objectMapper.readValue(root.path(foa.name()).toString(), FoaProject[].class);
        		k++;
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printObject(int index) {
		for(int i=0; i<foas.length; i++) {
			if(index==i) {
				System.out.println(foas[i]+" : "+foas[i].getDescription());
				System.out.println("project : "+projects[i][0].toString());
			}
		}
	}

}
