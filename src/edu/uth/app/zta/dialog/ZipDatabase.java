package edu.uth.app.zta.dialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.SwingWorker;

import org.apache.commons.io.FilenameUtils;

import edu.uth.kit.project.CsvParserSimple;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZipDatabase {
	private int 	zipcodeColIndex		= 0; //4;
	private int 	populationColIndex	= 1; //0;
	private int 	threshold 			= 20000;

	private File file = null;
	private List<Zip> zip5 = new ArrayList<>();
	private List<Zip> zip4 = new ArrayList<>();
	private List<Zip> zip3 = new ArrayList<>();
	private List<Zip> zip2 = new ArrayList<>();
	
	public ZipDatabase(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) {
			this.file = file;
		} else {
			this.file = fetchFirstCsvFile(file);
		}
		zip5 = sortAndCombine(read());
		zip4 = removeLastDigitThenSortAndCombine(zip5);
		zip3 = removeLastDigitThenSortAndCombine(zip4);
		zip2 = removeLastDigitThenSortAndCombine(zip3);
	}
	
	public List smallPopulationZipCode(int threshold, List<Zip> zipInput) {
		List<Zip> zip = new ArrayList<>();
		for(Zip z: zipInput) {
			if((z.getPopulation()<=threshold)) {
				zip.add(z.copy());
			}
		}
		return zip;
	}
	
	public String [] sampleZipCode(int nRows, int nDigit) {
		List<Zip> zip = null;
		switch(nDigit) {
			case 5:
				zip = zip5;
				break;
			case 4:
				zip = zip4;
				break;
			case 3:
				zip = zip3;
				break;
			case 2:
				zip = zip2;
				break;
			default:
				zip = zip5;
				break;
		}
		
		String [] s = new String[nRows];
		Random rn = new Random();
		int ia = 0;
		for(int i=0; i<nRows; i++) {
			ia = rn.nextInt(zip.size());
			s[i] = zip.get(ia).getZipcode();
		}
		return s;
	}

	private File fetchFirstCsvFile(File file) {
		File [] files = file.listFiles();
		for(File f: files) {
			String ext = FilenameUtils.getExtension(f.getName()).toLowerCase();
			if("csv".equals(ext)) {
				return f;
			}
		}
		return null;
	}
	public List<Zip> read() {
		CsvParserSimple obj = new CsvParserSimple();
		List<String[]> rows;
		try {
			rows = obj.readFile(file, 1);
			List<Zip> zip = new ArrayList<>();
			for(String [] r: rows) {
				zip.add(new Zip(r[zipcodeColIndex], Integer.parseInt(r[populationColIndex].trim())));
			}
			return zip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void write(String outputFileName) {
		write(outputFileName, zip5);
	}
	private void write(String outputFileName, List<Zip> rows) {
		if(rows==null || rows.size()==0) {
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outputFileName));
			writer.write("zipcode, population");
			for(Zip r: rows) {
				writer.newLine();
				writer.write(r.getZipcode()+", "+r.getPopulation());
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
	private boolean isInValid(String s) {
		return s==null ||s.isEmpty();
	}
	private boolean containsTotalPopulation(String [] record) {
		if(isInValid(record[1])&&isInValid(record[2])&&isInValid(record[3])) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<Zip> containsDigit(int numOfDigits, int operationType, List<Zip> rows) {
		List<Zip> list = new ArrayList<>();
		for(Zip z: rows) {
			if(operationType==0) {
				if(z.getZipcode().length()==numOfDigits) {
					list.add(z);
				}
			} else if(operationType==1) {
				if(z.getZipcode().length()>numOfDigits) {
					list.add(z);
				}
			}else if(operationType==-1) {
				if(z.getZipcode().length()<numOfDigits) {
					list.add(z);
				}
			}
		}
		return list;
	}
	public int getPopulation() {
		List<Zip> rows = zip5;
		int sum = 0;
		for(Zip z: rows) {
			sum += z.getPopulation();
		}
		return sum;
	}
	
	public int getPopulation(String zipcode) {
		if(zipcode.length()>5) {
			System.out.println("zipcode "+zipcode+" does not exist");
			return 0;
		}
		List<Zip> list = find(zipcode);
		if(list.size()!=1) {
			return 0;
		}
		return list.get(0).getPopulation();
	}
	public String calQualifiedZipcode(String zipcode) {
		if(zipcode.length()>5) {
			System.out.println("zipcode "+zipcode+" does not exist");
			return null;
		}
		List<Zip> list = null;
		int population = 0;
		do {
			list = find(zipcode);
			if(list.size()!=1) {
				return null;
			} 

			population = list.get(0).getPopulation();
			if(population<threshold) {
				zipcode = zipcode.substring(0, zipcode.length()-1);
			} else {
				return zipcode;
			}
		} while (zipcode.length()>1);

		return null;
	}
	public List<Zip> find(String zipcode) {
		List<Zip> list = null;
		if(zipcode.length()==5) {
			list = find(zipcode, zip5);
		} else if(zipcode.length()==4) {
			list = find(zipcode, zip4);
		} else if(zipcode.length()==3) {
			list = find(zipcode, zip3);
		}
		return list;
	}
	public List<Zip> find(String zipcode, List<Zip> rows) {
		List<Zip> list = new ArrayList<>();
		for(Zip z: rows) {
			if(zipcode.equals(z.getZipcode())) {
				list.add(z);
			}
		}
		return list;
	}
	public List<Zip> startsWith(String zipcode, List<Zip> rows) {
		List<Zip> list = new ArrayList<>();
		for(Zip z: rows) {
			if(z.getZipcode().startsWith(zipcode)) {
				list.add(z);
			}
		}
		return list;
	}
	public void print(List<Zip> rows) {
		print(rows, 0, rows.size());
	}
	
	public void print(List<Zip> rows, int fromInclusive, int toExclusive) {
		System.out.println("size="+rows.size()+" from="+fromInclusive +" to="+toExclusive);
		for(int i=fromInclusive; i<toExclusive; i++) {
			System.out.println(i+", "+rows.get(i).getZipcode()+", "+rows.get(i).getPopulation());
		}
	}
	
	public List<Zip> sort(List<Zip> rows) {
		return rows.stream()
				.sorted(Comparator.comparing(Zip::getZipcode))
				.collect(Collectors.toList());
	}

	public List<Zip> combineDuplicates(List<Zip> rows) {
		List<Zip> m = null;
		Zip prev = null;
		int k = 0;
		for (Zip r : rows) {
			if(m==null) {
				m = new ArrayList<>();
				m.add(r.copy());
				prev = m.get(k);
				k++;
				continue;
			}
			if(prev.getZipcode().equals(r.getZipcode())) {
				prev.incrementPopulation(r.getPopulation());
			} else {
				m.add(r.copy());
				prev = m.get(k);
				k++;
			}
		}
		return m;
	}
	
	public List<Zip> removeLastDigit(List<Zip> rows) {
		List<Zip> m = new ArrayList<>();
		Zip a = null;
		int k = 0;
		for (Zip r : rows) {
			a = r.copy();
			a.removeLastDigit();
			m.add(a);
		}
		return m;
	}
	
	
	public List<Zip> sortAndCombine(List<Zip> rows) {
		List<Zip> m1 = sort(rows);
		List<Zip> m2 = combineDuplicates(m1);
		return m2;
	}
	
	public List<Zip> removeLastDigitThenSortAndCombine(List<Zip> rows) {
		return sortAndCombine(removeLastDigit(rows));
	}
	
	
	

}
