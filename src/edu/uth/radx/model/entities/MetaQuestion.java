package edu.uth.radx.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetaQuestion {

	private int fieldLabelColIndex = 0;
	private int fieldTypeColIndex = 1;
	private int choiceColIndex = 2;
	private int requiredColIndex = 3;
	
	protected String[] row;
	

	public String getFieldLabel() {
		return row[fieldLabelColIndex];
	}
	public String getFieldType() {
		return row[fieldTypeColIndex];
	}
	public String getChoice() {
		return row[choiceColIndex];
	}
	public String getRequired() {
		return row[requiredColIndex];
	}
	
	public void setFieldType(String fieldType) {
		row[fieldTypeColIndex] = fieldType;
	}
	
	public void setFieldLabel(String fieldLabel) {
		row[fieldLabelColIndex] = fieldLabel;
	}
	
	public void setChoice(String choice) {
		row[choiceColIndex] = choice;
	}
	public void setRequired(String required) {
		row[requiredColIndex] = required;
	}	
	
}
