package edu.uth.radx.model;

import java.util.ArrayList;
import java.util.List;

import edu.uth.kit.project.CsvReaderWriter;
import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.MetaQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Meta{
	
	private CsvReaderWriter csvReaderWriter;
	private String userMetaFileName;
	private List<MetaQuestion> questions;
	
	public Meta(String inputFileName) {
		userMetaFileName = inputFileName;
		read(inputFileName);
	}
	
	private void read(String fileName) {
		csvReaderWriter = new CsvReaderWriter();
		csvReaderWriter.read(fileName, true);
		List<String[]> rows = csvReaderWriter.getRows();
		questions = new ArrayList<>();
		for(int i=0; i<rows.size(); i++) {
			String [] r = rows.get(i);
			questions.add(genQuestion(r));			
		}
		//csvReaderWriter.printClass();
	}
	
	private MetaQuestion genQuestion(String [] row) {
		MetaQuestion question = new MetaQuestion();
		question.setRow(row);
		return question;
	}
	
	public void writeCsv() {
		writeCsv(userMetaFileName);
		
	}
	public void writeCsv(String outputFileName) {
		csvReaderWriter.write(outputFileName);
	}
	

}
