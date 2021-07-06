package edu.uth.radx.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class FoaProjectData {
	
	@JsonProperty("AUTOMATIC")
	private ArrayNode automatic;
	
	@JsonProperty("CHEMOSENSORY")
	private ArrayNode chemosensory;
	
	@JsonProperty("EXOSOME")
	private ArrayNode exosome;
	
	@JsonProperty("MULTIMODAL")
	private ArrayNode multimodal;
	
	@JsonProperty("NOVEL")
	private ArrayNode novel;
	
	@JsonProperty("PREVAILKIDS")
	private ArrayNode prevailkids;
	
	@JsonProperty("SCENT")
	private ArrayNode scent;
	
	@JsonProperty("WASTEWATER")
	private ArrayNode wastewater;
	
	@JsonProperty("NIEHS")
	private ArrayNode niehs;
	
	public ArrayNode getArrayNode(int index) {
		if(index==0) {
			return automatic;
		} else if(index==1) {
			return chemosensory;
		} else if(index==2) {
			return exosome;
		} else if(index==3) {
			return multimodal;
		} else if(index==4) {
			return novel;
		} else if(index==5) {
			return prevailkids;
		} else if(index==6) {
			return scent;
		} else if(index==7) {
			return wastewater;
		} else if(index==8) {
			return niehs;
		} else {
			return null;
		}		
	}
	
	
//	@JsonProperty("AUTOMATIC")
//	private ArrayList < Object > automatic;
//	
//	@JsonProperty("CHEMOSENSORY")
//	private ArrayList < Object > chemosensory;
//	
//	@JsonProperty("EXOSOME")
//	private ArrayList < Object > exosome;
//	
//	@JsonProperty("MULTIMODAL")
//	private ArrayList < Object > multimodal;
//	
//	@JsonProperty("NOVEL")
//	private ArrayList < Object > novel;
//	
//	@JsonProperty("PREVAILKIDS")
//	private ArrayList < Object > prevailkids;
//	
//	@JsonProperty("SCENT")
//	private ArrayList < Object > scent;
//	
//	@JsonProperty("WASTEWATER")
//	private ArrayList < Object > wastewater;
//	
//	@JsonProperty("NIEHS")
//	private ArrayList < Object > niehs;
	
//	@JsonProperty("AUTOMATIC")
//	private Map<String, Object> automatic;
//	
//	@JsonProperty("CHEMOSENSORY")
//	private Map<String, Object> chemosensory;
//	
//	@JsonProperty("EXOSOME")
//	private Map<String, Object> exosome;
//	
//	@JsonProperty("MULTIMODAL")
//	private Map<String, Object> multimodal;
//	
//	@JsonProperty("NOVEL")
//	private Map<String, Object> novel;
//	
//	@JsonProperty("PREVAILKIDS")
//	private Map<String, Object> prevailkids;
//	
//	@JsonProperty("SCENT")
//	private Map<String, Object> scent;
//	
//	@JsonProperty("WASTEWATER")
//	private Map<String, Object> wastewater;
//	
//	@JsonProperty("NIEHS")
//	private Map<String, Object> niehs;	
	
}
