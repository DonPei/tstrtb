package edu.uth.app.common.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CsvModel extends AbstractTableModel {
	int nRows = 1;
	int nCols = 1;
	private String [] columnNameOrigin = null;
	private String [] columnName = null;
	private List<String[]> rows = null;
	private int [] id = null;

	public CsvModel(boolean addParenthesis, String [] columnName, List<String[]> rows) {
		this.columnName = new String[columnName.length+1];
		this.columnName[0] = " ";
		this.columnNameOrigin = new String[this.columnName.length];
		this.columnNameOrigin[0] = " ";
		for(int i=0; i<columnName.length; i++) {
			if(addParenthesis) {
				this.columnName[i+1] = columnName[i]+"("+(i+1)+")";
			} else {
				this.columnName[i+1] = columnName[i];
			}
			this.columnNameOrigin[i+1] = columnName[i];
		}
		this.rows = rows;
		nRows = rows.size();
		nCols = this.columnName.length;
		id = new int[nRows];
		for(int i=0; i<nRows; i++) {
			id[i] = i+1;
		}
	}

	public String getColumnNameOrigin(int column) 		{ return columnNameOrigin[column]; }
	public int getRowCount() 							{ return nRows;  }
	public int getColumnCount() 						{ return nCols; }
	public String getColumnName(int column) 			{ return columnName[column]; }
	public Object getValueAt(int iRow, int jCol) {
		if (iRow < 0 || iRow >= nRows) {
			return " ";
		} else {
			if(jCol==0) {
				return id[iRow];
			} else {
				return rows.get(iRow)[jCol-1];
			}
		}
	}
	public void setValueAt(Object value, int iRow, int iCol) {
		if(iCol==0) return;
		rows.get(iRow)[iCol-1] = value.toString();
		fireTableCellUpdated(iRow, iCol);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
}
