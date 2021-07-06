package edu.uth.radx.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum DictionaryRules {
	A(true, "Variable/Field Name" , new String [] {
		"Variable/Field names specify the variable name that will be used in reporting, data export, and data analysis",
		
		"Variable names may contain letters, numbers, and underscores, but no spaces or special characters. "
		+ "For multiple words, we recommend to use the underscore to link  them: e.g., 'firstname' to 'first_name'",
		
		"Variable names cannot start with a number",
		
		"Variable names must be unique, and cannot be repeated within a database, even in different forms",
		
		"Variable names should be brief; they do not need to be descriptive as the description will " + 
		"be included in the Variable Label. A common example is the use of 'dob' as a variable " + 
		"name, with the corresponding variable label of 'Date of birth'"
	}),
	B(true, "Variable Classification" , new String [] {
			"Variable Classifications are groupings of variables based on the nature of the variables. " + 
			"Assigning a proper class to a variable will not only assist data entry, but also assist " + 
			"CDEs mapping as well, as we may recommend different CDEs collections or other " + 
			"standards/ontologies based on the classification of the variable",
			
			"Examples of variable classification are sociodemographics, conditions/symptoms, lab " + 
			"tests, medications, or project-specific categories such as geolocations in wastewater " + 
			"surveillance. The assignment of these classes to study variables is subjective, which is " + 
			"based on your understanding of the variable. If you are not sure about specific variables, " + 
			"you can use the “other” category and DCC will sort them out later"
		}),
	C(true, "Field Label/Variable Description" , new String [] {
			"A Field Label (or variable label) is a word or phrase that is more descriptive than the " + 
			"variable/field name. It provides more information to the reader"
		}),
	D(false, "Variable Question" , new String [] {
			"If there is a survey question related to this study variable, please fill this column to " + 
			"include the full question"
		}),
	E(true, "Field Type" , new String [] {
			"Specifying the field type determines what types of responses are allowed. Field types " + 
			"include: 1) radio (one selection) 2) checkbox (multiple selections available), 3) yesno " + 
			"(boolean), 4) text. For radio and checkbox, please specify the permissible options in " + 
			"Choices, Calculations OR Slider Labels column",
			
			"If the Field Type is text, please also fill in the Validation column whenever possible, see " + 
			"column G"
		}),
	F(false, "Choices, Calculations OR Slider Labels" , new String [] {
			"All categorical field types (yes/no, radio buttons, checkboxes) must specify response " + 
			"options associating numerical values with labels. Please separate each response with | . " + 
			"For example, 1, Female | 2, Male | 3, Intersex | 4, Prefer not to answer",
			
			"The slider field allows you to label three anchor points: left, middle, and right. An " + 
			"example might be 'Strongly Disagree', 'Neutral', and 'Strongly Agree'"
		}),
	G(false, "Validation" , new String [] {
			"Format validation types for text fields are: date, time, integer, float, zipcode, phone, email " + 
			"and free_text. You may add additional rules in the following format: type: rule. For " + 
			"example: integer: min 0, max 120",
			"For slider fields, specify whether to display or hide the value (1S100) selected on the " + 
			"slider"
		}),
	H(false, "Field Notes" , new String [] {
			"Field notes are used to provide information, such as unit or any other information (e.g., " + 
			"calculation rule, branching_logic) that is important. Examples are specifying the " + 
			"expected format of a validated field (e.g. phone number), or units (e.g. kg vs. lb)"
		}),
	I(false, "Variable Question" , new String [] {
			"We recommend awardee to search candidate CDEs from existing CDEs collections, " + 
			"including https://cde.nlm.nih.gov/home or https://www.phenxtoolkit.org/ , and add " + 
			"candidate CDEs url to this column, separated by ; . This information is optional but will " + 
			"assist CDEs mapping and improve mapping algorithms later"
		});

    private String name;
    private String [] description;
    
    DictionaryRules(boolean required, String name, String [] description) {
        this.name = name;
        this.description = description;
    }

}