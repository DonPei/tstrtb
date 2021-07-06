package edu.uth.app.zta;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import edu.uth.app.zta.dialog.Zip;
import edu.uth.app.zta.dialog.ZipDatabase;
import edu.uth.kit.project.CsvParserSimple;

public class ZtaTest implements Runnable {
	public ZtaTest() {
	}

	public void run() {
		stringSplit();
		//copyList();
		//testLocalDate();
		//modifyCsvFile();
		//addMoreColumns();
		//getDateFormat();
		System.out.println("Successfully Done!");
	}

	public static void main(String[] args) {
		System.out.println("Executing ZtaTest()");
		SwingUtilities.invokeLater(new ZtaTest());
	}

	public void copyList() {
		List<String []> src = new ArrayList<>();
		src.add(new String [] {
				"I", "am", "here"
		});
		src.add(new String [] {
				"you", "am", "there"
		});
		
		printList(src);
		
		List<String []> copy = src.stream().collect(Collectors.toList());
		printList(copy);
		
		copy.add(new String [] {
				"He", "am", "there"
		});
		copy.remove(0);
		
		printList(copy);
		
		printList(src);
		
		
	}
	
	public void printList(List<String[]> list) {
		int k = 0;
		for(String [] r: list) {
			System.out.println("");
			System.out.print("k="+k+" "+r[0]);
			for(int j=1; j<r.length; j++) {
				System.out.print(" "+r[j]);
			}
			k++;
		}
	}
	public void testLocalDate() {
		int days = -2;
		LocalDate date = LocalDate.parse("2017-02-03");
		date = date.plusDays(days);
		//LocalDate date2 = date.plusDays(days);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		System.out.println(date.format(formatter));
		//System.out.println(date2.format(formatter));
		
//		int days = map.get(key);
//		LocalDate date = extractDate(value);
//		System.out.println("before: "+date.format(formatter)+" key="+key+" value="+value+" days="+days);
//		date.plusDays(days);
		
		
//		String [] dateString  = new String[] {
//				"2021/4/03", "2021-4-3", "12/1/2000", "1-23-1999"
//		};
//		for(int i=0; i<dateString.length; i++) {
//			 System.out.println(extractDate(dateString[i]));
//		}
	}
	
	
	LocalDate extractDate(String strDate){
		DateTimeFormatter[] dateFormatters = new DateTimeFormatter[] {
				DateTimeFormatter.ofPattern("M[/][-]d[/][-]u"),
				DateTimeFormatter.ofPattern("u[/][-]M[/][-]d")
		};

		for (DateTimeFormatter formatter : dateFormatters) {
			try {
				return LocalDate.parse(strDate, formatter);
			} catch (DateTimeParseException dtpe) {
			}
		}
		throw new IllegalArgumentException("Could not parse " + strDate);
	}
	public void stringSplit() {
		String s = "12-1-2019T12:05:03";
		int k = s.indexOf(' ');
		System.out.println("k: "+k);
	}
	public void getDateFormat() {
//		String s = "12-1-2019";
//		//System.out.println("date format: "+DateUtil.getDateFormat(s));
//
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M[/][-]d[/][-]u");
//		System.out.println(LocalDate.parse("02.05.2019", dateFormatter));
//		System.out.println(LocalDate.parse("3.5.2019", dateFormatter));
//		System.out.println(LocalDate.parse("4.05.2019", dateFormatter));
//		System.out.println(LocalDate.parse("06.5.2019", dateFormatter));
//		System.out.println(LocalDate.parse("15.12.2019", dateFormatter));
//		dateFormatter = DateTimeFormatter.ofPattern("M/d/u");
//		dateFormatter = DateTimeFormatter.ofPattern("M-d-u");
		System.out.println(LocalDate.parse("12-1-2000", dateFormatter));

		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
		//DateTimeFormatter f = builder.appendPattern("d.M.yyyy").toFormatter();
		
		builder.appendOptional(DateTimeFormatter.ofPattern(""+"[u[/][-]M[/][-]d]"))
		.appendOptional(DateTimeFormatter.ofPattern("[M[/][-]d[/][-]u]"));
		DateTimeFormatter f = builder.toFormatter();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(""
				+ "[M/d/u]"
				+ "[d/M/u]"
				+ "[u/M/d]"
				+ "[M-d-u]"
				+ "[d-M-u]"
				+ "[u-M-d]", Locale.ENGLISH);
		String [] dateString  = new String[] {
				"2021/4/3", "2021-4-3", "12/1/2000", "1-23-1999"
		};
		for(int i=0; i<dateString.length; i++) {
			 System.out.println(LocalDate.parse(dateString[i], f));
		}
	}

