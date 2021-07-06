package edu.uth.app.qac.validator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.uth.app.common.table.CsvModel;
import edu.uth.app.common.table.CsvTable;
import edu.uth.app.qac.LogLevel;
import edu.uth.app.qac.QacDatabase;
import edu.uth.app.qac.QacFrame;
import edu.uth.kit.project.CsvReaderWriter;
import edu.uth.radx.model.Dictionary;
import edu.uth.radx.model.entities.Question;

public class DataValidator implements Validator {
	private QacFrame frame;
	private CsvTable table;
	private CsvModel model;
	private Dictionary dictionary;
	private int[] checkListColIndex;
	private List<String> errorList = null;
	public CsvReaderWriter csvReaderWriter;
	private String inputDictionaryFileName;

	public DataValidator(QacFrame frame, CsvTable table, String inputDictionaryFileName) {
		this.frame = frame;
		this.table = table;
		if (frame != null) {
			model = table.getCsvModel();
			Dictionary inputDictionary = frame.getDatabase().loadDictionary(inputDictionaryFileName);
			frame.getDatabase().setDictionary(inputDictionary);
			dictionary = frame.getDatabase().getDictionary();
		} else {
			QacDatabase database = new QacDatabase(null);
			Dictionary inputDictionary = database.loadDictionary(inputDictionaryFileName);
			database.setDictionary(inputDictionary);
			dictionary = database.getDictionary();
		}
	}

