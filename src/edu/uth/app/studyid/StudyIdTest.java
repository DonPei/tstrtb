package edu.uth.app.studyid;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import edu.uth.kit.project.CsvParserSimple;
import nhash.Record;
import nhash.StudyIdGenerator;

public class StudyIdTest implements Runnable {
	public StudyIdTest() {
	}

	public void run() {
		studyId();
		System.out.println("Successfully Done!");
	}

	public static void main(String[] args) {
		System.out.println("Executing StudyIdTest()");
		SwingUtilities.invokeLater(new StudyIdTest());
	}

	public void studyId() {
		String inputFileName = "C:\\prowess\\uth\\release\\jars\\Nhash-May-4-2021\\MOCK_DATA.csv";
		String outputFileName = "C:\\prowess\\uth\\release\\jars\\Nhash-May-4-2021\\junk.csv";
		int lengthRandomNumber = 8;
		StudyIdGenerator generator = new StudyIdGenerator(inputFileName, outputFileName, lengthRandomNumber);
		LocalDateTime start = LocalDateTime.now();
		ArrayList<Record> dataList = generator.readCSV();
		if (dataList.size() > 0) {
			for (Record record1 : dataList) {
				try {
					record1.setStudyId(generator.generateStudyId(record1.getName(), record1.getMrn(), record1.getDob()));
				} catch (NullPointerException e) {
					System.out.println("Study ID is not generated because of missing required patient data or wrong data format: " + record1.getName() + ", " + record1.getMrn() + ", " + record1.getDob());
				} 
			} 
		} else {
			System.out.println("No patient data entered.");
		} 
		LocalDateTime end = LocalDateTime.now();
		Duration duration = Duration.between(start, end);
		Record record = new Record("Generation Time: " + (duration.toMillis() / 1000.0D) + " seconds", "", " ", " ", " ", " ");
		dataList.add(record);
		generator.save(dataList);
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
