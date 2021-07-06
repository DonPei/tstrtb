package edu.uth.app.zta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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

import edu.uth.app.component.FileMenuEvent;
import edu.uth.app.component.FileMenuListener;
import edu.uth.app.component.StatusBar;
import edu.uth.app.zta.dialog.ZipDatabase;
import edu.uth.kit.project.Project;
import edu.uth.kit.project.ProjectPreference;
import edu.uth.kit.project.ProjectZta;


public class ZtaFrame extends ZtaFrameBase implements FileMenuListener, KeyListener {
	private static final long serialVersionUID 		= 1L;

	public ZtaFrame(int exitCode, String title, int appId) {
		super(title, appId);
		this.exitCode 	= exitCode;

		projectPreference = new ProjectPreference(getProjectFileExtension());
		projectPreference.readPreferences();

		project = new ProjectZta(System.getProperty("user.dir")+File.separator+"untitled."+
		getProjectFileExtension());

		menuBar = new ZtaMenuBar(this);
		ArrayList<String> list = projectPreference.getRecentFileList();
		if( list != null ) {
			for( int i = list.size()-1; i >= 0; i-- )  menuBar.addRecentFile(list.get(i));
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
		project = new ProjectZta(projectFileName, iId, iUnit, velFileType, velFileName);

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
		appendLine("\nLoading file...");
			if(appId==0) {
				String resources = System.getProperty("java.resources.path");
				String basePath = FilenameUtils.concat(resources, "census");
				zipDatabase = new ZipDatabase(basePath);
			} else if(appId==10) {
				dateShift = new DateShift(this);
			}
			
			appendLine("Sucessfully loaded file.");
			addContents();
			getThisMenuBar().addSettings(appId);
			updateWorld();
		
		//LoadFileTask loadFileTask = new LoadFileTask(this, null);
		//loadFileTask.execute();
	}

	public boolean openProject(String projectFileName)	{
		ProjectZta project = new ProjectZta(projectFileName);

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
		ZtaPanel zfmPanel = new ZtaPanel(this, getProject().getFolderName());
		rtbCardPanel.addCard(zfmPanel, RtbCardPanel.Key.TABLEPANEL);
		rtbCardPanel.showCard(RtbCardPanel.Key.TABLEPANEL);
	}
	public void updateWorld() {
		if(getProject()!=null) {
		}
		revalidate();
	}
	public void loadFileToTabel(String fileName) { 
		appendLine("Load file: "+fileName);
		getProject().readCsvFile(fileName);
		getCsvTable().setCsvModel(true, getProject().getHeader(), getProject().getRows());
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
	public void process(String threshold, int zipcodeColIndex, int appId) {
		List<String []> rows = getProject().getRows();
		if(rows==null) {
			return;
		}
		
		if(appId==0) {
			boolean changed = false;
			appendLine("Start zipcode manipulation with zipcode column index: "+ zipcodeColIndex);
			zipDatabase.setThreshold(Integer.parseInt(threshold));
			int k = -1;
			for(String [] r: rows) {
				k++;
				String zipcode0 = r[zipcodeColIndex-1];
				String zipcode1 = zipDatabase.calQualifiedZipcode(zipcode0);
				if(zipcode1==null) {
					appendLine(1, "zip code does not exist: " +" "+ zipcode0);
					continue;
				}
				if(!(zipcode0.equals(zipcode1))) {
					changed = true;
					String zipcode2 = zipcode1;
					int len = zipcode2.length();
					for(int i=len; i<5; i++) {
						zipcode2 += "Z";
					}
					appendLine(1, "zip code "+zipcode0 + " has the population of "+
					zipDatabase.getPopulation(zipcode0) +" is truncated into "+zipcode2);
					getCsvTable().setIsChanged(k, true);
					getCsvTable().getCsvModel().setValueAt(zipcode2, k, zipcodeColIndex);
				} 
			}
			String info = null;
			if(changed) {
				getCsvTable().repaint();
				Path filePath = Paths.get(getProject().getResultsCwd("_zipcodeResults"), 
						FilenameUtils.getBaseName(getProject().getFileName())+"_zipcodeModified.csv");
				info = exportTableToCsv(filePath.toString());
				updateWorld();
			} else {
				Path filePath = Paths.get(getProject().getResultsCwd("_zipcodeResults"), 
						FilenameUtils.getBaseName(getProject().getFileName())+".csv");
				info = exportTableToCsv(filePath.toString());
			}
			appendLine(info);
		} else if(appId==10) {
			appendLine("Start date shift with subject ID column "+zipcodeColIndex+ "and date column "+threshold);
			int [] dateColIndex = parseDateColIndex(threshold);
			if(dateColIndex==null) {
				appendLine(1, "date column is none ");
				return;
			}
			dateShift.setSubjectIdColIndex(zipcodeColIndex-1);
			dateShift.addToMap(rows);
			
			int k = -1;
			for(String [] r: rows) {
				k++;
				String key = r[zipcodeColIndex-1];
				for(int j=0; j<dateColIndex.length; j++) {
					String dateString = dateShift.shift(key, r[dateColIndex[j]-1]);
					getCsvTable().getCsvModel().setValueAt(dateString, k, dateColIndex[j]);
				}
			}
			dateShift.writeMap();
			getCsvTable().repaint();
			
			Path filePath = Paths.get(getProject().getResultsCwd("_dateResults"), 
					FilenameUtils.getBaseName(getProject().getFileName())+"_dateModified.csv");
			String info = exportTableToCsv(filePath.toString());
			updateWorld();
			appendLine(info);
		}
	}
	
	private void adminFunction(int iMethod, List<String []> rows, int zipcodeColIndex) {
		if(iMethod==0) {
			String [] patientID = genRandomDigitString(rows.size(), 1000000000);
			String [] zipCode = zipDatabase.sampleZipCode(rows.size(), 5);
			for(int i=0; i<rows.size(); i++) {
				rows.get(i)[0] = patientID[i];
				rows.get(i)[zipcodeColIndex-1] = zipCode[i];
			}
		} else if (iMethod==1) {
			String [] header = null;
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

