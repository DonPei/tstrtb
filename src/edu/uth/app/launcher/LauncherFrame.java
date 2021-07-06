package edu.uth.app.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import org.apache.commons.io.FilenameUtils;

import edu.mines.jtk.mosaic.IPanel;
import edu.mines.jtk.mosaic.Mosaic;
import edu.uth.app.common.CommonFrame;
import edu.uth.app.component.StatusBar;
import edu.uth.app.qac.QacApp;
import edu.uth.app.studyid.StudyIdApp;
import edu.uth.app.zta.ZtaApp;
import edu.uth.kit.project.ProjectPreference;
import edu.uth.resources.GhcImageLoader;
// import com.ghc.tomo.TomoApp;
import edu.uth.util.GhcStringUtil;

/**
 * LauncherFrame starts a GUI interface. Users can access a collection of independent
 * modules to do daily chores.
 * <p>
 * LauncherFrame will store user input, check license for each modules, and passing a
 * process ID to each module
 * <p>
 * 
 * @author Don Pei, Halliburton
 * @version 2013.12.31
 */
public class LauncherFrame extends CommonFrame {
	public static int processIDZfm = 0;
	public static int processIDStudyId = 0;
	public static int processIDQac = 0;

	private LauncherMenuBar menuBar = null;
	private ZtaApp ztaApp = null;
	private StudyIdApp studyIdApp = null;
	private QacApp qacApp = null;

	private JButton zfmButton = null;
	private JButton dateButton = null;
	private JButton idButton = null;
	private JButton qacButton = null;
	private JLabel rightLabel = null;
	private String zfmString = null;
	private String dateString = null;
	private String idString = null;
	private String qacString = null;

	private int iApp = 1;

	public int nCwd = 3;
	// 0 - vecon3D viewer 1- file type converter 2-Data Conditioning
	public String[] cwd = null;

	private StatusBar statusBar = null;
	private JLabel infoLabel = null;

