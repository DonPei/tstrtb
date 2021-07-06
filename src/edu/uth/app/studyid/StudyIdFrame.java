package edu.uth.app.studyid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.apache.commons.io.FilenameUtils;

import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.common.table.CsvModel;
import edu.uth.app.component.FileMenuEvent;
import edu.uth.app.component.FileMenuListener;
import edu.uth.app.component.StatusBar;
import edu.uth.kit.project.Project;
import edu.uth.kit.project.ProjectPreference;
import edu.uth.kit.project.ProjectSig;
import edu.uth.radx.model.Foa;
import nhash.StudyIdGenerator;

public class StudyIdFrame extends StudyIdFrameBase implements FileMenuListener, KeyListener {
	private static final long serialVersionUID 		= 1L;

	public StudyIdFrame(int exitCode, String title, int appId) {
		super(title, appId);
		this.exitCode 	= exitCode;

		projectPreference = new ProjectPreference("sig");
		projectPreference.readPreferences();

		project = new ProjectSig(System.getProperty("user.dir")+File.separator+"untitled.sig");

		menuBar = new StudyIdMenuBar(this);
		ArrayList<String> list = projectPreference.getRecentFileList();
		if( list != null ) {
			for( int i = list.size()-1; i >= 0; i-- )  {
				menuBar.addRecentFile(list.get(i));
			}
		}
		for(int i=1; i<menuBar.getMenuCount(); i++) {
			JComponent component = (JComponent)menuBar.getComponent(i);
			component.setEnabled(false);
		}
		setJMenuBar(menuBar);

		setLayout(new BorderLayout());
		StatusBar statusBar = new StatusBar();
		statusBar.setZoneBorder(BorderFactory.createLineBorder(Color.GRAY));

		statusBar.setZones( new String[] { "first_zone", "second_zone", "remaining_zones" },
				new Component[] { new JLabel("first"), new JLabel("second"), new JLabel("remaining") },
				new String[] {"40%", "40%", "*"} );

		add(rtbCardPanel, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);

		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		addWindowListener( new doYouWantSave() );
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		getContentPane().setBackground(Color.white);
	}

	public void keyTyped(KeyEvent e) { }
	public void keyPressed(KeyEvent e) {
		int k 			= e.getKeyCode();
		if(e.isControlDown()&&!e.isAltDown()&&!e.isShiftDown()){
			if(k==KeyEvent.VK_1) {
				ArrayList<String> list = menuBar.getRecentFileList();
				if(list!=null && list.size()>=1) openProject(list.get(0));
			}
			if(k==KeyEvent.VK_2) {
				ArrayList<String> list = menuBar.getRecentFileList();
				if(list!=null && list.size()>=2) openProject(list.get(1));
			}
			if(k==KeyEvent.VK_3) {
				ArrayList<String> list = menuBar.getRecentFileList();
				if(list!=null && list.size()>=3) openProject(list.get(2));
			}
		}
	}
	public void keyReleased(KeyEvent e) { }
	public void savePreferences() {
		ArrayList<String> list = menuBar.getRecentFileList();
		if(list.size()>0) {
			projectPreference.setRecentFileList(list);
			projectPreference.writePreferences();
		}
	}

	public void createProject(String projectFileName, int iId, int iUnit, int velFileType, String velFileName)	{
		project = new ProjectSig(projectFileName, iId, iUnit, velFileType, velFileName);

		if(projectFileName!=null) {
			for(int i=1; i<menuBar.getMenuCount(); i++) {
				JComponent component = (JComponent)menuBar.getComponent(i);
				component.setEnabled(true);
			}
			menuBar.getJMenuItemOpenFile().setEnabled(true);

			if(!projectFileName.contains("untitled")) menuBar.addRecentFile(project.getProjectFileName());
			setTitle(projectFileName);

		}
	}

	public void loadProject(String projectName, int iId, int iUnit,
			int fileType, String fileName) {

		String fullPath = FilenameUtils.getFullPath(fileName);
		if (projectName == null) {
			projectName = fullPath + "untitled." + Project.getProjectFileExtension(iId);
		}
		createProject(projectName, iId, iUnit, fileType, fileName);
		addContents();
		getThisMenuBar().addSettings();
		updateWorld();
		if(database==null) {
			String resources = System.getProperty("java.history.path");
			String studyIdFileName = FilenameUtils.concat(resources, "study_ids.csv");
			database = new StudyIdDatabase(this, studyIdFileName);
		}
	}

	public boolean openProject(String projectFileName)	{
		ProjectSig project = new ProjectSig(projectFileName);

		loadProject(projectFileName, project.getIId(), project.getIUnit(), project.getFileType(),
				project.getFolderName());

		menuBar.addRecentFile(projectFileName);
		//getProjectPreference().setDataDirectory(projectFileName);
		savePreferences();
		setTitle(projectFileName);

		return true;
	}

