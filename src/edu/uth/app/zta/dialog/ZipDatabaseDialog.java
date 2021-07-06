package edu.uth.app.zta.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.uth.app.common.CommonDialog;
import edu.uth.app.zta.ZtaFrame;

public class ZipDatabaseDialog extends CommonDialog {
	private ZtaFrame 	frame 		= null;
	public JTextField 	zipcodeTF 	= null; 
	private int 	zipcode			= 77001;
	private ZipDatabase zipDatabase = null;
	protected JTextArea textArea = null;
	private boolean isListening = true;

	public ZipDatabaseDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		setEnableOKButton(false);
		setDialogWindowSize(800, 800);
		this.frame 		= (ZtaFrame)aParent;
		this.zipDatabase = frame.getZipDatabase();
		textArea = new JTextArea("Enter fetch population module @ ");
		appendCurrentTime();
	}

	public ZtaFrame getFrame() {
		return frame;
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
	
	protected JPanel createContents() {
		JPanel innerPanel = new JPanel(new BorderLayout());
		
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
		gbc= new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		modulePanel.add(new JLabel("Zip code to fetch:"), gbc);
		zipcodeTF 	= new JTextField(zipcode+"");
		browserButton = new JButton("Fetch");
		browserButton.addActionListener(e -> {
			okAction();
		});
		gbc= new GridBagConstraints(2, 0, 3, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		modulePanel.add(zipcodeTF, gbc);
		gbc= new GridBagConstraints(5, 0, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		modulePanel.add(browserButton, gbc);
		
		modulePanel.setBorder(BorderFactory.createTitledBorder(null, "Fetch Population:",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, myFont, myColor));
		gbc= new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
		innerPanel.add(modulePanel, BorderLayout.NORTH);
		
		innerPanel.add(genTextAreaScrollPane(), BorderLayout.CENTER);
		return innerPanel;
	}
	
	public void smallThreeDigitZipCode(int threshold) {
		String [] zs = new String[] {
				"036", "059", "102", "203", "205", 
				"369", "556", "692", "821", "823",
				"878", "879", "884", "893"
		};
	}
	
	protected boolean okAction() {
		String zipcode = zipcodeTF.getText().trim();
		zipDatabase.getPopulation(zipcode);
		
		appendLine("zip code "+zipcode + " has the population of "+ zipDatabase.getPopulation(zipcode));
		if(zipcode.length()==4) {
			List<Zip> rows = zipDatabase.startsWith(zipcode, zipDatabase.getZip5());
			for(Zip z: rows) {
				appendLine(1, z.toString());
			}
		} else if(zipcode.length()==3) {
			List<Zip> rows = zipDatabase.startsWith(zipcode, zipDatabase.getZip4());
			for(Zip z: rows) {
				appendLine(1, z.toString());
				List<Zip> rows2 = zipDatabase.startsWith(z.getZipcode(), zipDatabase.getZip5());
				for(Zip z2: rows2) {
					appendLine(2, z2.toString());
				}
			}
		}
		return true;
	}
	
	private void findSmallThreeDigit() {
		int threshold = 20000;
		appendLine("threshold = "+threshold);
		int k = 0;
		System.out.println("3 digit:");
		List<Zip> rows = zipDatabase.smallPopulationZipCode(threshold, zipDatabase.getZip3());
		for(Zip z: rows) {
			k++;
			//System.out.println(k+" "+ z.toString());
			listDetails(z);
		}
		
		k = 0;
		System.out.println("2 digit:");
		rows = zipDatabase.smallPopulationZipCode(threshold, zipDatabase.getZip2());
		for(Zip z: rows) {
			k++;
			System.out.println(k+" "+ z.toString());
		}
	}
	
	private void listDetails(Zip zz) {
		appendLine("zip code "+zz.getZipcode() + " has the population of "+ zipDatabase.getPopulation(zz.getZipcode()));
		List<Zip> rows = zipDatabase.startsWith(zz.getZipcode(), zipDatabase.getZip4());
		for(Zip z: rows) {
			appendLine(1, z.toString());
			List<Zip> rows2 = zipDatabase.startsWith(z.getZipcode(), zipDatabase.getZip5());
			for(Zip z2: rows2) {
				appendLine(2, z2.toString());
			}
		}
	}
	
	public List<Zip> startsWith(String zipcode, List<Zip> rows) {
		List<Zip> list = new ArrayList<>();
		for(Zip z: rows) {
			if(z.getZipcode().startsWith(zipcode)) {
				list.add(z);
			}
		}
		return list;
	}
	
	protected JScrollPane genTextAreaScrollPane() {
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				isListening = true;
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		scrollPane.setColumnHeaderView(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setWheelScrollingEnabled(true);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(isListening) {
					e.getAdjustable().setValue(e.getAdjustable().getMaximum());
					isListening = false;
				}
			}
		});

		return scrollPane;
	}
	

	private class LoadFilePanel extends JPanel {
		public LoadFilePanel() {
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
			gbc= new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(new JLabel("Zip code to fetch:"), gbc);
			zipcodeTF 	= new JTextField(zipcode+"");
			browserButton = new JButton("Fetch");
			browserButton.addActionListener(e -> {
				
			});
			gbc= new GridBagConstraints(0, 2, 3, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(zipcodeTF, gbc);
			gbc= new GridBagConstraints(0, 5, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
			modulePanel.add(browserButton, gbc);
		}

	}

}