	public String validate(String inputFileName) {
		errorList = new ArrayList<>();
		boolean stopAtFirstError = false;
		if (inputFileName != null) {
			csvReaderWriter = new CsvReaderWriter();
			csvReaderWriter.read(inputFileName, true);
		}
		List<Integer> rowIndexes = checkData(stopAtFirstError);

		if (frame != null) {
			table.setHighlightColor(Color.red);
			table.setIsChanged(false);
			if (rowIndexes == null || rowIndexes.size() == 0) {

			} else if (rowIndexes.size() == 1) {
				int rowIndex = rowIndexes.get(0);
				if (rowIndex == -2) {
					table.setHighlightColor(Color.green);
					table.setIsChanged(true);
				} else if (rowIndex == -1) {
					table.setHighlightColor(Color.red);
					table.setIsChanged(true);
				} else {
					table.setHighlightColor(Color.red);
					table.setIsChanged(rowIndex, true);
					table.scrollCellToView(rowIndex, 0);
				}
			} else {
				int firstErrorRowIndex = -99999;
				table.setHighlightColor(Color.red);
				for (int rowIndex : rowIndexes) {
					if (rowIndex == -1) {
						table.setIsChanged(true);
						break;
					} else {
						if (firstErrorRowIndex == -99999) {
							firstErrorRowIndex = rowIndex;
						}
						table.setIsChanged(rowIndex, true);
					}
				}
				if (firstErrorRowIndex != -99999) {
					table.scrollCellToView(firstErrorRowIndex, 0);
				}
			}
			table.repaint();
			return null;
		} else {
			if (rowIndexes == null || rowIndexes.size() == 0) {
				appendLine(LogLevel.INFO, "Program has some bugs.");
			} else if (rowIndexes.size() == 1) {
				int rowIndex = rowIndexes.get(0);
				if (rowIndex == -2) {
					appendLine(LogLevel.INFO, "The data satisfies all requirements.");
				} else if (rowIndex == -1) {
				} else {
				}
			} else {
			}
			StringBuilder sb = new StringBuilder();
			for (String s : errorList) {
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}
	}

	private String checkHeader(String[] keys, boolean stopAtFirstError) {
		StringBuilder sb = new StringBuilder();
		String errorMessage = null;
		boolean errorOccurred = false;
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i].trim();
			int iCol = getHeaderColIndex(key);
			if (iCol < 0) {
				errorMessage = ("Either \"" + key + "\" doese not exist or spelling (case sensitive) wrong at column header");
				sb.append(errorMessage);
				errorOccurred = true;
				if (stopAtFirstError) {
					break;
				} else {
					sb.append("\n");
				}
			}
		}
		if (errorOccurred) {
			return sb.toString();
		} else {
			return null;
		}
	}

	private String checkRow(int rowIndex, String[] keys, boolean stopAtFirstError) {
		StringBuilder sb = new StringBuilder();
		String errorMessage = null;
		boolean errorOccurred = false;
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i].trim();
			int iCol = getColIndex(rowIndex, key);
			if (iCol < 0) {
				errorMessage = ("Either \"" + key + "\" doese not exist or spelling (case sensitive) wrong at row index " + (rowIndex));
				sb.append(errorMessage);
				errorOccurred = true;
				if (stopAtFirstError) {
					break;
				} else {
					sb.append("\n");
				}
			}
		}
		if (errorOccurred) {
			return sb.toString();
		} else {
			return null;
		}
	}

	private List<Integer> checkData(boolean stopAtFirstError) {
		boolean errorOccurred = false;
		int firstErrorRowIndex = -99999;
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		List<Integer> rowIndexes = new ArrayList<>();
		int rowIndex = -1;

		List<Question> questions = dictionary.getQuestions();
		String[] keys = new String[questions.size()];
		for (int i = 0; i < questions.size(); i++) {
			keys[i] = questions.get(i).getRow()[0];
		}

		String errorMessage = checkHeader(keys, stopAtFirstError);
		if (errorMessage != null) {
			// appendLine(LogLevel.ERROR, errorMessage);
			rowIndexes.add(rowIndex);
			if (stopAtFirstError) {
				return rowIndexes;
			} else {
				sb.append(errorMessage);
				if (firstErrorRowIndex == -99999) {
					firstErrorRowIndex = rowIndex;
				}
				errorOccurred = true;
			}
		}

		rowIndex++;

		if (errorOccurred) {
			appendLine(LogLevel.ERROR, sb.toString());
			if (firstErrorRowIndex == -99999) {
				return rowIndexes;
			} else {
				return rowIndexes;
			}
		} else {
			appendLine(LogLevel.INFO, "The data satisfies all requirements.");
			rowIndexes.add(-2);
			return rowIndexes;
		}
	}

	private void appendLine(LogLevel logLevel, String message) {
		if (frame != null) {
			frame.appendLine(logLevel, message);
		} else {
			errorList.add(logLevel.getDescription() + ": " + message);
		}
	}

	private String getValueAt(int rowIndex, int colIndex) {
		if (model != null) {
			return (String) model.getValueAt(rowIndex, colIndex);
		} else {
			String[] row = csvReaderWriter.getRows().get(rowIndex);
			return row[colIndex];
		}
	}

	private int getHeaderColIndex(String text) {
		int nCol = 0;
		int col1 = 0;

		if (model != null) {
			nCol = model.getColumnCount();
			col1 = 1;
		} else {
			nCol = csvReaderWriter.getHeader().length;
		}
		for (int i = col1; i < nCol; i++) {
			String value = null;
			if (model != null) {
				value = model.getColumnNameOrigin(i);
			} else {
				value = csvReaderWriter.getHeader()[i];
			}

			if (equal(text, value)) {
				return i;
			}
		}
		return -1;
	}

	private int getColIndex(int rowIndex, String text) {
		int nCol = 0;
		int col1 = 0;
		if (model != null) {
			nCol = model.getColumnCount();
			col1 = 1;
		} else {
			nCol = csvReaderWriter.getRows().get(rowIndex).length;
		}
		for (int i = col1; i < nCol; i++) {
			String value = getValueAt(rowIndex, i);
			if (equal(text, value)) {
				return i;
			}
		}
		return -1;
	}

	private int getRowIndex(int columnIndex, String text) {
		int nRow = 0;
		if (model != null) {
			nRow = model.getRowCount();
		} else {
			nRow = csvReaderWriter.getRows().size();
		}
		for (int i = 0; i < nRow; i++) {
			String value = getValueAt(i, columnIndex);
			if (equal(text, value)) {
				return i;
			}
		}
		return -1;
	}
}
