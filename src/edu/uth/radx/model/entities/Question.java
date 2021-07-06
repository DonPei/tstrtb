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
public class Question {

	@JsonProperty("name")
	private String name;
	@JsonProperty("option")
	private ArrayNode optionsNode;
	
	private Option [] options;
	protected String[] row;
	
//	private String variable;
//	private String fieldType;
//	private String fieldLabel;
//	private String choice;
//	private String fieldNote;
//	private String sectionHeader;
	
	private int variableColIndex = 0;
	private int fieldTypeColIndex = 3;
	private int fieldLabelColIndex = 4;
	private int choiceColIndex = 5;
	private int fieldNoteColIndex = 6;
	private int sectionHeaderColIndex = 2;
	
	public void read(ObjectMapper objectMapper) {
		//ObjectMapper objectMapper = new ObjectMapper();
		try {
			options = objectMapper.readValue(optionsNode.toString(), Option[].class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getVariable() {
		return row[variableColIndex];
	}
	public String getFieldType() {
		return row[fieldTypeColIndex];
	}
	public String getFieldLabel() {
		return row[fieldLabelColIndex];
	}
	public String getChoice() {
		return row[choiceColIndex];
	}
	public String getSectionHeader() {
		return row[sectionHeaderColIndex];
	}
	
	public String getFieldNote() {
		return row[fieldNoteColIndex];
	}
	
	public void setFieldNote(String fieldNote) {
		row[fieldNoteColIndex] = fieldNote;
	}
	
	public boolean getWaived() {
		String fieldNote = getFieldNote();
		if(fieldNote==null || fieldNote.isEmpty()) {
			return false;
		}
		if(fieldNote.contains("WAIVED")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setWaived(boolean waivered) {
		String fieldNote = getFieldNote();
		if(waivered) {
			if(fieldNote==null || fieldNote.isEmpty()) {
				fieldNote = "WAIVED";
			}
			if(fieldNote.contains("WAIVED")) {
				
			} else {
				fieldNote += " WAIVED";
			}
		} else {
			if(fieldNote==null || fieldNote.isEmpty()) {
			}
			if(fieldNote.contains("WAIVED")) {
				fieldNote = fieldNote.replace("WAIVED","");
			} else {
				
			}
		}
		setFieldNote(fieldNote);
	}
	
	public ObjectNode createObjectNode(ObjectMapper objectMapper) {
		ObjectNode questionNode = objectMapper.createObjectNode().objectNode();
		questionNode.put("name", name);
        
        ArrayNode optionNode = objectMapper.createArrayNode();
        for(Option option: options) {
        	ObjectNode oNode = option.createObjectNode(objectMapper);
        	optionNode.add(oNode);
        }
        
        questionNode.set("option", optionNode);
        
        return questionNode;
	}
	
//	public ObjectNode createObjectNode(ObjectMapper objectMapper) {
//		ObjectNode questionNode = objectMapper.createObjectNode().objectNode();
//		questionNode.put("Variable", variable);
//		questionNode.put("Section Header", sectionHeader);
//		questionNode.put("Field Type", fieldType);
//		questionNode.put("Field Label", fieldLabel);
//		questionNode.put("Choice", choice);
//		questionNode.put("FieldNote", fieldNote);
//        
//        return questionNode;
//	}
	
	
}
