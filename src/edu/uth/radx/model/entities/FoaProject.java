package edu.uth.radx.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoaProject {
	 private Long id; 
	 
	 @JsonProperty("grantId")
	 private String grantId;
	 @JsonProperty("title")
	 private String title;
	 @JsonProperty("pi")
	 private String pi;
	 @JsonProperty("institute")
	 private String institute;
	 
	 public String toString() {
		 return ("grantId="+grantId+"\nTitle="+title+"\npi="+pi+" \ninstitute="+institute);
	 }
	
}

