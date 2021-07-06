package edu.uth.app.qac.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import edu.uth.app.common.CommonDialog;
import edu.uth.app.common.RtbCardPanel;
import edu.uth.app.component.HtmlTooltip;
import edu.uth.app.qac.LogLevel;
import edu.uth.app.qac.QacFrame;
import edu.uth.app.qac.QacPanel;
import edu.uth.kit.project.ProjectQac;
import edu.uth.radx.model.Dictionary;
import edu.uth.radx.model.Meta;
import edu.uth.radx.model.MinimumCde;
import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.Option;
import edu.uth.radx.model.entities.Question;
import edu.uth.util.IOUtil;
import edu.uth.radx.model.entities.MetaQuestion;

public class MetaFileDialog extends CommonDialog {
	private QacFrame 	frame 			= null;
	public JTextField 	dataDictionaryTF = null;
	public JTextField 	inputTF 			= null;
	private int 		velFileType	= 0; 	// 0 csv
	public int 			iUnit 			= 1;	// 0 ft; 1 m 2 km
	public int 			iId				= 30;	
	private int foaType = 0;
	private int foaProjectType = 0;
	
	private Meta meta;
	public JTextField [] questionTFs = null;
	
	public MetaFileDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		setResizable(false);
		setOkText("Create Meta File");
		setDialogWindowSize(900, 1000);
		this.frame 		= (QacFrame)aParent;
		meta = frame.getDatabase().getMeta();
	}

	protected JScrollPane createContents() {
		LoadFilePanel panel = new LoadFilePanel(System.getProperty("user.dir"));
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setWheelScrollingEnabled(true);
		
		return scrollPane;
	}
	protected boolean okAction() {
		List<MetaQuestion> questions = meta.getQuestions();
		String [] header = meta.getCsvReaderWriter().getHeader();
		StringBuilder sb = new StringBuilder();
		sb.append("\""+header[0]+"\","+"\""+header[2]+"\"");
		sb.append("\n");
		for(int i=0; i<questions.size(); i++) {
			MetaQuestion question = questions.get(i);
			question.setChoice(questionTFs[i].getText().trim());
			sb.append("\""+question.getFieldLabel()+"\","+"\""+questionTFs[i].getText().trim()+"\"");
			sb.append("\n");
		}

		Path filePath = Paths.get(frame.getProject().getResultsCwd("_results"), "meta.csv");
		frame.appendLine("Meta data is successfully created and outputed to: "+filePath.toString());
		String text = sb.toString(); 
		IOUtil.writeTextToFile(text, filePath.toString());

		return true;
	}

	private class LoadFilePanel extends JPanel {
		public LoadFilePanel(String folderName) {
			setLayout(new GridBagLayout());
			Insets insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gbc;

			Font myFont = new Font("SansSerif", Font.PLAIN, 12);
			Color myColor = Color.BLUE;
			Dimension dBt = new Dimension(600,40);
			
			List<MetaQuestion> questions = meta.getQuestions();
			questionTFs = new JTextField[questions.size()];
			for(int i=0; i<questions.size(); i++) {
				MetaQuestion question = questions.get(i);
				
				String fieldLabel = question.getFieldLabel();
				JTextField questionTF = new JTextField(question.getChoice());
				questionTF.setPreferredSize(dBt);
				JPanel modulePanel = new JPanel( new GridBagLayout());
				gbc= new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
				modulePanel.add(questionTF, gbc);
				
				questionTFs[i] = questionTF;
				
				modulePanel.setBorder(BorderFactory.createTitledBorder(null, (i+1)+". "+fieldLabel, 
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, myFont, myColor));
				gbc= new GridBagConstraints(0, i, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
				add(modulePanel, gbc);
			}
		}
	}

}