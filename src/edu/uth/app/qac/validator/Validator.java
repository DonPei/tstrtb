package edu.uth.app.qac.validator;

import java.util.ArrayList;
import java.util.List;

import edu.uth.kit.project.CsvReaderWriter;

public interface Validator {
	
	public String validate(String inputFileName);
	
	
	public default boolean equal(String a, String b) {
		String a1 = a.trim();
		String b1 = b.trim();
		if(a1.equals(b1)) {
			return true;
		}
		return false;
	}
	
	//https://www.codeproject.com/Articles/147230/Simple-Fuzzy-String-Similarity-in-Java
	public default boolean fuzzyEqual(String a, String b) {
		double threshold = 0.9;
		String a1 = a.trim().toLowerCase();
		String b1 = b.trim().toLowerCase();
		if(a1.equals(b1)) {
			return true;
		}
		
		List<char[]> a2 = bigram(a1);
		List<char[]> b2 = bigram(b1);
		double score = dice(a2, b2);
		
		if(score>=threshold) {
			return true;
		}
		return false;
	}
	
	public default double dice(List<char[]> bigram1, List<char[]> bigram2) {
	    List<char[]> copy = new ArrayList<char[]>(bigram2);
	    int matches = 0;
	    for (int i = bigram1.size(); --i >= 0;) {
	        char[] bigram = bigram1.get(i);
	        for (int j = copy.size(); --j >= 0;){
	            char[] toMatch = copy.get(j);
	            if (bigram[0] == toMatch[0] && bigram[1] == toMatch[1]) {
	                copy.remove(j);
	                matches += 2;
	                break;
	            }
	        }
	    }
	    return (double) matches / (bigram1.size() + bigram2.size());
	}
	
	public default List<char[]> bigram(String input) {
	    ArrayList<char[]> bigram = new ArrayList<char[]>();
	    for (int i = 0; i < input.length() - 1; i++) {
	        char[] chars = new char[2];
	        chars[0] = input.charAt(i);
	        chars[1] = input.charAt(i+1);
	        bigram.add(chars);
	    }
	    return bigram;
	}

}
