package edu.uth.app.zta.dialog;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Zip {
	private String zipcode;
	private int population;
	public Zip(String zipcode, int population) {
		this.zipcode = zipcode;
		this.population = population;
	}

	public void incrementPopulation(int incremental) {
		population += incremental;
	}
	
	public Zip copy() {
		Zip z = new Zip(zipcode, population);
		return z;		
	}
	public void removeLastDigit() {
		zipcode = zipcode.substring(0, zipcode.length()-1);
	}
	
	public boolean isDigit() {
		return isDigit(zipcode);
	}
	public boolean isDigit(String input) {
		// null or length < 0, return false.
		if (input == null || input.length() < 0)
			return false;

		// empty, return false
		input = input.trim();
		if ("".equals(input))
			return false;

		if (input.startsWith("-")) {
			// negative number in string, cut the first char
			return input.substring(1).matches("[0-9]*");
		} else {
			// positive number, good, just check
			return input.matches("[0-9]*");
		}
	}
	public int toInt(String s) {
		if (isDigit(s)) {
			return Integer.parseInt(s);
		} else {
			return 0;
		}
	}
	
	public String toString() {
		return zipcode+", "+population;
	}
}
