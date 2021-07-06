package edu.uth.app.studyid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uth.app.common.table.CsvModel;
import edu.uth.kit.project.CsvReaderWriter;

public class StudyIdDatabase {
	private StudyIdFrame frame = null;
	private String fileName = null;
	private CsvReaderWriter csvReaderWriter = null;
	private List<String[]> rows = null;
	
	public StudyIdDatabase(StudyIdFrame frame, String fileName) {
		this.frame = frame;
		this.fileName = fileName;
		File file = new File(fileName);
		if(file.isFile()) {
			csvReaderWriter = new CsvReaderWriter();
			csvReaderWriter.read(fileName, false);
			rows = csvReaderWriter.getRows();			
		} 
	}
	
	public void write() {
		if(rows==null) {
			return;
		}
		if(csvReaderWriter==null) {
			csvReaderWriter = new CsvReaderWriter();
		}
		csvReaderWriter.setRows(rows);
		csvReaderWriter.write(fileName);
	}
	
	
	private String [] copy(String [] s) {
		String [] v = new String[s.length];
		for(int i=0; i<s.length; i++) {
			v[i] = s[i];
		}
		return v;
	}
	
	private List<String[]> copyRows(List<String[]> rows) {
		List<String[]> v = new ArrayList<>();
		for(String [] s: rows) {
			v.add(copy(s));
		}		
		return v;
	}
	
	public String [] check(String [] s) {
		int nCol = s.length;
		String [] record = new String[nCol];
		for(int i=0; i<nCol; i++) {
			if(i==(nCol-1)) {
				record[i] = getIdAndAppendIfNotExisting(s);
			} else {
				record[i] = s[i];
			}
		}
		return record;
	}
	
	private String getIdAndAppendIfNotExisting(String [] s) {
		if(rows==null) {
			rows = new ArrayList<String[]> ();
			rows.add(copy(s));
			return s[s.length-1];
		}
		
		for(String [] r: rows) {
			if(equal(s, r)) {
				return r[r.length-1];
			}
		}
		rows.add(copy(s));
		return s[s.length-1];
	}
	
	private boolean equal(String [] left, String [] right) {
		int n = left.length-1;
		for(int i=0; i<n; i++) {
			if(!left[i].equalsIgnoreCase(right[i])) {
				return false;
			}
		}
		return true;
	}
	
	

}
