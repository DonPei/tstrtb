package edu.uth.app.qac;

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
import edu.uth.app.qac.validator.DictionaryValidator;
import edu.uth.app.qac.validator.MetaValidator;
import edu.uth.app.qac.validator.DataValidator;
import edu.uth.kit.project.CsvReaderWriter;
import edu.uth.kit.project.Project;
import edu.uth.kit.project.ProjectPreference;
import edu.uth.kit.project.ProjectQac;
import edu.uth.radx.model.Dictionary;
import edu.uth.radx.model.Foa;
import edu.uth.util.IOUtil;
import nhash.StudyIdGenerator;

public class QacFrame extends QacFrameBase implements FileMenuListener, KeyListener {
	private static final long serialVersionUID 		= 1L;

	public QacFrame(int exitCode, String title, int appId) {
		super(title, appId);
		this.exitCode 	= exitCode;

		projectPreference = new ProjectPreference("qac");
		projectPreference.readPreferences();

		project = new ProjectQac(System.getProperty("user.dir")+File.separator+"untitled.qac");

		menuBar = new QacMenuBar(this);
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

	public void createProject(String projectFileName, int iId, int iUnit, int velFileType, String velFileName, 
			int foaType, int foaProjectType, String dataDictionaryFileName)	{
		project = new ProjectQac(projectFileName, iId, iUnit, velFileType, velFileName, 
				foaType, foaProjectType, dataDictionaryFileName);

		if(projectFileName!=null) {
			for(int i=1; i<menuBar.getMenuCount(); i++) {
				JComponent component = (JComponent)menuBar.getComponent(i);
				component.setEnabled(true);
			}
			menuBar.getJMenuItemOpenFile().setEnabled(true);

			if(!projectFileName.contains("untitled")) {
				menuBar.addRecentFile(project.getProjectFileName());
			}
			setTitle(projectFileName);

		}
	}

	public void loadProject(String projectName, int iId, int iUnit,
			int fileType, String fileName, int foaType, int foaProjectType, String dataDictionaryFileName) {

		String fullPath = FilenameUtils.getFullPath(fileName);
		if (projectName == null) {
			projectName = fullPath + "untitled." + Project.getProjectFileExtension(iId);
		}
		createProject(projectName, iId, iUnit, fileType, fileName, foaType, foaProjectType, dataDictionaryFileName);
		if(database==null) {
			database = new QacDatabase(this);
		}
		addContents();
		getThisMenuBar().addSettings();
		updateWorld();
	}

	public boolean openProject(String projectFileName)	{
		ProjectQac project = new ProjectQac(projectFileName);

		loadProject(projectFileName, project.getIId(), project.getIUnit(), project.getFileType(),
				project.getFolderName(), project.getFoaType(), project.getFoaProjectType(), project.getDataDictionaryFileName());

		menuBar.addRecentFile(projectFileName);
		savePreferences();
		setTitle(projectFileName);

		return true;
	}

	public boolean saveProject(String selectedFileName)	{
		project.setProjectName(selectedFileName);
		project.write(selectedFileName);
		menuBar.addRecentFile(selectedFileName);
		savePreferences();
		setTitle(selectedFileName);
		fadeOut();
		return true;
	}

	public void addContents() {
		QacPanel panel = new QacPanel(this, getProject().getFolderName());
		rtbCardPanel.addCard(panel, RtbCardPanel.Key.TABLEPANEL);
		rtbCardPanel.showCard(RtbCardPanel.Key.TABLEPANEL);
	}
	public void updateWorld() {
		revalidate();
	}
	public void loadFileToTabel(String fileName) { 
		String ext = FilenameUtils.getExtension(fileName);
		if(ext==null || ext.isEmpty()) {
			getProject().setLoadedFileName(null);
			return;
		}
		ext = ext.trim().toLowerCase();
		if("csv".equals(ext)) {
			appendLine("Load file: "+fileName);
			getProject().setLoadedFileName(fileName);
			CsvReaderWriter csvReaderWriter = new CsvReaderWriter();
			csvReaderWriter.read(fileName, true);
			getCsvTable().setCsvModel(true, csvReaderWriter.getHeader(), csvReaderWriter.getRows());
			updateWorld();
		}
	}

	public void process(int mode, int [] checkListColIndex, String inputDictionaryFileName) {
		if(mode==1) {
			MetaValidator metaValidator = new MetaValidator(this, this.getCsvTable());
			metaValidator.validate(null);
		} else if(mode==2) {
			DictionaryValidator dictionaryCdeValidator = new DictionaryValidator(this, this.getCsvTable(), checkListColIndex);
			dictionaryCdeValidator.validate(null);
		} else if(mode==3) {
			DataValidator dataCdeValidator = new DataValidator(this, this.getCsvTable(), inputDictionaryFileName);
			dataCdeValidator.validate(null);
		} 

		String loadedFileName = getProject().getLoadedFileName();
		if(loadedFileName!=null) {
			Path filePath = Paths.get(getProject().getResultsCwd("_results"), 
					FilenameUtils.getBaseName(loadedFileName)+"_results.txt");
			appendLine("Output: "+filePath.toString());
			String text = getColorTextPane().getText(); 
			IOUtil.writeTextToFile(text, filePath.toString());		
			updateWorld();
			
//			filePath = Paths.get(getProject().getResultsCwd("_results"), 
//					FilenameUtils.getBaseName(loadedFileName)+"_data.csv");
//			appendLine("Output: "+filePath.toString());
//			text = getDatabase().getDictionary().genTestData();
//			IOUtil.writeTextToFile(text, filePath.toString());	
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

