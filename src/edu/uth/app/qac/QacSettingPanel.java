package edu.uth.app.qac;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uth.resources.GhcImageLoader;

import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.qac.dialog.MetaFileDialog;
import edu.uth.app.qac.dialog.MinimumCdeExceptionDialog;

public class QacSettingPanel extends JPanel {

	private QacFrame frame;
	private int mode; //0 - dictionary 1 - data

	private JCheckBox [] checkListBox;
	private boolean [] checkListEnabled;

	public JTextField 	dataDictionaryTF = null;
	public JTextField 	metaTF = null;
	private JTextField 	variableColIndexTF 	= null;
	private JTextField 	fieldLabelColIndexTF 	= null;
	private JTextField 	fieldTypeColIndexTF 	= null;
	private JTextField 	choiceColIndexTF 	= null;

	private int variableColIndex = 1;
	private int fieldTypeColIndex = 4;
	private int fieldLabelColIndex = 5;
	private int choiceColIndex = 6;
	
	private int sectionHeaderColIndex = 3;

	public QacSettingPanel(QacFrame frame, int mode) {
		this.frame = frame;
		this.mode = mode;

		setLayout(new GridBagLayout());
		Insets insets = new Insets(1, 0, 1, 0);
		GridBagConstraints gbc;

		Dimension dBt = null;
		JButton jButton = null;

		dBt = new Dimension(950,30); 
		setMinimumSize(dBt);
		setPreferredSize(dBt);
		setMaximumSize(dBt);
		
		JTextField textField = null;
		dBt = new Dimension(50,30);
		textField = new JTextField(variableColIndex+"");
		textField.setToolTipText("Variable / Field Name");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		variableColIndexTF = textField;

		textField = new JTextField(fieldLabelColIndex+"");
		textField.setToolTipText("Field Label");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		fieldLabelColIndexTF = textField;

		textField = new JTextField(fieldTypeColIndex+"");
		textField.setToolTipText("Field Type");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		fieldTypeColIndexTF = textField;

		textField = new JTextField(choiceColIndex+"");
		textField.setToolTipText("Choices, Calculations, OR Slider Labels");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		choiceColIndexTF = textField;

		int iCol = 0;

		String verifyLabel = null;
		String verifyTooltips = null;
		if(mode==2) {
			verifyLabel = "Verify Dictionary";
			verifyTooltips = "verify files using data dictionary rules";
			
			Font myFont = new Font("SansSerif", Font.PLAIN, 12);
			Color myColor = Color.BLUE;
			JPanel modulePanel = new JPanel( new GridBagLayout());

			int jCol = 0;
			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(new JLabel("  Column Index:   ", JLabel.LEFT), gbc);
			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(new JLabel("      Variable Name  ", JLabel.RIGHT), gbc);
			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(variableColIndexTF, gbc);

			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(new JLabel("      Field Type  ", JLabel.RIGHT), gbc);
			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(fieldTypeColIndexTF, gbc);

			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(new JLabel("      Field Label  ", JLabel.LEFT), gbc);
			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(fieldLabelColIndexTF, gbc);

			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(new JLabel("      Choices  ", JLabel.RIGHT), gbc);
			gbc= new GridBagConstraints(jCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(choiceColIndexTF, gbc);

			modulePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(myColor)));

			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(modulePanel, gbc);

			jButton = new JButton("Waiver");
			jButton.setToolTipText("waive some of minimum CDE");
			dBt = new Dimension(60,30); //Sets the size of the button in the  JMenuBar
			jButton.setMargin(new Insets(0,0,0,0));
			jButton.setBorder(null);
			jButton.setMinimumSize(dBt);
			jButton.setPreferredSize(dBt);
			jButton.setMaximumSize(dBt);
			jButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if(frame.getProject()==null) return;
					MinimumCdeExceptionDialog dialog = new MinimumCdeExceptionDialog(frame, "Minimum CDE waiver form", false);
					dialog.showDialog();					
				}
			});
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			//add(jButton, gbc);

		} else if(mode==3) {
			verifyLabel = "Verify Data";
			verifyTooltips = "verify data files using loaded data dictionary file";
			dataDictionaryTF = new JTextField (frame.getProject().getDataDictionaryFileName());
//			dBt = new Dimension(80,30); //Sets the size of the button in the  JMenuBar
//			dataDictionaryTF.setMinimumSize(dBt);
//			dataDictionaryTF.setPreferredSize(dBt);
//			dataDictionaryTF.setMaximumSize(dBt);
			
			jButton = new JButton("Load Dictionary");
			jButton.setToolTipText("Select data dictionary file for this project");
			jButton.setMargin(new Insets(0,0,0,0));
			jButton.setBorder(null);
			dBt = new Dimension(100,30); //Sets the size of the button in the  JMenuBar
			jButton.setMinimumSize(dBt);
			jButton.setPreferredSize(dBt);
			jButton.setMaximumSize(dBt);
			jButton.addActionListener(e -> {
				FileNameExtensionFilter [] exts = null;
				exts = new FileNameExtensionFilter [] { new FileNameExtensionFilter("CSV (*.csv)", "csv") }; 
				String fileName = dataDictionaryTF.getText().trim();
				String selectedFileName = frame.openFileUsingJFileChooser(exts, fileName);

				if(selectedFileName==null) return;
				else { dataDictionaryTF.setText(selectedFileName.trim()); }
			});

			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(dataDictionaryTF, gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, insets, 0, 0);
			add(jButton, gbc);
		} else if(mode==1) {
			verifyLabel = "Verify Meta file";
			verifyTooltips = "verify files using meta file rules";
//			metaTF = new JTextField (frame.getProject().getMetaFileName());
//			jButton = new JButton("Load Meta File to Edit");
//			jButton.setToolTipText("Select meta file to edit for this project");
//			jButton.setMargin(new Insets(0,0,0,0));
//			jButton.setBorder(null);
//			dBt = new Dimension(140,30); //Sets the size of the button in the  JMenuBar
//			jButton.setMinimumSize(dBt);
//			jButton.setPreferredSize(dBt);
//			jButton.setMaximumSize(dBt);
//			jButton.addActionListener(e -> {
//				FileNameExtensionFilter [] exts = null;
//				exts = new FileNameExtensionFilter [] { new FileNameExtensionFilter("CSV (*.csv)", "csv") }; 
//				String fileName = metaTF.getText().trim();
//				String selectedFileName = frame.openFileUsingJFileChooser(exts, fileName);
//
//				if(selectedFileName==null) return;
//				else { metaTF.setText(selectedFileName.trim()); }
//			});
//
//			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
//			add(metaTF, gbc);
//			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, insets, 0, 0);
//			add(jButton, gbc);
//			
			jButton = new JButton("Create a Meta File");
			jButton.setToolTipText("create a new meta file for this project");
			jButton.setMargin(new Insets(0,0,0,0));
			jButton.setBorder(null);
			dBt = new Dimension(120,30); //Sets the size of the button in the  JMenuBar
			jButton.setMinimumSize(dBt);
			jButton.setPreferredSize(dBt);
			jButton.setMaximumSize(dBt);
			jButton.addActionListener(e -> {
				if(frame.getProject()==null) return;
				MetaFileDialog dialog = new MetaFileDialog(frame, "Create a Meta File", false);
				dialog.showDialog();
			});

			
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, insets, 0, 0);
			//add(jButton, gbc);
		}

		jButton = new JButton(verifyLabel, GhcImageLoader.getImageIcon(GhcImageLoader.RUN));
		jButton.setToolTipText(verifyTooltips);
		dBt = new Dimension(140,30); //Sets the size of the button in the  JMenuBar
		jButton.setMargin(new Insets(0,0,0,0));
		jButton.setBorder(null);
		jButton.setMinimumSize(dBt);
		jButton.setPreferredSize(dBt);
		jButton.setMaximumSize(dBt);
		jButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(frame.getProject()==null) return;
				if(mode==2) {
					getInput();
					int [] checkListColIndex = new int[] {
							variableColIndex, fieldTypeColIndex, fieldLabelColIndex, choiceColIndex
					};
					frame.process(mode, checkListColIndex, null);
					QacPanel panel = (QacPanel)(frame.getRtbCardPanel().getCard(RtbCardPanel.Key.TABLEPANEL));
					panel.refreshTree();
				} else if(mode==3) {
					String inputDictionaryFileName = dataDictionaryTF.getText().trim();
					frame.process(mode, null, inputDictionaryFileName);
				} else if(mode==1) {
					frame.process(mode, null, null);
					QacPanel panel = (QacPanel)(frame.getRtbCardPanel().getCard(RtbCardPanel.Key.TABLEPANEL));
					panel.refreshTree();
				}

			}
		});
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 0.01, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);

		jButton = new JButton(GhcImageLoader.getImageIcon(GhcImageLoader.REFRESH));
		jButton.setToolTipText("Refresh table");
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);
		dBt = new Dimension(30,30); 
		jButton.setMinimumSize(dBt);
		jButton.setPreferredSize(dBt);
		jButton.setMaximumSize(dBt);
		jButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(frame.getProject()==null) return;
				if(frame.getCsvTable()!=null) {
					frame.getCsvTable().toggleTableView();
				}
			}
		});

		if(mode==1) {
			dBt = new Dimension(400,30);
			JLabel glue = new JLabel("  ", JLabel.LEFT);
			glue.setMinimumSize(dBt);
			glue.setPreferredSize(dBt);
			glue.setMaximumSize(dBt);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(glue, gbc);
		}
	}

	public void getInput() {
		variableColIndex = Integer.parseInt(variableColIndexTF.getText().trim());
		fieldLabelColIndex = Integer.parseInt(fieldLabelColIndexTF.getText().trim());
		fieldTypeColIndex = Integer.parseInt(fieldTypeColIndexTF.getText().trim());
		choiceColIndex = Integer.parseInt(choiceColIndexTF.getText().trim());
	}


	private void getGUI() {

		setLayout(new GridBagLayout());
		Insets insets = new Insets(1, 0, 1, 0);
		GridBagConstraints gbc;

		Dimension dBt;
		JButton jButton;
		int iCol = 0;
		//			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		//			add(new JLabel(" minimum CDE:"), gbc);


		String [] checkListName = new String[] {
				"Study_ID element", "12 minimum CDE elements", "47 minimum CDE sub-elements", "field type of minimum CDE" 
		};
		String [] checkListToolTipText = new String[] {
				"First variable should be \"Study_ID\"",
				"12 minimum CDE elements should exist in column \"Variable Classification\"", 
				"47 minimum CDE sub-elements should exist in column \"Field Label/Variable Description\"", 
				"field type of minimum CDE should be correct in column \"Field Type\""	
		};

		int n = checkListName.length;
		checkListBox = new JCheckBox[n];
		checkListEnabled = new boolean[n];
		for(int i=0; i<n; i++) {
			checkListEnabled[i] = true;
		}
		for(int i=0; i<n; i++) {
			final int j = i;
			checkListBox[i] = new JCheckBox(checkListName[i], checkListEnabled[i]); 
			checkListBox[i].setToolTipText(checkListToolTipText[i]);
			checkListBox[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox checkBox = (JCheckBox)e.getSource();
					checkListEnabled[j] = checkBox.isSelected();
				}
			});
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(checkListBox[i], gbc);
		}

		jButton = new JButton("Verify", GhcImageLoader.getImageIcon(GhcImageLoader.RUN));
		jButton.setToolTipText("Verify the selected file");
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);
		dBt = new Dimension(80,30); //Sets the size of the button in the  JMenuBar
		jButton.setMinimumSize(dBt);
		jButton.setPreferredSize(dBt);
		jButton.setMaximumSize(dBt);
		jButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(frame.getProject()==null) return;
				//frame.process(checkListName, checkListEnabled);
				QacPanel panel = (QacPanel)(frame.getRtbCardPanel().getCard(RtbCardPanel.Key.TABLEPANEL));
				panel.refreshTree();
			}
		});

		jButton = new JButton(GhcImageLoader.getImageIcon(GhcImageLoader.REFRESH));
		jButton.setToolTipText("Refresh table");
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);
		dBt = new Dimension(30,30); 
		jButton.setMinimumSize(dBt);
		jButton.setPreferredSize(dBt);
		jButton.setMaximumSize(dBt);
		jButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(frame.getProject()==null) return;
				frame.getCsvTable().toggleTableView();
			}
		});
	}


}
