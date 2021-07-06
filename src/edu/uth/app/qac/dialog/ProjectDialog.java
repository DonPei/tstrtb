package edu.uth.app.qac.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uth.app.common.CommonDialog;
import edu.uth.app.qac.QacFrame;
import edu.uth.kit.project.ProjectQac;
import edu.uth.swing.ui.UiUtil;

public class ProjectDialog  extends CommonDialog {
	private QacFrame 	frame 			= null;
	public JTextField 	dataDictionaryTF = null;
	public JTextField 	inputTF 			= null;
	private int 		velFileType	= 0; 	// 0 csv
	public int 			iUnit 			= 1;	// 0 ft; 1 m 2 km
	public int 			iId				= 31;	
	private int foaType = 0;
	private int foaProjectType = 0;
	
	public ProjectDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		setOkText("Create Project");
		setDialogWindowSize(1000, 400);
		this.frame 		= (QacFrame)aParent;
	}

	protected JPanel createContents() {
		JPanel innerPanel = new JPanel(new BorderLayout());
		ProjectQac 		prevProject 		= null;
		ArrayList<String> files = frame.getProjectPreference().getRecentFileList();
		if(files!=null&&files.size()>0) {
			String projectName = files.get(0);
			if(projectName!=null) {
				File file = new File(projectName);
				if(file.exists()) {
					prevProject = new ProjectQac(projectName);
				}
			}
		}

		if(prevProject!=null) {
			iUnit = prevProject.getIUnit();
			iId = prevProject.getIId();
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
		
		String dataDictionaryFileName = dataDictionaryTF.getText().trim();
		
		frame.loadProject(null, iId, iUnit, velFileType, inputName, foaType, foaProjectType, dataDictionaryFileName);

		return true;
	}
	
	class ComboBoxRenderer extends DefaultListCellRenderer {
	  public Component getListCellRendererComponent(JList list,Object value,
	                      int index,boolean isSelected,boolean cellHasFocus)
	  {
	    JLabel lbl = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
	    lbl.setText((String)value);
	    lbl.setOpaque(true);
	    
	    if (index % 2 == 0 ) {
	    	lbl.setBackground(new Color(240,240,240));
		} else {
			lbl.setBackground(getBackground());
		}
	    
	    return lbl;
	  }
	}

	private class LoadFilePanel extends JPanel {
		private DefaultComboBoxModel model;
		
		
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
			JPanel modulePanel = new JPanel( new GridBagLayout());
			model = new DefaultComboBoxModel(frame.getDatabase().genProject(foaType));
			JComboBox<String> cb = new JComboBox<>(frame.getDatabase().genFoa());
			//cb.setRenderer(new ComboBoxRenderer());
			cb.setSelectedIndex(foaType);
			cb.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent e) {   
					JComboBox cb = (JComboBox)e.getSource();
					foaType = cb.getSelectedIndex();
					model.removeAllElements();
					String [] names = frame.getDatabase().genProject(foaType);
					for(int i=0; i<names.length; i++) {
						model.addElement(names[i]);
					}
				}  
			});
			int jRow = 0;
			gbc= new GridBagConstraints(0, jRow, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
			modulePanel.add(new JLabel("Select FOA:"), gbc);
			gbc= new GridBagConstraints(1, jRow, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(cb, gbc);
			
			cb = new JComboBox<>(model);
			cb.setSelectedIndex(foaProjectType);
			cb.addActionListener(new ActionListener() {  
				public void actionPerformed(ActionEvent e) {   
					JComboBox cb = (JComboBox)e.getSource();
					foaProjectType = cb.getSelectedIndex();
				}  
			});
			
			jRow++;
			gbc= new GridBagConstraints(0, jRow, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
			modulePanel.add(new JLabel("FOA Project :"), gbc);
			gbc= new GridBagConstraints(1, jRow, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(cb, gbc);
			
			jRow++;
			gbc= new GridBagConstraints(0, jRow, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
			modulePanel.add(new JLabel("Data Dictionary:"), gbc);
			
			dataDictionaryTF = new JTextField (folderName);
			browserButton = new JButton("Browse");
			browserButton.addActionListener(e -> {
				FileNameExtensionFilter [] exts = null;
				exts = new FileNameExtensionFilter [] { new FileNameExtensionFilter("CSV (*.csv)", "csv") }; 
				String fileName = dataDictionaryTF.getText().trim();
				String selectedFileName = frame.openFileUsingJFileChooser(exts, fileName);
				
				if(selectedFileName==null) return;
				else { dataDictionaryTF.setText(selectedFileName.trim()); }
			});
			jRow++;
			gbc= new GridBagConstraints(0, jRow, 5, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(dataDictionaryTF, gbc);
			gbc= new GridBagConstraints(5, jRow, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, insets, 0, 0);
			modulePanel.add(browserButton, gbc);			
			
			
			int iRow = 0;
			modulePanel.setBorder(BorderFactory.createTitledBorder(null, "Data Dictionary", 
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, myFont, myColor));
			iRow++;
			gbc= new GridBagConstraints(0, iRow, 5, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(modulePanel, gbc);
			
			
			iRow++;
			gbc= new GridBagConstraints(0, iRow, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel("Select data folder that contains all data files:"), gbc);

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



