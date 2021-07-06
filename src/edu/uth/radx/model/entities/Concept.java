package edu.uth.radx.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Concept {
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("question")
	private ArrayNode questionsNode;	
	private Question [] questions; 	
	
	public void read(ObjectMapper objectMapper) {
		try {
			questions = objectMapper.readValue(questionsNode.toString(), Question[].class);
			for(Question question: questions) {
				question.read(objectMapper);
			}
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(ObjectMapper objectMapper) {
		try {
			questions = objectMapper.readValue(questionsNode.toString(), Question[].class);
			for(Question question: questions) {
				//question.read(objectMapper);
			}
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ObjectNode createObjectNode(ObjectMapper objectMapper) {
		ObjectNode conceptNode = objectMapper.createObjectNode().objectNode();
		conceptNode.put("id", id);
		conceptNode.put("name", name);
		
        ArrayNode questionNode = objectMapper.createArrayNode();
        for(Question question: questions) {
        	ObjectNode oNode = question.createObjectNode(objectMapper);
        	questionNode.add(oNode);
        }
        
        conceptNode.set("question", questionNode);
        
        return conceptNode;
	}
	
	public String toString() {
		return "id="+id+" name="+name;
	}
}
