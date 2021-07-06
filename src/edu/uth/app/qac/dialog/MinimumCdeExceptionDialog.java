package edu.uth.app.qac.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import edu.uth.app.common.CommonDialog;
import edu.uth.app.component.HtmlTooltip;
import edu.uth.app.qac.QacFrame;
import edu.uth.radx.model.Dictionary;
import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.Question;

public class MinimumCdeExceptionDialog extends CommonDialog {
	private QacFrame frame = null;
	public JTextField dataDictionaryTF = null;
	public JTextField inputTF = null;
	private int velFileType = 0; // 0 csv
	public int iUnit = 1; // 0 ft; 1 m 2 km
	public int iId = 30;
	private int foaType = 0;
	private int foaProjectType = 0;

	private Dictionary dictionary;

	public MinimumCdeExceptionDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		setOkText("Save & Apply Waiver");
		setDialogWindowSize(600, 1000);
		this.frame = (QacFrame) aParent;
		dictionary = frame.getDatabase().getDictionary();
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
		return true;
	}

	private class LoadFilePanel extends JPanel {
		public LoadFilePanel(String folderName) {
			setLayout(new GridBagLayout());
			Insets insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gbc;

			Font myFont = new Font("SansSerif", Font.PLAIN, 12);
			Color myColor = Color.BLUE;

			Concept[] concepts = dictionary.getConcepts1();
			for (int i = 0; i < concepts.length; i++) {
				Concept concept = concepts[i];
				String conceptName = concept.getName();

				JPanel modulePanel = new JPanel(new GridBagLayout());

				Question[] questions = concept.getQuestions();
				for (int j = 0; j < questions.length; j++) {
					Question question = questions[j];

					JCheckBox checkBox = new JCheckBox("waiver", question.getWaived());
					checkBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							JCheckBox box = (JCheckBox) event.getSource();
							question.setWaived(box.isSelected());
						}
					});

					String label = HtmlTooltip.toString(question.getFieldLabel());
					gbc = new GridBagConstraints(0, j, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
					modulePanel.add(new JLabel(label), gbc);

					gbc = new GridBagConstraints(5, j, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0);
					modulePanel.add(checkBox, gbc);
				}

				modulePanel.setBorder(BorderFactory.createTitledBorder(null, conceptName,
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, myFont, myColor));
				gbc = new GridBagConstraints(0, i, 5, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
				add(modulePanel, gbc);
			}
		}
	}

}