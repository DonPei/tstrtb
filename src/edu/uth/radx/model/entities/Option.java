package edu.uth.radx.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Option {

	@JsonProperty("type")
	private String type;
	@JsonProperty("name")
	private String name;
	@JsonProperty("exception")
	private String exception;
	
	
	public ObjectNode createObjectNode(ObjectMapper objectMapper) {
		ObjectNode oNode = objectMapper.createObjectNode();
		oNode.put("type", type);
		oNode.put("name", name);
		oNode.put("exception", exception);
		
		return oNode;
	}
	
}
