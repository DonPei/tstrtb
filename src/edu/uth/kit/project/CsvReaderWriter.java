package edu.uth.kit.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
// @NoArgsConstructor
// @AllArgsConstructor
@ToString
public class CsvReaderWriter {


	private String [] header = null;
	private List<String[]> rows = null;

	public void read(String fileName, boolean headerEnabled) {
		File file = new File(fileName);
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			CsvParserSimple obj = new CsvParserSimple();
			obj.setHeaderEnabled(headerEnabled);
			try {
				rows = obj.readFile(file, 0);
				header = obj.getHeader();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}

	public void write(String fileName) {
		if(rows==null) {
			return;
		}
		
		List<String[]> list = new ArrayList<>();
		if(header!=null) {
			list.add(header);
		}
		for(String [] r: rows) {
			list.add(r);
		}
		
		CsvWriterSimple writer = new CsvWriterSimple();
        writer.writeToCsvFile(list, new File(fileName));
	}
	
	public void write1(String fileName) {
		if(rows==null) {
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			if(header!=null) {
				writer.write(toLine(header));
				writer.newLine();
			}

			for (String [] r: rows){
				writer.write(toLine(r));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException exc) {
				}
			}
		}
	}

	private String toLine(String [] s) {
		StringBuilder sb = new StringBuilder(s[0]);
		for(int i=1; i<s.length; i++) {
			sb.append(","+s[i]);
		}
		return sb.toString();
	}
	
	public void printClass() {
		System.out.println(toLine(header));
		for (String [] r: rows){
			System.out.println(toLine(r));
		}
	}

}
