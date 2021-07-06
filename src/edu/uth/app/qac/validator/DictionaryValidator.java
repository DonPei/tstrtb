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

public class DictionaryValidator implements Validator {
	private QacFrame frame;
	private CsvTable table;
	private CsvModel model;
	private Dictionary dictionary;
	private int[] checkListColIndex;
	private List<String> errorList = null;
	public CsvReaderWriter csvReaderWriter;

	public DictionaryValidator(QacFrame frame, CsvTable table, int[] checkListColIndex) {
		this.frame = frame;
		this.table = table;
		if (frame != null) {
			model = table.getCsvModel();
			dictionary = frame.getDatabase().getDictionary();
		} else {
			QacDatabase database = new QacDatabase(null);
			dictionary = database.getDictionary();
		}
		this.checkListColIndex = checkListColIndex;
	}

	public String validate(String inputFileName) {
		errorList = new ArrayList<>();
		boolean stopAtFirstError = false;
		if (inputFileName != null) {
			csvReaderWriter = new CsvReaderWriter();
			csvReaderWriter.read(inputFileName, true);
		}
		List<Integer> rowIndexes = checkCde(stopAtFirstError);

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
					appendLine(LogLevel.INFO, "The data dictionary satisfies all minimum CDE requirements.");
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

	private String checkRow(int rowIndex, String[] checkListName, boolean stopAtFirstError) {
		StringBuilder sb = new StringBuilder();
		String errorMessage = null;
		boolean errorOccurred = false;
		for (int i = 0; i < checkListName.length; i++) {
			String key = checkListName[i].trim();
			int colIndex = checkListColIndex[i];
			String value = getValueAt(rowIndex, colIndex);
			value = value.trim();
			if (!equal(key, value)) {
				errorMessage = ("Either \"" + key + "\" doese not exist or spelling (case sensitive) wrong at row index "
						+ (rowIndex) + " colIndex " + (colIndex) + ". The current value is " + value);
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

	private List<Integer> checkCde(boolean stopAtFirstError) {
		boolean errorOccurred = false;
		int firstErrorRowIndex = -99999;
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		List<Integer> rowIndexes = new ArrayList<>();
		int rowIndex = 0;
		String study_id = dictionary.getQuestions().get(0).getVariable();
		String errorMessage = checkRow(rowIndex, new String[] { study_id }, stopAtFirstError);
		if (errorMessage != null) {
			errorMessage = "variable " + study_id + " must be the first row in dictionary.";
			appendLine(LogLevel.ERROR, errorMessage);
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

		List<Question> questions = dictionary.getQuestions();
		String[] keys = new String[questions.size()];
		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
			if (question.getWaived()) {
				continue;
			}
			String variableName = question.getVariable();
			if ("height_embedding".equals(variableName)) {
				continue;
			}
			rowIndex = getRowIndex(checkListColIndex[0], variableName);
			if (rowIndex < 0) {
				errorMessage = "The minimum CDE variable \"" + variableName + "\" does not exist.";
				errorOccurred = true;
				rowIndexes.add(rowIndex);
				if (stopAtFirstError) {
					appendLine(LogLevel.ERROR, errorMessage);
					return rowIndexes;
				} else {
					sb.append("\n");
					sb.append(errorMessage);
					if (firstErrorRowIndex == -99999) {
						firstErrorRowIndex = rowIndex;
					}
					continue;
				}
			}
			String[] checkListName = new String[] {
					variableName,
					question.getFieldType(),
					question.getFieldLabel(),
					question.getChoice()
			};
			errorMessage = checkRow(rowIndex, checkListName, stopAtFirstError);
			if (errorMessage != null) {
				rowIndexes.add(rowIndex);
				errorOccurred = true;
				if (stopAtFirstError) {
					break;
				} else {
					sb.append(errorMessage);
					if (firstErrorRowIndex == -99999) {
						firstErrorRowIndex = rowIndex;
					}
				}
			}
		}

		if (errorOccurred) {
			appendLine(LogLevel.ERROR, sb.toString());
			if (firstErrorRowIndex == -99999) {
				return rowIndexes;
			} else {
				return rowIndexes;
			}
		} else {
			appendLine(LogLevel.INFO, "The data dictionary satisfies all minimum CDE requirements.");
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

	// private String exist(int columnIndex, String text) {
	// int nRow = model.getRowCount();
	// for(int i=0; i<nRow; i++) {
	// String value = (String)model.getValueAt(i, columnIndex);
	// if(fuzzyEqual(text, value)) {
	// return null;
	// }
	// }
	// return text+" does not exist";
	// }

	private String getValueAt(int rowIndex, int colIndex) {
		if (model != null) {
			return (String) model.getValueAt(rowIndex, colIndex);
		} else {
			String[] row = csvReaderWriter.getRows().get(rowIndex);
			return row[colIndex];
		}
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
