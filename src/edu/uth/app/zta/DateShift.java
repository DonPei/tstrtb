package edu.uth.app.zta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

import edu.uth.app.studyid.StudyIdDatabase;
import edu.uth.kit.project.CsvParserSimple;

public class DateShift {
	private int 	subjectIdColIndex	= 4;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
	
	private ZtaFrame 		frame 	= null;
	Random rand;
	private Map<String, Integer> map = null;
	
	public DateShift() {
	}
	public DateShift(ZtaFrame frame) {
		this.frame = frame;
		map = readMap();
		rand = new Random();
	}
	public void setSubjectIdColIndex(int subjectIdColIndex) {
		this.subjectIdColIndex = subjectIdColIndex;
	}
	public void addToMap(List<String []> rows) {
		if(map==null) {
			map = new HashMap<>();
		}
		for(String [] r: rows) {
			String key = r[subjectIdColIndex];
			if (!map.containsKey(key)) {
				int rn = 0;
				do {
					rn = rand.nextInt(10)-5;
				} while(rn==0);
				map.put(key, rn);
			}
		}
	}
	
	public String shift(String key, String value) {
		int days = map.get(key);
		String dString = null;
		if(value.length()<=10) {
			dString = value;
			LocalDate date = extractDate(dString);
			LocalDate date1 = date.plusDays(days);
			return date1.format(formatter);
		}
		int k = findIndex(value);
		if(k==-1) {
			return value;
		}
		dString = value.substring(0, k);
		LocalDate date = extractDate(dString);
		LocalDate date1 = date.plusDays(days);
		return date1.format(formatter)+value.substring(k);
	}
	
	private int findIndex(String s) {
		int k = s.indexOf('T');
		if(k>0 && k<=10) {
			return k;
		}
		k = s.indexOf(' ');
		if(k>0 && k<=10) {
			return k;
		}
		return -1;
	}
	
	
	public String format(String strDate) {
		LocalDate date = extractDate(strDate);
		return date.format(formatter);
	}
	
	private LocalDate extractDate(String strDate){
		DateTimeFormatter[] dateFormatters = new DateTimeFormatter[] {
				DateTimeFormatter.ofPattern("u[/][-]M[/][-]d"),
				DateTimeFormatter.ofPattern("M[/][-]d[/][-]u")
		};

		for (DateTimeFormatter formatter : dateFormatters) {
			try {
				return LocalDate.parse(strDate, formatter);
			} catch (DateTimeParseException dtpe) {
			}
		}
		throw new IllegalArgumentException("Could not parse " + strDate);
	}
	
	public void writeMap() {
		String resources = System.getProperty("java.history.path");
		String fileName = FilenameUtils.concat(resources, "dateShifts.csv");
		//String outputFileName = frame.getProject().getFullName()+".map";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			for(Map.Entry m:map.entrySet()){  
				writer.write(m.getKey()+","+m.getValue());
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
	
	public Map<String, Integer> readMap() {
		String resources = System.getProperty("java.history.path");
		String fileName = FilenameUtils.concat(resources, "dateShifts.csv");
		//String fileName = frame.getProject().getFullName()+".map";
		File file = new File(fileName);
		if(file.exists()) {
			CsvParserSimple obj = new CsvParserSimple();
			List<String[]> result;
			try {
				result = obj.readFile(file, 0);
				Map<String, Integer> map = new HashMap<>();
				for (String[] r : result) {
					map.put(r[0], Integer.parseInt(r[1]));
				}
				return map;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
