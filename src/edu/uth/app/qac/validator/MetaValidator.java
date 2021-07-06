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
import edu.uth.radx.model.Meta;
import edu.uth.radx.model.entities.MetaQuestion;

public class MetaValidator implements Validator{
	private QacFrame frame;
	private CsvTable table;
	private CsvModel model;
	private Meta meta;
	private List<String> errorList = null;
	public CsvReaderWriter csvReaderWriter;

	public MetaValidator(QacFrame frame, CsvTable table) {
		this.frame = frame;
		this.table = table;
		if(frame!=null) {
			model = table.getCsvModel();
			meta = frame.getDatabase().getMeta();
		} else {
			QacDatabase database = new QacDatabase(null);
			meta = database.getMeta();
		}
	}

	public String validate(String inputFileName) {
		errorList = new ArrayList<>();
		boolean stopAtFirstError = false;
		if(inputFileName!=null) {
			csvReaderWriter = new CsvReaderWriter();
			csvReaderWriter.read(inputFileName, true);
		}
		List<Integer> rowIndexes = checkMeta(stopAtFirstError);
		
		if(frame!=null) {
			if(rowIndexes==null || rowIndexes.size()==0) {
				
			} else if(rowIndexes.size()==1) {
				int rowIndex = rowIndexes.get(0);
				if(rowIndex ==-2) {
					table.setHighlightColor(Color.green);
					table.setIsChanged(true);
				} else if(rowIndex == -1) {
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
				for(int rowIndex: rowIndexes) {
					if(rowIndex == -1) {
						table.setIsChanged(true);
						break;
					} else {
						if(firstErrorRowIndex == -99999) {
							firstErrorRowIndex = rowIndex;
						}
						table.setIsChanged(rowIndex, true);
					}
				}
				if(firstErrorRowIndex != -99999) {
					table.scrollCellToView(firstErrorRowIndex, 0);
				}
			}	
			table.repaint();
			return null;			
		} else {
			if(rowIndexes==null || rowIndexes.size()==0) {
				appendLine(LogLevel.INFO, "Program has some bugs.");
			} else if(rowIndexes.size()==1) {
				int rowIndex = rowIndexes.get(0);
				if(rowIndex ==-2) {
					appendLine(LogLevel.INFO, "The meta file satisfies all requirements.");
				} else if(rowIndex == -1) {
				} else {
				}
			} else {
			}
			StringBuilder sb = new StringBuilder();
			for(String s: errorList) {
				sb.append(s);
				sb.append("\n");
			}
			return sb.toString();
		}
	}

	private List<Integer> checkMeta(boolean stopAtFirstError) {
		int firstErrorRowIndex = -99999;
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		List<Integer> rowIndexes = new ArrayList<>();
		int rowIndex = 0;
		String errorMessage = null;
		boolean errorOccurred = false;
		List<MetaQuestion> questions = meta.getQuestions();
		for(int i=0; i<questions.size(); i++) {
			MetaQuestion question = questions.get(i);
			String required = question.getRequired();
			if(required!=null && !required.isEmpty()) {
				continue;
			}
			
			String variableName = question.getFieldLabel();
			rowIndex = getRowIndex(1, variableName);
			if(rowIndex<0) {
				errorMessage = "Meta item \""+variableName+"\" does not exist.";
				errorOccurred = true;
				rowIndexes.add(rowIndex);
				if(stopAtFirstError) {
					appendLine(LogLevel.ERROR, errorMessage);
					return rowIndexes;
				} else {
					sb.append("\n");
					sb.append(errorMessage);
					if(firstErrorRowIndex == -99999) {
						firstErrorRowIndex = rowIndex;
					}
					continue;
				}
			}
			int colIndex = 2;
			String value = getValueAt(rowIndex, colIndex);
			value = value.trim();
			if(value==null || value.isEmpty()) {
				errorMessage = ("Value at row index "+ (rowIndex+1)+" colIndex "+(colIndex)+" should not be empty.");
				sb.append(errorMessage);
				rowIndexes.add(rowIndex);
				errorOccurred = true;
				if(stopAtFirstError) {
					break;
				} else {
					sb.append("\n");
					if(firstErrorRowIndex == -99999) {
						firstErrorRowIndex = rowIndex;
					}
				}
			}		
		}

		if(errorOccurred) {
			appendLine(LogLevel.ERROR, sb.toString());
			if(firstErrorRowIndex == -99999) {
				return rowIndexes;
			} else {
				//rowIndexes.add(firstErrorRowIndex);
				return rowIndexes;
			}
		} else {
			appendLine(LogLevel.INFO, "The meta file satisfies all requirements.");
			rowIndexes.add(-2);
			return rowIndexes;
		}
		
	}
	
	private void appendLine(LogLevel logLevel, String message) {
		if(frame!=null) {
			frame.appendLine(logLevel, message);
		} else {
			errorList.add(logLevel.getDescription()+": "+message);
		}
	}
	
	private String getValueAt(int rowIndex, int colIndex) {
		if(model!=null) {
			return (String)model.getValueAt(rowIndex, colIndex);		
		} else {
			String[] row = csvReaderWriter.getRows().get(rowIndex);
			return row[colIndex-1];	
		}
	}
	
	private int getRowIndex(int columnIndex, String text) {
		int nRow = 0;
		if(model!=null) {
			nRow = model.getRowCount();
		} else {
			nRow = csvReaderWriter.getRows().size();
		}
		for(int i=0; i<nRow; i++) {
			String value = getValueAt(i, columnIndex);
			if(equal(text, value)) {
				return i;
			}
		}
		return -1;
	}

//	private String getColumnNameOrigin(int colIndex) {
//		return "\""+model.getColumnNameOrigin(colIndex)+"\"";
//	}
}