	public void modifyCsvFile() {
		String inputFileName = "C:\\GH_DATA\\zipcode\\testData\\testDataSet6.csv";
		String outputFileName = "C:\\GH_DATA\\zipcode\\testData\\data6.csv";
		CsvParserSimple obj = new CsvParserSimple();
		obj.setHeaderEnabled(true);
		List<String[]> rows;
		try {
			List<String[]> outRows = new ArrayList<>();
			rows = obj.readFile(new File(inputFileName), 0);
			String [] header = obj.getHeader();
			header[6] = "login_date";
			for(int i=0; i<rows.size(); i++) {
				String [] r = rows.get(i);
				if(Integer.parseInt(r[2])<18) {
					r[2] = "19";
				}
				int nr = r.length;
				String [] or = new String[nr];
				int kj = 0;
				for(int j=0; j<r.length; j++) {
					if(j==6) {
						or[kj++] = r[j+1];
					} else {
						or[kj++] = r[j];
					}
				}
				outRows.add(or);
			}
			writeData(outputFileName, header, outRows);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addMoreColumns() {
		String inputFileName = "C:\\GH_DATA\\zipcode\\testData\\data1.csv";
		String outputFileName = "C:\\GH_DATA\\zipcode\\testData\\dataMoreColumn1.csv";
		CsvParserSimple obj = new CsvParserSimple();
		obj.setHeaderEnabled(true);
		List<String[]> rows;
		try {
			List<String[]> outRows = new ArrayList<>();
			rows = obj.readFile(new File(inputFileName), 0);
			System.out.println(rows.size());
			String [] h = obj.getHeader();
			System.out.println(rows.size()+" "+h.length+" "+h[0]);

			int nCol = 25;
			String [] header = new String[h.length+nCol];
			int ik = 0;
			for(int i=0; i<h.length; i++) {
				header[ik++] = h[i];
			}
			for(int i=0; i<nCol ; i++) {
				header[ik++] = "Col"+(h.length+i);
			}

			String [][] col = new String[nCol][];
			for(int i=0; i<nCol ; i++) {
				col[i] = genRandomDigitString(rows.size(), 1000000000);
			}
			for(int i=0; i<rows.size(); i++) {
				String [] r = rows.get(i);
				int nr = r.length;
				String [] or = new String[nr+nCol];
				int kj = 0;
				for(int j=0; j<r.length; j++) {
					or[kj++] = r[j];
				}
				for(int j=0; j<nCol ; j++) {
					or[kj++] = col[j][i];
				}
				outRows.add(or);
			}
			writeData(outputFileName, header, outRows);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected String [] genRandomDigitString(int nRows, int limit) {
		String [] s = new String[nRows];
		Random rn = new Random();
		int k = 0;
		int a = 0;
		//int limit = 1000000000;
		int min = limit/10;
		do {
			a = rn.nextInt(limit);
			if(a>min) {
				s[k++]=a+"";
			}
		} while (k<nRows);
		return s;
	}

	public void writeData(String outputFileName, String [] header, List<String[]> rows) {
		if(rows==null || rows.size()==0) {
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outputFileName));
			writeLine(header, writer);
			for(String [] r: rows) {
				writer.newLine();
				writeLine(r, writer);
			}
			System.out.println("sucessfully saved: "+outputFileName);
			writer.close();
		} catch (IOException e) {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException exc) {
				}
			}
			System.out.println("save file failed: "+outputFileName);
		}
	}

	private void writeLine(String [] s, BufferedWriter writer) {
		try {
			writer.write(s[0]);
			for(int i=1; i<s.length; i++) {
				writer.write(","+s[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testZipReplacement() {
		String filename = "C:\\GH_DATA\\zip\\population_by_zip_2010.csv";
		String path = "C:\\prowess\\geohammer\\lib\\census";
		ZipDatabase zipDatabase = new ZipDatabase(filename);
		int iMethod = 1;
		if(iMethod==0) {
			List<Zip> zip5 = zipDatabase.getZip5();
			List<Zip> zip51 = zipDatabase.sortAndCombine(zip5);
			zipDatabase.print(zip5, 0, 1);
			zipDatabase.print(zip51, 0, 1);
			List<Zip> d5 = zipDatabase.containsDigit(5, 0, zip5);
			//zipDatabase.print(d5, 0, 5);
			List<Zip> fd = zipDatabase.find("0602", zip51);
			zipDatabase.print(fd);
		} else if(iMethod==1) {
			String zipcode = zipDatabase.calQualifiedZipcode("440");
			if(zipcode==null) {
				System.out.println("null");
			} else {
				System.out.println(zipcode+"");
			}
		}
	}

	
	private boolean isDigit(String input) {
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
	private int toInt(String s) {
		if (isDigit(s)) {
			return Integer.parseInt(s);
		} else {
			return 0;
		}
	}


	public void genDateShiftingTestData() {
		String subjectId;
		LocalDate date1;
		LocalDate date2;
		LocalDate date3;



	}

}
