package edu.uth.app.zta;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.uth.resources.GhcImageLoader;

import edu.uth.app.common.RtbCardPanel;

public class ZtaSettingPanel extends JPanel {

	private ZtaFrame frame;
	private int appId;
	private JTextField 	dateColIndexTF 	= null;
	private JTextField 	primaryColIndexTF 	= null;
	private int primaryColIndex = 1;
	private String dateColIndex = null;

	public ZtaSettingPanel(ZtaFrame frame, int appId) {
		this.frame = frame;
		this.appId = appId;
		if(appId==0) {
			primaryColIndex = 6;
			dateColIndex = "20000";
		} else if(appId==10) {
			primaryColIndex = 1;
			dateColIndex = "7,8";
		}
		
		setLayout(new GridBagLayout());
		Insets insets = new Insets(1, 0, 1, 0);
		GridBagConstraints gbc;
		
		Dimension dBt;
		JButton jButton;
		int iCol = 0;

		dBt = new Dimension(35,30);
		primaryColIndexTF = new JTextField(primaryColIndex+"");
		primaryColIndexTF.setToolTipText("index is inside parentheses of the header");
		primaryColIndexTF.setMinimumSize(dBt);
		primaryColIndexTF.setPreferredSize(dBt);
		primaryColIndexTF.setMaximumSize(dBt);
		

		dBt = new Dimension(100,30);
		dateColIndexTF = new JTextField(dateColIndex);
		dateColIndexTF.setMinimumSize(dBt);
		dateColIndexTF.setPreferredSize(dBt);
		dateColIndexTF.setMaximumSize(dBt);
		
		jButton = new JButton(GhcImageLoader.getImageIcon(GhcImageLoader.RUN));
		jButton.setToolTipText("Run");
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(jButton, gbc);
		dBt = new Dimension(30,30); //Sets the size of the button in the  JMenuBar
		jButton.setMinimumSize(dBt);
		jButton.setPreferredSize(dBt);
		jButton.setMaximumSize(dBt);
		jButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(frame.getProject()==null) return;
				frame.process(dateColIndexTF.getText().trim(), 
						Integer.parseInt(primaryColIndexTF.getText().trim()), appId);
				ZtaPanel panel = (ZtaPanel)(frame.getRtbCardPanel().getCard(RtbCardPanel.Key.TABLEPANEL));
				panel.refreshTree();

			}
		});
		
		
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		if(appId==0) {
			add(new JLabel(" Zipcode column index:"), gbc);
		} else if(appId==10) {
			add(new JLabel(" Subject ID column index:"), gbc);
		}
		gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		add(primaryColIndexTF, gbc);

		if(appId==0) {
			dateColIndexTF.setToolTipText("zipcode whose population is less than the threshold will be changed.");
		} else if(appId==10) {
			dateColIndexTF.setToolTipText("The index list elements are separated by comma.");
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(new JLabel(" Date column index list:"), gbc);
			gbc= new GridBagConstraints(iCol++, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			add(dateColIndexTF, gbc);
		}

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
