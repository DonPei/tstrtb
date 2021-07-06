package edu.uth.app.common.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class CsvTable extends JTable {

	private CsvModel 	csvModel 	= null;
	private boolean [] isChanged = null;
	private Color highlightColor = Color.yellow;

	public CsvModel getCsvModel() {
		return csvModel;
	}
	public CsvTable() {
	}

	public void setCsvModel(boolean addParenthesis, String [] columnName, List<String[]> rows) {
		List<String []> copy = rows.stream().collect(Collectors.toList());
		csvModel = new CsvModel(addParenthesis, columnName, copy);
		//zfmModel.addTableModelListener(this);
		isChanged = new boolean[rows.size()];
		for(int i=0; i<isChanged.length; i++) {
			isChanged[i] = false;
		}
		//setAutoCreateColumnsFromModel(false);
		setModel(csvModel);
		((DefaultTableCellRenderer)getTableHeader()
				.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		for (int k = 0; k <csvModel.getColumnCount(); k++) {
			CsvTableCellRenderer renderer = new CsvTableCellRenderer();
			renderer.setHorizontalAlignment(JLabel.CENTER);
			getColumnModel().getColumn(k).setCellRenderer(renderer);
		}
		setRowHeight(30);
		setCellSelectionEnabled(false);
		//getTableHeader().setReorderingAllowed(false);
		//setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		fixWidth(0, 40);
	}
	
	
	public void toggleTableView() {
		int mode = getAutoResizeMode();
		if(mode==JTable.AUTO_RESIZE_OFF) {
			//setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			autoResizeAllColumnsWithHeader();
		} else {
			setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		repaint();
	}
	
	private void autoResizeAllColumnsWithHeader() {
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		for (int column = 0; column < getColumnCount(); column++){
		    TableColumn tableColumn = getColumnModel().getColumn(column);
		    //int preferredWidth = tableColumn.getMinWidth();
		    int maxWidth = 0;
		    TableCellRenderer rend = getTableHeader().getDefaultRenderer();
		    TableCellRenderer rendCol = tableColumn.getHeaderRenderer();
		    if (rendCol == null) rendCol = rend;
		    Component header = rendCol.getTableCellRendererComponent(this, tableColumn.getHeaderValue(), false, false, 0, column);
		    maxWidth = header.getPreferredSize().width;
		    //System.out.println("col= :"+column + " maxWidth :"+maxWidth);

		    for (int row = 0; row < getRowCount(); row++){
		    //for (int row = 0; row < 1; row++){
		        TableCellRenderer cellRenderer = getCellRenderer(row, column);
		        Component c = prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + getIntercellSpacing().width;
		        maxWidth = Math.max(maxWidth, width);
		        //System.out.println("preferredWidth :"+preferredWidth);
		        //System.out.println("Width :"+width);
//		        if (preferredWidth <= maxWidth){
//		            preferredWidth = maxWidth;
//		            break;
//		        }
		    }
		    tableColumn.setPreferredWidth(maxWidth);
		}
	}
	
	private void fixWidth(int columnIndex, int width) {
		TableColumn column = getColumnModel().getColumn(columnIndex);
		column.setMinWidth(width);
		column.setMaxWidth(width);
		column.setPreferredWidth(width);
	}
	public void setIsChanged(boolean changed) {
		for(int i=0; i<isChanged.length; i++) {
			isChanged[i] = changed;
		}
	}
	public void setIsChanged(int index, boolean changed) {
		isChanged[index] = changed;
	}
	public Color getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}

	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int colIndex) {
		Component c = super.prepareRenderer(renderer, rowIndex, colIndex);
		if(isChanged!=null && isChanged[rowIndex]) {
			c.setBackground(highlightColor);
		} else {
			if (rowIndex % 2 == 0 ) {
				c.setBackground(new Color(240,240,240));
			} else {
				c.setBackground(getBackground());
			}
		}
		return c;
	}
	
	public void scrollCellToView(int rowIndex, int vColIndex) {
	    if (!(this.getParent() instanceof JViewport)) {
	        return;
	    }
	    JViewport viewport = (JViewport) this.getParent();
	    Rectangle rect = this.getCellRect(rowIndex, vColIndex, true);
	    Rectangle viewRect = viewport.getViewRect();

	    int x = viewRect.x;
	    int y = viewRect.y;

	    if (rect.x >= viewRect.x && rect.x <= (viewRect.x + viewRect.width - rect.width)){

	    } else if (rect.x < viewRect.x){
	        x = rect.x;
	    } else if (rect.x > (viewRect.x + viewRect.width - rect.width)) {
	        x = rect.x - viewRect.width + rect.width;
	    }

	    if (rect.y >= viewRect.y && rect.y <= (viewRect.y + viewRect.height - rect.height)){

	    } else if (rect.y < viewRect.y){
	        y = rect.y;
	    } else if (rect.y > (viewRect.y + viewRect.height - rect.height)){
	        y = rect.y - viewRect.height + rect.height;
	    }

	    viewport.setViewPosition(new Point(x,y));
	}
	
	public class CsvTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int rowIndex, int colIndex) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, 
					hasFocus, rowIndex, colIndex);

			int rendererWidth = c.getPreferredSize().width;
			TableColumn tableColumn = getColumnModel().getColumn(colIndex);
			tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

			return c;
		}
	}
	
}
