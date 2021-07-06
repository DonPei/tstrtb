package edu.uth.app.qac;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;


import edu.uth.app.common.CommonFrame;
import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.common.table.CsvModel;
import edu.uth.app.common.table.CsvTable;
import edu.uth.app.component.ColorTextPane;
import edu.uth.kit.project.ProjectQac;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QacFrameBase extends CommonFrame {
	private static final long serialVersionUID 		= 1L;
	protected int 					exitCode 			= 1;
	protected ProjectQac 			project 			= null;
	protected int appId = 0;
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	protected QacMenuBar 			menuBar			= null;
	protected ColorTextPane colorTextPane = null; 
	protected QacDatabase database = null;
	
	protected int mode = 1; //"Meta Files  ", "Dictionary Files  ", "Data Files  "
	protected CsvTable csvTable = null;
	protected RtbCardPanel rtbCardPanel = null;

	public QacFrameBase(String title, int appId) {
		super(title);
		this.appId = appId;
		colorTextPane = new ColorTextPane();		
		csvTable = new CsvTable();
		csvTable.setHighlightColor(Color.red);
		rtbCardPanel = new RtbCardPanel();
		JTextPane htmlPane = new JTextPane();
		htmlPane.setContentType("text/html");
		htmlPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
		htmlPane.setText(introductionMessage());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(1, 5, 1, 1));
		panel.add(htmlPane, BorderLayout.CENTER);
		rtbCardPanel.addCard(panel, RtbCardPanel.Key.INTRODUCTIONPANEL);
	}

	public String introductionMessage() {
		String part1 = "<html><body>" + 
				"<font size=\"7\" color=\"maroon\"><b>Usage:</b></font>"+
				"<OL>" +
				"  <LI><font size=\"5\">File>>New... to create a new project.</font>" +
				"  <LI><font size=\"5\"> Once created, the project should be <font size=\"6\"><b>IMMEDIATELY</b></font>  saved.</font>" +
				"  <LI><font size=\"5\"> Select mode, random number length, column index (number inside parentheses of the header) and click run buttom.</font>" +
				"  <LI><font size=\"5\"> All output files are inside <em>xxxResults</em> which is in the same folder as your data file.</font>" +
				"</OL>"+
				
				"<p></p>"+
	"<font size=\"7\" color=\"maroon\"><b>Dictionary Rules:</b></font>";
			return part1 +
					"<OL>" +
					"  <LI>Rule 1: The first variable of the dictionary must be “study_id”." + 
					"  <LI>Rule 2: All 12 minimumn CDE must exist for all projects." +
					"  <LI>Rule 3: All 47 minimum CDE variables must exactly match (case sensitive) with the requirements if <b>NOT</b> waivered." +
					"</OL>"+
					
				"<p></p>"+
				"</body></html>";	
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

	public String getMethodDescription(int methodId) {
		StringBuilder sb = new StringBuilder();
		if(methodId==0) {
			sb.append("Zip code replacement algorithm: "+"\n");
			sb.append(
					"This algorithm is composed of iterations. "+
							"The input file must contain population and zipcode columns."+"\n"+
							"Step 1: Check population. For rows whoese population is equal or greater than the threshold, "
							+ "mark it as qualified row and remove them out of iteration."+"\n"+
							"Step 2: For dis-qualified rows, remove the last digit of their zip code."+"\n"+
							"Step 3: Combine population of rows that has the same zipcode"+"\n"+
							"Repeat the above steps until "+"\n"+
							"a) all rows become qualified rows; "+"\n"+
							"or b) all remaining rows contain one digit zipcode."+"\n"
					);
		}

		return sb.toString();
	}

	public void appendCurrentTime() {
		appendLine(Timestamp.from(Instant.now())+"");
	}
	public void append(Color color, String message) {
		colorTextPane.append(color, message);
	}
	
	public void appendLine(String message) {
		appendLine(0, message);
	}
	public void appendLine(int indentLevel, String message) {
		appendLine(LogLevel.INFO, indentLevel, message);
	}
	public void appendLine(LogLevel logLevel, String message) {
		append(logLevel, 0, message, true);
	}
	public void appendLine(LogLevel logLevel, int indentLevel, String message) {
		append(logLevel, indentLevel, message, true);
	}
	public void append(LogLevel logLevel, int indentLevel, String message, boolean appendLine) {
		StringBuilder sb = new StringBuilder(logLevel.getDescription()+": ");
		for(int i=0; i<indentLevel; i++) {
			sb.append("    ");
		}
		sb.append(message);
		if(appendLine) {
			sb.append("\n");
		}
		String text = sb.toString();

		switch (logLevel) {
		case OFF: 
			break;
		case ERROR:
			append(Color.red, text);
			break;
		case WARNING:
			append(Color.orange, text);
			break;
		case INFO:
			append(Color.black, text);
			break;
		default: 
			append(Color.black, text);
			break;
		}

	}

	public ProjectQac getProject() 					{ return project; }
	public QacMenuBar getThisMenuBar() 				{ return menuBar; }

	public String exportTableToCsv(String outputFileName) {
		String info = null;
		BufferedWriter writer = null;
		CsvModel model = csvTable.getCsvModel();
		int nRow = model.getRowCount();
		int nCol = model.getColumnCount();
		
		try {
			writer = new BufferedWriter(new FileWriter(outputFileName));
			StringBuffer sb = new StringBuffer();
			for (int j = 1; j < nCol; j++) {
				if (j!=1) sb.append(",");
				sb.append(model.getColumnNameOrigin(j));
			}
			
			writer.write(sb.toString());
			writer.newLine();
			
			for (int i = 0 ; i < nRow ; i++){
				sb = new StringBuffer();
				for (int j = 1; j < nCol; j++) {
					if (j!=1) sb.append(",");
					sb.append(model.getValueAt(i,j));
				}
				writer.write(sb.toString());
				writer.newLine();
			}
			info = ("sucessfully saved: "+outputFileName);
			writer.close();
		} catch (IOException e) {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException exc) {
				}
			}
			info = ("save file failed: "+outputFileName);
		}
		return info;
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if(visible) {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration [] gc = gd.getConfigurations();
			Rectangle gcBounds = gc[0].getBounds();

			int w = (int)(9.5*gcBounds.getWidth() / 10.0);
			int h = (int)(9.5*gcBounds.getHeight() / 10.0);
			setSize(w, h);
			//setSize(1000, 800);

			setLocationRelativeTo(null);
		}
	}

}
