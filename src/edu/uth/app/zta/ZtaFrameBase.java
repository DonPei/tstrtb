package edu.uth.app.zta;

import java.awt.BorderLayout;
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
import edu.uth.app.zta.dialog.ZipDatabase;
import edu.uth.app.common.table.CsvModel;
import edu.uth.app.common.table.CsvTable;
import edu.uth.kit.project.Project;
import edu.uth.kit.project.ProjectZta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZtaFrameBase extends CommonFrame {
	private static final long serialVersionUID 		= 1L;
	protected int 					exitCode 			= 1;
	protected ProjectZta 			project 			= null;
	protected String projectFileExtension = null;
	protected int appId = 0; //0 zta 1 dts
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	protected ZtaMenuBar 			menuBar			= null;
	protected JTextArea textArea = null;

	protected CsvTable csvTable = null;
	protected ZipDatabase zipDatabase = null;
	protected DateShift dateShift = null;
	protected RtbCardPanel rtbCardPanel = null;

	public ZtaFrameBase(String title, int appId) {
		super(title);
		this.appId = appId;
		projectFileExtension = Project.getProjectFileExtension(appId);
		textArea = new JTextArea();
		csvTable = new CsvTable();
		rtbCardPanel = new RtbCardPanel();
		JTextPane htmlPane = new JTextPane();
		htmlPane.setContentType("text/html");
		htmlPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 24));
		htmlPane.setText(introductionMessage(appId));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(1, 5, 1, 1));
		panel.add(htmlPane, BorderLayout.CENTER);
		rtbCardPanel.addCard(panel, RtbCardPanel.Key.INTRODUCTIONPANEL);
	}

	public String introductionMessage(int appId) {
		String part1 = "<html><body>" + 
				"<font size=\"7\" color=\"maroon\"><b>Usage:</b></font>"+
				"<OL>" +
				"  <LI><font size=\"5\">File>>New... to create a new project.</font>" +
				"  <LI><font size=\"5\"> Once created, the project should be <font size=\"6\"><b>IMMEDIATELY</b></font>  saved.</font>" +
				"  <LI><font size=\"5\"> Select column index (number inside parentheses of the header) and click run buttom.</font>" +
				"  <LI><font size=\"5\"> All output files are inside <em>xxxResults</em> which is in the same folder as your data file.</font>" +
				"</OL>"+
				"<p></p>"+

	"<font size=\"7\" color=\"maroon\"><b>Function:</b></font>";
		if(appId==0) {
			return part1 +
					"<OL>" +
					"  <LI><font size=\"5\">Validate the zip code.</font>" +
					"  <LI><font size=\"5\">Truncate last digit of the zip code if its population is less than a threshold.</font>" +
					"  <LI><font size=\"5\">Repeat the above process until the truncated zip code population is equal to or greater than the threshold.</font>" +
					"  <LI><font size=\"5\">The original zip code will be replaced by the final truncated one.</font>" +
					"</OL>"+
					"</body></html>";	
		} else if(appId==10) {
			return part1 +
					"<OL>" +
					"  <LI><font size=\"5\">Subject ID is the unique key for all files.</font>" +
					"  <LI><font size=\"5\">Randomly shifting date off a random number of days.</font>" +
					"  <LI><font size=\"5\">The shifting amount is the same for the same ID.</font>" +
					"</OL>"+

				"<p></p>"+
				"<font size=\"7\" color=\"maroon\"><b>Date Format:</b></font>" +
				"<OL>" +
				"  <LI><font size=\"5\">M(M)/d(d)/yyyy (example: 05/01/2020, 5/01/2020, 05/1/2020, 5/1/2020)</font>" +
				"  <LI><font size=\"5\">M(M)-d(d)-yyyy (example: 05-01-2020, 5-01-2020, 05-1-2020, 5-1-2020)</font>" +
				"  <LI><font size=\"5\">yyyy/M(M)/d(d) (example: 2000/05/01, 2000/5/01, 2000/05/1, 2000/5/1)</font>" +
				"  <LI><font size=\"5\">yyyy-M(M)-d(d) (example: 2000-05-01, 2000-5-01, 2000-05-1, 2000-5-1)</font>" +
				"</OL>"+
				
"<p></p>"+
"<font size=\"7\" color=\"maroon\"><b>Time Format:</b></font>" +
"<OL>" +
"  <LI><font size=\"5\">Time string could be empty. If presented, it must immediately follow date string.</font>" +
"  <LI><font size=\"5\">Both are connected by a letter T (example: 2000-05-01T00:01:01)</font>" +
"  <LI><font size=\"5\"><b>OR</b> by <b>ONE</b> white space (example: 2000-05-01 00:01:01)</font>"  +
"</OL>"+
				"</body></html>";	
		} else {
			return null;
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
	public void appendLine(String message) {
		textArea.append(message+"\n");
	}
	public void append(String message) {
		textArea.append(message);
	}
	public void appendLine(int level, String message) {
		if(level==0) {
			appendLine(message);
		} else if(level==1) {
			appendLine("    "+message);
		} else if(level==2) {
			appendLine("        "+message);
		} else if(level==3) {
			appendLine("            "+message);
		} else {
			appendLine(message);
		}
	}

	public ProjectZta getProject() 					{ return project; }
	public ZtaMenuBar getThisMenuBar() 				{ return menuBar; }

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
