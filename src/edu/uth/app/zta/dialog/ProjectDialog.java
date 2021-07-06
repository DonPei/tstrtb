package edu.uth.app.zta.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uth.app.common.CommonDialog;
import edu.uth.app.zta.ZtaFrame;
import edu.uth.kit.project.ProjectZta;
import edu.uth.swing.ui.UiUtil;

public class ProjectDialog  extends CommonDialog {
	private ZtaFrame 	frame 			= null;
	public JTextField 	inputTF 			= null;
	private int 		velFileType	= 0; 	// 0 csv
	public int 			iUnit 			= 1;	// 0 ft; 1 m 2 km
	public int 			iId				= 0;	
	
	public ProjectDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		setOkText("Create Project");
		setDialogWindowSize(800, 200);
		this.frame 		= (ZtaFrame)aParent;
		iId = frame.getAppId();
	}

	protected JPanel createContents() {
		JPanel innerPanel = new JPanel(new BorderLayout());
		ProjectZta 		prevProject 		= null;
		ArrayList<String> files = frame.getProjectPreference().getRecentFileList();
		if(files!=null&&files.size()>0) {
			String projectName = files.get(0);
			if(projectName!=null) {
				File file = new File(projectName);
				if(file.exists()) {
					prevProject = new ProjectZta(projectName);
				}
			}
		}

		if(prevProject!=null) {
			LoadFilePanel panel = new LoadFilePanel(prevProject.getFolderName());
			innerPanel.add(panel, BorderLayout.CENTER);
			return innerPanel;
		}
		LoadFilePanel panel = new LoadFilePanel(System.getProperty("user.dir"));
		innerPanel.add(panel, BorderLayout.CENTER);
		return innerPanel;
	}

	public static String [] parseFileName(String pckFileName) {
		if(pckFileName==null||pckFileName.isEmpty()) return null;
		StringTokenizer st = new StringTokenizer(pckFileName, ";");
		String [] fileName = new String[st.countTokens()];
		for(int i=0; i<fileName.length; i++) {
			fileName[i] = st.nextToken().trim();
		}
		return fileName;
	}

	protected boolean okAction() {
		boolean inValid = false;
		String inputName = inputTF.getText().trim();
		if(inputName==null || inputName.isEmpty()) inValid = true;
		frame.loadProject(null, iId, iUnit, velFileType, inputName);

		return true;
	}

	private class LoadFilePanel extends JPanel {
		public LoadFilePanel(String folderName) {
			setLayout(new GridBagLayout());
			Insets insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gbc;
			JButton browserButton = null;

			String [] moduleString = null;
			int n = 1;
			ButtonGroup moduleRadioGroup = null;
			JRadioButton [] moduleRadioButton = null;

			Font myFont = new Font("SansSerif", Font.PLAIN, 12);
			Color myColor = Color.BLUE;
			//JPanel modulePanel = new JPanel( new GridBagLayout());

			int iRow = 0;
			gbc= new GridBagConstraints(0, iRow, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel("Select data folder that contains all input csv files:"), gbc);

			inputTF = new JTextField (folderName, 5);
			browserButton = new JButton("Browse");
			browserButton.addActionListener(e -> {
				String fileName = inputTF.getText().trim();

				//FileNameExtensionFilter [] exts = null;
				//if(velFileType==0) { exts = new FileNameExtensionFilter [] { new FileNameExtensionFilter("CSV (*.csv)", "csv") }; }
				//else { exts = new FileNameExtensionFilter [] { new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx") }; }

				//String selectedFileName = frame.openFileUsingJFileChooser(exts, fileName);
				String selectedFileName = frame.openDirectoryUsingJFileChooser(fileName);
				
				if(selectedFileName==null) return;
				else { inputTF.setText(selectedFileName.trim()); }
			});
			iRow++;
			gbc= new GridBagConstraints(0, iRow, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(inputTF, gbc);
			gbc= new GridBagConstraints(4, iRow++, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(browserButton, gbc);
		}

	}

}



