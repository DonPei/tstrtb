package edu.uth.app.qac;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.uth.kit.project.CsvReaderWriter;
import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.Question;

public class JSUtils {
	
	
private CsvReaderWriter csvReaderWriter;
	
	public JSUtils(String inputFileName) {
		read(inputFileName);
	}
	
	public List<String> toJSArray() {
		String[] header = csvReaderWriter.getHeader();
		int nCol = header.length;
		List<String[]> rows = csvReaderWriter.getRows();
		
		List<String> jsArray = new ArrayList<>();
		for(int i=0; i<header.length; i++) {			
			StringBuilder sb = new StringBuilder("let "+header[i]+" = new Array(\n");
			for(int j=0; j<rows.size(); j++) {
				sb.append("\""+rows.get(j)[i]+"\"");
				if(j==rows.size()-1) {
					sb.append("\n);");
				} else {
				sb.append(",\n");
				}
			}
			jsArray.add(sb.toString());
		}
		return jsArray;
	}
	
	private void read(String fileName) {
		csvReaderWriter = new CsvReaderWriter();
		csvReaderWriter.read(fileName, true);
		
	}
	
}