	public boolean saveProject(String selectedFileName)	{
		project.setProjectName(selectedFileName);
		project.write(selectedFileName);
		menuBar.addRecentFile(selectedFileName);
		//getProjectPreference().setDataDirectory(selectedFileName);
		savePreferences();
		setTitle(selectedFileName);
		fadeOut();
		return true;
	}

	public void addContents() {
		StudyIdPanel zfmPanel = new StudyIdPanel(this, getProject().getFolderName());
		rtbCardPanel.addCard(zfmPanel, RtbCardPanel.Key.TABLEPANEL);
		rtbCardPanel.showCard(RtbCardPanel.Key.TABLEPANEL);
	}
	public void updateWorld() {
		revalidate();
	}
	public void loadFileToTabel(String fileName) { 
		appendLine("Load file: "+fileName);
		getProject().readCsvFile(fileName);
		getCsvTable().setCsvModel(false, getProject().getHeader(), getProject().getRows());
		updateWorld();
		//LoadFileTask loadFileTask = new LoadFileTask(this, fileName);
		//loadFileTask.execute();
	}
	
	private int [] parseDateColIndex(String indexString) {
		String[] s = indexString.split("\\s*,\\s*");
		int [] index = new int[s.length];
		for(int i=0; i<index.length; i++) {
			index[i] = Integer.parseInt(s[i].trim());
		}
		return index;
	}
	
	public void process(int rnLength, int nameColIndex, int genderColIndex, int dobColIndex) {
		List<String []> rows = getProject().getRows();
		if(rows==null || rows.size()==0) {
			appendLine("No input data.");
			return;
		}
		
		int numOfColumn = rows.get(0).length;
		if(numOfColumn==2) {
			appendLine("Start subject ID generation with project # column index: "+ nameColIndex +
					", local subject identifier column index: "+ genderColIndex 
					);
		} else {
			appendLine("Start subject ID generation with name column index: "+ nameColIndex +
					", gender column index: "+ genderColIndex +
					", date of birth column index: "+ dobColIndex
					);
		}
		boolean [] isChanged = new boolean[rows.size()];
		for(int i=0; i<isChanged.length; i++) {
			isChanged[i] = false;
		}
		StudyIdGenerator generator = new StudyIdGenerator("  ", "  ", rnLength);

		ArrayList<String []> list = new ArrayList<>();
		int k = -1;
		String gender = "U";
		String dob = "01/01/1900";
		if(numOfColumn==2) {
			generator.setMode(2);
			for(String [] r: rows) {
				k++;
				String name = r[genderColIndex-1]+r[nameColIndex-1];
				String studyId = "NULL";
				try {
					studyId = generator.generateStudyId(name, gender, dob);
					
				} catch (NullPointerException e) {
					appendLine(1, "Subject ID generation failed for row : " +k +
							" "+r[nameColIndex-1]+", "+r[genderColIndex-1]);
					isChanged[k] = true;
				} 
				String [] record = database.check(new String[] {r[nameColIndex-1], r[genderColIndex-1],studyId});
				list.add(record);
			}
		} else {
			for(String [] r: rows) {
				k++;
				String name = r[nameColIndex-1];
				gender = r[genderColIndex-1];
				dob = r[dobColIndex-1];
				String studyId = "NULL";
				try {
					//String formatDob = dateShift.format(dob.trim());
					String formatDob = dob.trim();
					studyId = generator.generateStudyId(name, gender, formatDob);
				} catch (NullPointerException e) {
					appendLine(1, "Subject ID generation failed for row : " +k +
							" "+name+", "+gender+", "+dob);
					isChanged[k] = true;
				} 
				list.add(new String[] {name, gender,dob,studyId});
			}
		}
		

		String [] header = getProject().getHeader();
		String [] columnName = new String[header.length+1];
		for(int i=0; i<header.length; i++) {
			columnName[i] = header[i];
		}
		columnName[header.length] = "subject_id";
		
		getCsvTable().setCsvModel(false, columnName, list);
		for(int i=0; i<isChanged.length; i++) {
			getCsvTable().setIsChanged(i, isChanged[i]);
		}
		getCsvTable().repaint();
		
		String info = null;
		Path filePath = Paths.get(getProject().getResultsCwd("_studyIdResults"), 
				FilenameUtils.getBaseName(getProject().getFileName())+"_studyIdAppended.csv");
		info = exportTableToCsv(filePath.toString());
		updateWorld();
		appendLine(info);
		if(numOfColumn==2) {
			database.write();
		}
	}


	public String toLineString(String [] s) {
		StringBuilder sb = new StringBuilder(s[0]);
		for(int i=1; i<s.length; i++) {
			sb.append(","+s[i]);
		}
		return sb.toString();
	}

	@Override
	public void fileSelected(FileMenuEvent e) {
		try {
			openProject(e.file().getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}



}

