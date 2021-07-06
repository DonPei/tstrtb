package edu.uth.radx.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

import edu.uth.app.zta.dialog.Zip;
import edu.uth.kit.project.CsvReaderWriter;
import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.FoaProject;
import edu.uth.radx.model.entities.FoaProjectData;
import edu.uth.radx.model.entities.Option;
import edu.uth.radx.model.entities.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MinimumCde {
	private Concept [] concepts;
	
	public MinimumCde(String fileName) {
		read(fileName);
	}
	
	public String [] getConceptNames() {
		String [] names = new String[concepts.length];
		for(int i=0; i<concepts.length; i++) {
			names[i] = concepts[i].getName();
		}
		return names;
	}
	
	public String [] getQuestionNames(int conceptIndex) {
		Question [] questions = concepts[conceptIndex].getQuestions();
		String [] names = new String[questions.length];
		for(int i=0; i<questions.length; i++) {
			names[i] = questions[i].getName();
		}
		return names;
	}
	
	
	private void read(String fileName) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
        	
        	JsonNode root = objectMapper.readTree(new File(fileName));
        	
        	JsonNode conceptNode = root.path("concept");
        	
        	concepts = objectMapper.readValue(conceptNode.toString(), Concept[].class);
        	for(int i=0; i<concepts.length; i++) {
        		concepts[i].read(objectMapper);
        	}
        	//String resultOriginal = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
           //System.out.println("Before Update " + resultOriginal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String outputFileName) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		ObjectNode rootNode = objectMapper.createObjectNode().objectNode();
        
        ArrayNode conceptNode = objectMapper.createArrayNode();
        for(Concept concept: concepts) {
        	ObjectNode oNode = concept.createObjectNode(objectMapper);
        	conceptNode.add(oNode);
        }
        
        rootNode.set("concept", conceptNode);
		
		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(outputFileName), rootNode);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void read1(String jsonFileName){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(jsonFileName)));
			String jsonText = read(br);
			//System.out.println(jsonText);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			JSONPObject json = new JSONPObject(jsonText);
//			 JSONArray arr = json.getJSONArray("Compemployes");
			
			br.close();
		} catch (IOException e) {
			if (br != null) {
				try {
					br.close();
				} catch (IOException exc) {
				}
			}
		}
	}
	
	private String read(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
}