	public LauncherFrame(int iApp) {
		this.iApp = iApp;
		menuBar = new LauncherMenuBar(this, iApp);
		setJMenuBar(menuBar);
		setLayout(new BorderLayout());

		add(new LauncherPanel(), BorderLayout.CENTER);

		projectPreference = new ProjectPreference("launcher");
		projectPreference.readPreferences();
		ArrayList<String> list = projectPreference.getRecentFileList();
		cwd = new String[nCwd];
		for (int i = 0; i < cwd.length; i++)
			cwd[i] = System.getProperty("user.dir");
		if (list != null) {
			int N = list.size();
			N = N < nCwd ? N : nCwd;
			for (int i = 0; i < N; i++)
				cwd[i] = list.get(i);
		}

		statusBar = new StatusBar();
		statusBar.setZoneBorder(BorderFactory.createLineBorder(Color.GRAY));
		statusBar.setZones(new String[] { "first_zone", "remaining_zones" },
				new Component[] { new JLabel("first"), new JLabel("remaining") },
				new String[] { "30%", "70%" });
		infoLabel = (JLabel) statusBar.getZone("remaining_zones");
		JLabel label = (JLabel) statusBar.getZone("first_zone");
		label.setText("Copyright (C) UTH SBMI 2021-2022");

		add(statusBar, BorderLayout.SOUTH);
		setTitle("RADx-Rad Toolbox " + LauncherApp.VERSION);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				savePreferences();
				exit();
			}
		});
		setResizable(false);
	}

	public void exit() {
		if (ztaApp != null) {
			ztaApp.exit();
		}
		if (studyIdApp != null) {
			studyIdApp.exit();
		}
		if (qacApp != null) {
			qacApp.exit();
		}
		System.exit(0);
	}

	public void savePreferences() {
		if (cwd != null) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < cwd.length; i++)
				list.add(cwd[i]);
			projectPreference.setRecentFileList(list);
			projectPreference.writePreferences();
		}
	}

	public void startApp(JButton jButton) {
		String html = jButton.getText();
		String plain = html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ").trim();
		if (jButton == zfmButton) {
			if (checkLicenseAndRecordResponse()) {
				ztaApp = new ZtaApp(this, 2, 2, processIDZfm++, plain, 0);
			}
		} else if (jButton == dateButton) {
			if (checkLicenseAndRecordResponse()) {
				ztaApp = new ZtaApp(this, 2, 2, processIDZfm++, plain, 10);
			}
		} else if (jButton == idButton) {
			if (checkLicenseAndRecordResponse()) {
				studyIdApp = new StudyIdApp(this, 2, 2, processIDStudyId++, plain, 20);
			}
		} else if (jButton == qacButton) {
			if (checkLicenseAndRecordResponse()) {
				qacApp = new QacApp(this, 2, 2, processIDQac++, plain, 20);
			}
		} else {
			JOptionPane.showMessageDialog(this, "The module will be availabe by the end of this year",
					"Error", JOptionPane.ERROR_MESSAGE);

		}
	}

	private boolean checkLicenseAndRecordResponse() {
		boolean response = checkLicense();
		projectPreference.setDisclaimer(response ? "true" : "false");
		savePreferences();
		return response;
	}

	private boolean checkLicense() {
		// https://clamp.uth.edu/download.php?v=bigsur&email=Donghong.Pei@uth.tmc.edu&id=13334
		String resources = System.getProperty("java.resources.path");
		String basePath = FilenameUtils.concat(resources, "terms");
		String fileName = FilenameUtils.concat(basePath, "disclaimer.txt");
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			String disclaimer = projectPreference.getDisclaimer();
			if (disclaimer.equals("true")) {
				return true;
			} else {
				try {
					String content = readFile(fileName, StandardCharsets.UTF_8);
					JScrollPane scrollPane = genTextAreaScrollPane(content);
					JPanel pane = genDisclaimerPage(scrollPane);
					int res = showDisclaimerDialog(pane);
					return res == 0 ? true : false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	protected JScrollPane genTextAreaScrollPane(String content) {
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(233, 233, 233));
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		textArea.setText(content);
		textArea.setCaretPosition(0);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		scrollPane.setColumnHeaderView(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setWheelScrollingEnabled(true);
		// scrollPane.setMinimumSize(new Dimension(750, 500));
		scrollPane.setPreferredSize(new Dimension(900, 500));

		return scrollPane;
	}

	private JPanel genDisclaimerPage(JScrollPane scrollPane) {
		JPanel panel = new JPanel(new BorderLayout());
		String htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>USER AGREEMENT</b></font>";
		JLabel label = new JLabel(htmlText, null, JLabel.LEFT);
		label.setBackground(Color.white);
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(10, 10, 10, 10));
		// rightLabel.setPreferredSize(new Dimension(500,600));
		// rightLabel.setMinimumSize(new Dimension(500,600));
		panel.add(label, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}

	private int showDisclaimerDialog(final Object message)
			throws HeadlessException {
		String[] options = new String[] {
				"Yes, I accept the agreement",
				"No, I DON'T accept the agreement"
		};

		final JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, options, null);
		final String title = "Disclaimer";
		final JDialog dialog = pane.createDialog(null, title);
		dialog.setVisible(true);
		dialog.dispose();
		String value = (String) pane.getValue();
		if (value == null) {
			return 1;
		} else {
			return (value.equals(options[0])) ? 0 : 1;
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration[] gc = gd.getConfigurations();
			Rectangle gcBounds = gc[0].getBounds();
			setSize(1050, 600);

			setLocationRelativeTo(null);
		}
	}

	private MouseListener ml = new MouseAdapter() {
		Color defaultColor = UIManager.getColor("control");
		Color mouseOverColor = new Color(139, 233, 255);

		public void mousePressed(MouseEvent evt) {
			JButton jButton = (JButton) evt.getSource();
			SwingUtilities.invokeLater(() -> {
				startApp(jButton);
			});
		}

		public void mouseReleased(MouseEvent evt) {
		}

		public void mouseEntered(MouseEvent evt) {
			JButton jButton = (JButton) evt.getSource();
			jButton.setBackground(mouseOverColor);
			if (jButton == zfmButton) {
				rightLabel.setText(zfmString);
			} else if (jButton == dateButton) {
				rightLabel.setText(dateString);
			} else if (jButton == idButton) {
				rightLabel.setText(idString);
			} else if (jButton == qacButton) {
				rightLabel.setText(qacString);
			}
		}

		public void mouseExited(MouseEvent evt) {
			JButton jButton = (JButton) evt.getSource();
			jButton.setBackground(defaultColor);
		}

		public void mouseClicked(MouseEvent evt) {
		}
	};

	private class LauncherPanel extends JPanel {
		public LauncherPanel() {

			HTMLEditorKit kit = new HTMLEditorKit();
			StyleSheet styleSheet = kit.getStyleSheet();
			String resources = System.getProperty("java.resources.path");
			String basePath = FilenameUtils.concat(resources, "images");
			String fileName = FilenameUtils.concat(basePath, "bullet_green.png");
			String bulletUrl = "file:/" + fileName.replace('\\', '/');
			// if(jarPath==null) {
			// try {
			// bulletUrl = new
			// File(GhcImageLoader.getImageURL(GhcImageLoader.BULLET_GREEN).getFile()).toURI().toURL().toString();
			// System.out.println(bulletUrl);
			// } catch (MalformedURLException e) {
			// System.out.println("bulletUrl=null");
			// e.printStackTrace();
			// }
			// } else {
			// String imageFileName =
			// GhcImageLoader.IMAGE_RESOURCE_PATH_PREFIX+GhcImageLoader.BULLET_GREEN;
			// System.out.println(imageFileName);
			// File jarFile = new File(jarPath);
			// URL url;
			// try {
			// url = jarFile.toURI().toURL();
			// bulletUrl = "jar:" + url + "!"+imageFileName;
			// System.out.println(bulletUrl);
			// } catch (MalformedURLException e1) {
			// e1.printStackTrace();
			// }
			// }
			styleSheet.addRule(String.format("ul{list-style-image:url(%s);", bulletUrl));
			// styleSheet.addRule("UL {font : 12px monaco; }");
			styleSheet.addRule("UL {font : 12px Serif; }");

			styleSheet.addRule("ul li {margin: 5px 0 0 3px;}");

			int k = 0;
			String base = "org/ucdm/resource/icon/";
			URL url = null;
			String htmlText = null;
			JButton jButton = null;

			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Date Shifting </b></font>";
			jButton = new JButton(htmlText);
			jButton.setIcon(GhcImageLoader.getImageIcon(GhcImageLoader.CALENDAR));
			jButton.setHorizontalAlignment(SwingConstants.LEFT);
			jButton.setIconTextGap(30);
			jButton.addMouseListener(ml);
			jButton.setEnabled(true);
			jButton.setFocusable(false);
			jButton.setOpaque(true);
			dateButton = jButton;
			dateString = htmlText +
					"<UL>" +
					"  <LI>All original date are shifted by a random integer called shifting constant." +
					"  <LI>The participants with the same subject id have the same shifting constant cross all studies." +
					"  <LI>All date from one participant are shifted by the same shifting constant." +
					"</UL>";

			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Subject ID Generator</b></font>";
			jButton = new JButton(htmlText);
			jButton.setIcon(GhcImageLoader.getImageIcon(GhcImageLoader.STUDYID));
			jButton.setHorizontalAlignment(SwingConstants.LEFT);
			jButton.setIconTextGap(30);
			jButton.addMouseListener(ml);
			jButton.setEnabled(true);
			jButton.setFocusable(false);
			jButton.setOpaque(true);
			idButton = jButton;
			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Generate Unique Subject ID By</b></font>";

			idString = htmlText +
					"<UL>" +
					"  <LI>Subject name" +
					"  <LI>Gender" +
					"  <LI>Date of birth" +
					"</UL>" +
					"<font size=\"5\" ><b>OR</b></font>" +
					"<UL>" +
					"  <LI>Project number" +
					"  <LI>Local subject identifier number" +
					"</UL>";

			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Quality Assurance</b></font>";
			jButton = new JButton(htmlText);
			jButton.setIcon(GhcImageLoader.getImageIcon(GhcImageLoader.QAC));
			jButton.setHorizontalAlignment(SwingConstants.LEFT);
			jButton.setIconTextGap(30);
			jButton.addMouseListener(ml);
			jButton.setEnabled(true);
			jButton.setFocusable(false);
			jButton.setOpaque(true);
			qacButton = jButton;
			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Quality Assurance By</b></font>";

			qacString = htmlText +
					"<UL>" +
					"  <LI>Rule 1: The first variable of the dictionary must be “study_id”." +
					"  <LI>Rule 2: 12 minimumn CDE must exist for all projects." +
					"  <LI>Rule 3: 47 minimum CDE variables must exactly match with the requirements if <b>NOT</b> waivered." +
					"</UL>";

			// htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Study ID
			// Generator</b></font>";

			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>Zip Code Truncation & Appending</b></font>";
			jButton = new JButton(htmlText);
			jButton.setIcon(GhcImageLoader.getImageIcon(GhcImageLoader.ZIPCODEAPP));
			jButton.setHorizontalAlignment(SwingConstants.LEFT);
			jButton.setIconTextGap(30);
			jButton.addMouseListener(ml);
			jButton.setEnabled(true);
			jButton.setFocusable(false);
			jButton.setOpaque(true);
			zfmButton = jButton;
			zfmString = htmlText +
					"<UL>" +
					"  <LI>Truncate zip code to three or four digit if its population is small." +
					"  <LI>Append one or two Zs at the end of the truncated codes." +
					"  <LI>Output a five-character-long zip code string" +
					"</UL>";

			setLayout(new GridBagLayout());
			Insets insets = new Insets(1, 5, 1, 5);
			GridBagConstraints gbc = null;
			JPanel left = null;

			int nRow = 3 + 1;
			left = new JPanel(new GridLayout(nRow, 1, 1, 1));
			left.setBackground(Color.white);
			left.setOpaque(true);
			left.add(idButton);
			// left.add(zfmButton);
			// left.add(dateButton);
			// left.add(qacButton);
			left.add(new JLabel(""));
			left.add(new JLabel(""));
			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>More Modules Coming Soon</b></font>";
			JLabel jLabel = new JLabel(htmlText, null, JLabel.CENTER);
			// left.add(jLabel);
			left.add(new JLabel(""));

			gbc = new GridBagConstraints(0, 0, 1, 1, 100.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 1, 1);
			add(left, gbc);

			htmlText = "<html>" + "<font size=\"5\" color=\"maroon\"><b>RADx-Rad Toolbox</b></font>";

			String aString = htmlText +
					"<P>" + "A collection of tools for boi-medical informatics research" +
					"<UL>" +
					"  <LI>More tools will be released soon." +
					"</UL>";

			rightLabel = new JLabel(aString, null, JLabel.LEFT);
			rightLabel.setBackground(Color.white);
			rightLabel.setOpaque(true);
			setOpaque(true);
			// Dimension setOpaque(true);d = _jLabel.getPreferredSize();
			rightLabel.setPreferredSize(new Dimension(500, 600));
			rightLabel.setMinimumSize(new Dimension(500, 600));
			// _jLabel.setPreferredSize(new Dimension(450,560));
			// _jLabel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
			// _jLabel.setBorder(BorderFactory.createTitledBorder("Introduction"));
			Border raisedbevel = BorderFactory.createRaisedBevelBorder();
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			// _jLabel.setBorder(BorderFactory.createCompoundBorder(raisedbevel,
			// loweredbevel));
			Border paddingBorder = BorderFactory.createEmptyBorder(5, 0, 5, 2);
			Border outside = BorderFactory.createCompoundBorder(paddingBorder, raisedbevel);
			paddingBorder = BorderFactory.createEmptyBorder(10, 5, 10, 5);
			Border inside = BorderFactory.createCompoundBorder(loweredbevel, paddingBorder);
			rightLabel.setBorder(BorderFactory.createCompoundBorder(outside, inside));

			rightLabel.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						final String htmlText = rightLabel.getText();
						List<String> hrefText = GhcStringUtil.grabHTMLLinks(htmlText);
						if (hrefText != null && hrefText.size() > 1) {
							Desktop.getDesktop().browse(new URI(hrefText.get(1)));
						}
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(Cursor.getDefaultCursor());
				}
			});

			gbc = new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, insets, 0, 0);
			add(rightLabel, gbc);
		}
	}

	@Override
	public IPanel getBaseWorld() {
		return null;
	}

	@Override
	public Mosaic getMosaic() {
		return null;
	}

	public String getProjectFullName() {
		return System.getProperty("user.dir") + File.separator + "junk.txt";
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}

	public String getCwd(int index) {
		return cwd[index];
	}

	public void setCwd(int index, String cwd) {
		this.cwd[index] = cwd;
	}

	// public class Html2Text extends HTMLEditorKit.ParserCallback {
	// StringBuffer s;
	//
	// public Html2Text() {}
	//
	// public void parse(Reader in) throws IOException {
	// s = new StringBuffer();
	// ParserDelegator delegator = new ParserDelegator();
	// // the third parameter is TRUE to ignore charset directive
	// delegator.parse(in, this, Boolean.TRUE);
	// }
	//
	// public void handleText(char[] text, int pos) {
	// s.append(text);
	// }
	//
	// public String getText() {
	// return s.toString();
	// }
	//
	// }

}
