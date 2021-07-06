package edu.uth.app.qac;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import edu.uth.app.zta.dialog.Zip;
import edu.uth.app.zta.dialog.ZipDatabase;
import edu.uth.kit.project.CsvParserSimple;
import edu.uth.radx.model.Dictionary;
import edu.uth.radx.model.FoaProjects;
import edu.uth.radx.model.MinimumCde;
import edu.uth.util.IOUtil;

public class QacTest implements Runnable {
	public QacTest() {
	}

	public void run() {
		testDictionary();
		// testMinimumCde();
		// testFoaProject();
		// testPOI();
		// testReplacement();
		// testJSUtils();
		System.out.println("Successfully Done!");
	}

	public static void main(String[] args) {
		System.out.println("Executing StudyIdTest()");
		SwingUtilities.invokeLater(new QacTest());
	}

	public void testMinimumCde() {
		String inputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\minimumCDE.json";
		String outputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\minimumCDETest_out.json";
		MinimumCde minimumCde = new MinimumCde(inputFileName);
		minimumCde.write(outputFileName);
	}

	public void testFoaProject() {
		String inputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\projectList.json";
		String outputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\minimumCDETest_out.json";
		FoaProjects foaproject = new FoaProjects(inputFileName);
	}

	public void testDictionary() {
		String inputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\RADx-rad_MinimumCDE_REDCapDataDictionary_2021-06-18.csv";
		String outputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\examples\\qa\\data_well_formed.csv";
		Dictionary dictionary = new Dictionary(inputFileName);
		// System.out.println(dictionary.toString());
		String text = dictionary.genTestData();
		IOUtil.writeTextToFile(text, outputFileName);
	}

	public void testJSUtils() {
		String inputFileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\RADx_Global_Codebook_RadxRad_Dictionary.csv";

		JSUtils jSUtils = new JSUtils(inputFileName);
		List<String> rows = jSUtils.toJSArray();

		for (int i = 0; i < rows.size(); i++) {
			System.out.println(rows.get(i));
		}
	}

	public void testReplacement() {
		String str = "WAIVED age in WAIVED years";
		String rs = str.replace("WAIVED", ""); // Replace 'h' with 's'
		System.out.println(rs);

		rs += " WAIVED"; // Replace 'h' with 's'
		System.out.println(rs);
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
			String[] h = obj.getHeader();
			System.out.println(rows.size() + " " + h.length + " " + h[0]);

			int nCol = 25;
			String[] header = new String[h.length + nCol];
			int ik = 0;
			for (int i = 0; i < h.length; i++) {
				header[ik++] = h[i];
			}
			for (int i = 0; i < nCol; i++) {
				header[ik++] = "Col" + (h.length + i);
			}

			String[][] col = new String[nCol][];
			for (int i = 0; i < nCol; i++) {
				col[i] = genRandomDigitString(rows.size(), 1000000000);
			}
			for (int i = 0; i < rows.size(); i++) {
				String[] r = rows.get(i);
				int nr = r.length;
				String[] or = new String[nr + nCol];
				int kj = 0;
				for (int j = 0; j < r.length; j++) {
					or[kj++] = r[j];
				}
				for (int j = 0; j < nCol; j++) {
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

	protected String[] genRandomDigitString(int nRows, int limit) {
		String[] s = new String[nRows];
		Random rn = new Random();
		int k = 0;
		int a = 0;
		// int limit = 1000000000;
		int min = limit / 10;
		do {
			a = rn.nextInt(limit);
			if (a > min) {
				s[k++] = a + "";
			}
		} while (k < nRows);
		return s;
	}

	public void writeData(String outputFileName, String[] header, List<String[]> rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outputFileName));
			writeLine(header, writer);
			for (String[] r : rows) {
				writer.newLine();
				writeLine(r, writer);
			}
			System.out.println("sucessfully saved: " + outputFileName);
			writer.close();
		} catch (IOException e) {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException exc) {
				}
			}
			System.out.println("save file failed: " + outputFileName);
		}
	}

	private void writeLine(String[] s, BufferedWriter writer) {
		try {
			writer.write(s[0]);
			for (int i = 1; i < s.length; i++) {
				writer.write("," + s[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testPOI() {
		String filename = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\dataDictionary\\WASTEWATER\\Wastewater_Data_Standards.xlsx";
		String path = "C:\\prowess\\geohammer\\lib\\census";
		ZipDatabase zipDatabase = new ZipDatabase(filename);
		int iMethod = 1;
		if (iMethod == 0) {
			List<Zip> zip5 = zipDatabase.getZip5();
			List<Zip> zip51 = zipDatabase.sortAndCombine(zip5);
			zipDatabase.print(zip5, 0, 1);
			zipDatabase.print(zip51, 0, 1);
			List<Zip> d5 = zipDatabase.containsDigit(5, 0, zip5);
			// zipDatabase.print(d5, 0, 5);
			List<Zip> fd = zipDatabase.find("0602", zip51);
			zipDatabase.print(fd);
		} else if (iMethod == 1) {
			String zipcode = zipDatabase.calQualifiedZipcode("440");
			if (zipcode == null) {
				System.out.println("null");
			} else {
				System.out.println(zipcode + "");
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
