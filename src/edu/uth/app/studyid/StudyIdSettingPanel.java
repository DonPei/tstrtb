package edu.uth.app.studyid;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.uth.resources.GhcImageLoader;

import edu.uth.app.common.RtbCardPanel;

public class StudyIdSettingPanel extends JPanel {

	private StudyIdFrame frame;
	private int mode;
	private JTextField 	nameColIndexTF 	= null;
	private JTextField 	genderColIndexTF 	= null;
	private JTextField 	dobColIndexTF 	= null;
	private JTextField 	rnLengthTF 	= null;
	private int nameColIndex = 1;
	private int genderColIndex = 1;
	private int dobColIndex = 1;
	private int rnLength = 10;

	public StudyIdSettingPanel(StudyIdFrame frame, int mode) {
		this.frame = frame;
		this.mode = mode;
		nameColIndex = 1;
		genderColIndex = 2;
		dobColIndex = 3;

		setLayout(new GridBagLayout());
		Insets insets = new Insets(1, 0, 1, 0);
		GridBagConstraints gbc;

		Dimension dBt;
		JButton jButton;
		int iCol = 0;

		JTextField textField = null;
		dBt = new Dimension(35,30);
		textField = new JTextField(nameColIndex+"");
		textField.setToolTipText("first name + space + last name");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		nameColIndexTF = textField;

		textField = new JTextField(genderColIndex+"");
		textField.setToolTipText("F-female, M-male, U-unknown, and O-others");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		genderColIndexTF = textField;

		textField = new JTextField(dobColIndex+"");
		textField.setToolTipText("mm/dd/yyyy");
		textField.setMinimumSize(dBt);
		textField.setPreferredSize(dBt);
		textField.setMaximumSize(dBt);
		dobColIndexTF = textField;

		final DefaultComboBoxModel rnLengthName = new DefaultComboBoxModel();
		rnLengthName.addElement("Random # Length");
		rnLengthName.addElement("6");
		rnLengthName.addElement("7");
		rnLengthName.addElement("8");
		rnLengthName.addElement("9");
		rnLengthName.addElement("10");

		final JComboBox rnLengthCombo = new JComboBox(rnLengthName);    
		rnLengthCombo.setSelectedIndex(3);
		rnLengthCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = rnLengthCombo.getSelectedIndex();
				if(index==0) {
					rnLength = 8;
					rnLengthCombo.setSelectedIndex(3);
				} else {
					rnLength = Integer.parseInt((String)rnLengthCombo.getSelectedItem());
				}
			}
		});

		jButton = new JButton("Run", GhcImageLoader.getImageIcon(GhcImageLoader.RUN));
		jButton.setToolTipText("generate study id");
		jButton.setMargin(new Insets(0,0,0,0));
		jButton.setBorder(null);
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);
		dBt = new Dimension(80,30); //Sets the size of the button in the  JMenuBar
		jButton.setMinimumSize(dBt);
		jButton.setPreferredSize(dBt);
		jButton.setMaximumSize(dBt);
		jButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(frame.getProject()==null) return;
				frame.process(rnLength, Integer.parseInt(nameColIndexTF.getText().trim()),
						Integer.parseInt(genderColIndexTF.getText().trim()),
						Integer.parseInt(dobColIndexTF.getText().trim()));
				StudyIdPanel panel = (StudyIdPanel)(frame.getRtbCardPanel().getCard(RtbCardPanel.Key.TABLEPANEL));
				panel.refreshTree();
			}
		});

		//gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		//add(rnLengthCombo, gbc);
		
		//gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		//add(new JLabel("    column index:"), gbc);
		
		if(mode==2) {
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel(" Study Number"), gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(nameColIndexTF, gbc);

			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel(" Subject Local Identifier"), gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(genderColIndexTF, gbc);
		} else if(mode==3) {
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel(" Name"), gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(nameColIndexTF, gbc);

			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel(" Gender"), gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(genderColIndexTF, gbc);

			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel(" Date of Birth"), gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(dobColIndexTF, gbc);
		}

		jButton = new JButton("Refresh", GhcImageLoader.getImageIcon(GhcImageLoader.REFRESH));
		jButton.setToolTipText("Refresh table");
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);
		jButton.setMargin(new Insets(0,0,0,0));
		jButton.setBorder(null);
		dBt = new Dimension(100,30); 
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
	
	public void getInput() {
		
		
	}

}
