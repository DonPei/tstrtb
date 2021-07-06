package edu.uth.app.studyid;

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
import edu.uth.app.common.table.CsvModel;
import edu.uth.app.common.table.CsvTable;
import edu.uth.kit.project.ProjectSig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyIdFrameBase extends CommonFrame {
	private static final long serialVersionUID 		= 1L;
	protected int 					exitCode 			= 1;
	protected ProjectSig 			project 			= null;
	protected int appId = 0;
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	protected StudyIdMenuBar 			menuBar			= null;
	protected JTextArea textArea = null;
	
	//protected int mode = 0;
	protected StudyIdDatabase database = null;
	protected CsvTable csvTable = null;
	protected RtbCardPanel rtbCardPanel = null;

	public StudyIdFrameBase(String title, int appId) {
		super(title);
		this.appId = appId;
		textArea = new JTextArea();
		csvTable = new CsvTable();
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
				"  <LI><font size=\"5\"> Select csv files from left panel, then click run button on the top to calculate study ID.</font>" +
				"  <LI><font size=\"5\"> All output files are inside <em>xxxResults</em> which is in the same folder as your data file." +
				"</OL>"+
				"<p></p>"+

	"<font size=\"7\" color=\"maroon\"><b>Subject Data Format - Mode 1:</b></font>";
			return part1 +
					"<OL>" +
					"  <LI><font size=\"5\"> Mode 1 is automatically selected if input csv files contain three columns. </font>" +
					"  <LI><font size=\"5\"> Column 1: \"Name\" - subject name in the format of first name + space + last name. No middle name allowed</font>" +
					"  <LI><font size=\"5\"> Column 2: \"Gender\" - (F for female, M for male, U for unknown, O for others, and blank for unknown).</font>" +
					"  <LI><font size=\"5\"> Column 3: \"Date of Birth\" - in format mm/dd/yyyy.</font>" +
					"  <LI><font size=\"5\">A sample file \"NHash-mode-1-subject-data.csv\" is provided under [installationFolder]/examples/subjectid.</font>" +
					"</OL>"+

"<p></p>"+
"<font size=\"7\" color=\"maroon\"><b>Subject Data Format - Mode 2:</b></font>" +
"<OL>" +
"  <LI><font size=\"5\"> Mode 2 is automatically selected if input csv files contain two columns. </font>" +
"  <LI><font size=\"5\"> Column 1: Project Number.</font>" +
"  <LI><font size=\"5\"> Column 2: Local subject identifier.</font>" +
"  <LI><font size=\"5\">A sample file \"NHash-mode-2-subject-data.csv\" is provided under [installationFolder]/examples/subjectid.</font>" +
"</OL>"+
//"<p></p>"+
//"<font size=\"7\" color=\"maroon\"><b>Date Format:</b></font>" +
//"<OL>" +
//"  <LI><font size=\"5\">M(M)/d(d)/yyyy (example: 05/01/2020, 5/01/2020, 05/1/2020, 5/1/2020)</font>" +
//"  <LI><font size=\"5\">M(M)-d(d)-yyyy (example: 05-01-2020, 5-01-2020, 05-1-2020, 5-1-2020)</font>" +
//"  <LI><font size=\"5\">yyyy/M(M)/d(d) (example: 2000/05/01, 2000/5/01, 2000/05/1, 2000/5/1)</font>" +
//"  <LI><font size=\"5\">yyyy-M(M)-d(d) (example: 2000-05-01, 2000-5-01, 2000-05-1, 2000-5-1)</font>" +
//"</OL>"+

//"<p></p>"+
//"<font size=\"7\" color=\"maroon\"><b>Notes:</b></font>" +
//"<OL>" +
//"  <LI><font size=\"5\">Note 1: ...</font>" +
//"</OL>"+


//				"<p></p>"+
//				"<font size=\"7\" color=\"maroon\"><b>Reference (under [installationFolder]/doc):</b></font>" +
//				"<OL>" +
//				"  <LI><font size=\"5\">NHash: Randomized N-Gram Hashing for Distributed Generation of Validatable Unique Study Identifiers in Multicenter Research. </font>" +
//				"</OL>"+
				"</body></html>";	
	}
	
	public String introductionMessage1() {
		String part1 = "<html><body>" + 
				"<font size=\"7\" color=\"maroon\"><b>Usage:</b></font>"+
				"<OL>" +
				"  <LI><font size=\"5\">File>>New... to create a new project.</font>" +
				"  <LI><font size=\"5\"> Once created, the project should be <font size=\"6\"><b>IMMEDIATELY</b></font>  saved.</font>" +
				"  <LI><font size=\"5\"> Select csv files from left panel, then click run button on the top to calculate study ID.</font>" +
				"  <LI><font size=\"5\"> Mode 1 is automatically selected if input files contain three columns. Otherwise mode 2 is selected.</font>" +
				"  <LI><font size=\"5\"> All output files are inside <em>xxxResults</em> which is in the same folder as your data file." +
				"</OL>"+
				"<p></p>"+

	"<font size=\"7\" color=\"maroon\"><b>Mode 1:</b></font>";
			return part1 +
					"<OL>" +
					"  <LI><font size=\"5\">Generate study IDs by subject name, gender, and date of birth (DOB).</font>" +
					"  <LI><font size=\"5\">Study IDs generated by the same name, gender, and DOB will be the same for <b>MULTIPLE</b> runs.</font>" +
					"  <LI><font size=\"5\">Therefore, this study IDs <b>CAN</b> be used to link subjects among different studies.</font>" +
					"</OL>"+

"<p></p>"+
"<font size=\"7\" color=\"maroon\"><b>Mode 2:</b></font>" +
"<OL>" +
"  <LI><font size=\"5\">Generate study IDs by study number and subject local identifier number.</font>" +
"  <LI><font size=\"5\">The generated study IDs will be <b>MULTIPLE</b> different every time users run the generator.</font>" +
"  <LI><font size=\"5\">Therefore, this study IDs <b>CANNOT</b> be used to link subjects among different studies.</font>" +
"</OL>"+
//"<p></p>"+
//"<font size=\"7\" color=\"maroon\"><b>Date Format:</b></font>" +
//"<OL>" +
//"  <LI><font size=\"5\">M(M)/d(d)/yyyy (example: 05/01/2020, 5/01/2020, 05/1/2020, 5/1/2020)</font>" +
//"  <LI><font size=\"5\">M(M)-d(d)-yyyy (example: 05-01-2020, 5-01-2020, 05-1-2020, 5-1-2020)</font>" +
//"  <LI><font size=\"5\">yyyy/M(M)/d(d) (example: 2000/05/01, 2000/5/01, 2000/05/1, 2000/5/1)</font>" +
//"  <LI><font size=\"5\">yyyy-M(M)-d(d) (example: 2000-05-01, 2000-5-01, 2000-05-1, 2000-5-1)</font>" +
//"</OL>"+

				"<p></p>"+
				"<font size=\"7\" color=\"maroon\"><b>Column Format:</b></font>" +
				"<OL>" +
				"  <LI><font size=\"5\">Subject name: first name + space + last name</font>" +
				"  <LI><font size=\"5\">Gender: F for female, M for male, U for unknown, and O for others</font>" +
				"  <LI><font size=\"5\">Date of Birth: MM/dd/yyyy</font>" +
				"  <LI><font size=\"5\">Other columns: string</font>" +
				"</OL>"+
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

	public ProjectSig getProject() 					{ return project; }
	public StudyIdMenuBar getThisMenuBar() 				{ return menuBar; }

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
