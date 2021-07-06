package edu.uth.app.zta;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.io.FilenameUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import edu.uth.app.common.table.CsvTable;


public class ZtaPanel extends JPanel implements TreeSelectionListener {

	private ZtaFrame 		frame 			= null;
	private JTree 			tree 			= null;
	private JPopupMenu popupMenu = null;

	private boolean isListening = true;
	
	private TreeNode 		nextSibling 	= null;
	private String folderName = null;

	public ZtaPanel(ZtaFrame frame, String folderName) {
		this.frame = frame;
		this.folderName = folderName;
		setLayout(new BorderLayout());
		add(createContents(folderName), BorderLayout.CENTER);
	}

	private JSplitPane createContents(String folderName) {
		File[] roots = new File [] { new File(folderName)};
		FileTreeNode rootTreeNode = new FileTreeNode(roots);
		tree = new JTree(rootTreeNode);
		tree.setCellRenderer(new FileTreeCellRenderer());
		tree.setRootVisible(false);
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(evt.isMetaDown()) {//Press mouse right button
					if((!evt.isAltDown())&&(!evt.isShiftDown())) {
						TreePath path = tree.getPathForLocation ( evt.getX (), evt.getY () );
						popupMenu = new JPopupMenu();
						setPopupMenu(path);
						popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
					}
				} 
			}
		});

		JScrollPane treeJScrollPane = new JScrollPane(tree);

		JSplitPane rightComponent = createRightContents();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treeJScrollPane);
		splitPane.setRightComponent(rightComponent);

		return splitPane;
	}
	private JSplitPane createRightContents() {
		CsvTable table = frame.getCsvTable();
		table.setRowHeight(30);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane tableScrollPane = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JScrollPane textAreaScrollPane = genTextAreaScrollPane();

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(tableScrollPane);
		splitPane.setBottomComponent(textAreaScrollPane);
		setJSplitPaneDividerLocation(splitPane, 0.7);
		return splitPane;
	}

	protected JScrollPane genTextAreaScrollPane() {
		JTextArea textArea = frame.getTextArea();
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
		textArea.setText(getUserGuide());
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		scrollPane.setColumnHeaderView(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setWheelScrollingEnabled(true);
		// scrollPane.setMinimumSize(new Dimension(750, 500));
		// scrollPane.setPreferredSize(new Dimension(750, 500));

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
	
	public String getUserGuide() {
		String message = 
						"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"+
						"@@@@@@@@@@@@@@@@@@ Welcome to Radx-Rad Toolbox @@@@@@@@@@@@@@@@@@@\n"+
						"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"+
						Timestamp.from(Instant.now())+"\n";

		StringBuilder sb = new StringBuilder(message);

		sb.append("File>>New... to create a new project.\n");
		sb.append("Once created, project should be IMMEDIATELY saved.\n");

		return sb.toString();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		FileTreeNode node = (FileTreeNode)tree.getLastSelectedPathComponent();
		if (node == null) return;

		if (node.isLeaf()) {
			//int nRow = tree.getRowCount();
			File file = node.getFile();
			try {
				String filename = file.getCanonicalPath();
				if(FilenameUtils.isExtension(filename, "csv")) {
					frame.loadFileToTabel(filename);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//updateCurve(getCurveIndex((String)nodeInfo));
		} 
	}

	//http://www.javalobby.org/forums/thread.jspa?threadID=16052&tstart=0
	private static class FileTreeNode implements TreeNode {
		private File file;
		private File[] children;
		private TreeNode parent;
		private boolean isFileSystemRoot = false;

		public FileTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
			this.file = file;
			this.isFileSystemRoot = isFileSystemRoot;
			this.parent = parent;
			this.children = this.file.listFiles();
			if (this.children == null)
				this.children = new File[0];
		}

		public FileTreeNode(File[] children) {
			this.file = null;
			this.parent = null;
			this.children = children;
		}

		public Enumeration children() {
			final int elementCount = this.children.length;
			return new Enumeration<File>() {
				int count = 0;
				public boolean hasMoreElements() { return this.count < elementCount; }

				public File nextElement() {
					if (this.count < elementCount) {
						return FileTreeNode.this.children[this.count++];
					}
					throw new NoSuchElementException("Vector Enumeration");
				}
			};

		}

		public boolean getAllowsChildren() 	{ return true; }
		public int getChildCount() 			{ return this.children.length; }
		public TreeNode getParent() 		{ return this.parent; }
		public File getFile() 				{ return file; }


		public TreeNode getChildAt(int childIndex) {
			return new FileTreeNode(this.children[childIndex],
					this.parent == null, this);
		}

		public int getIndex(TreeNode node) {
			FileTreeNode ftn = (FileTreeNode) node;
			for (int i = 0; i < this.children.length; i++) {
				if (ftn.file.equals(this.children[i]))
					return i;
			}
			return -1;
		}

		public boolean isLeaf() { return (this.getChildCount() == 0); }
	}

	protected static FileSystemView fsv = FileSystemView.getFileSystemView();
	private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {
		private Map<String, Icon> iconCache = new HashMap<String, Icon>();
		private Map<File, String> rootNameCache = new HashMap<File, String>();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			FileTreeNode ftn = (FileTreeNode) value;
			File file = ftn.file;
			String filename = "";
			if (file != null) {
				if (ftn.isFileSystemRoot) {
					// long start = System.currentTimeMillis();
					filename = this.rootNameCache.get(file);
					if (filename == null) {
						filename = fsv.getSystemDisplayName(file);
						this.rootNameCache.put(file, filename);
					}
					// long end = System.currentTimeMillis();
					// System.out.println(filename + ":" + (end - start));
				} else {
					filename = file.getName();
				}
			}
			JLabel result = (JLabel) super.getTreeCellRendererComponent(tree,
					filename, sel, expanded, leaf, row, hasFocus);
			if (file != null) {
				Icon icon = this.iconCache.get(filename);
				if (icon == null) {
					// System.out.println("Getting icon of " + filename);
					icon = fsv.getSystemIcon(file);
					this.iconCache.put(filename, icon);
				}
				result.setIcon(icon);
			}
			return result;
		}
	}
	
	/**
	 * Set JSplitPane proportional divider location
	 * 
	 * @param jsplitpane JSplitPane to set
	 * @param proportionalLocation double <0.0; 1.0>
	 */
	public static void setJSplitPaneDividerLocation(final JSplitPane jsplitpane, final double proportionalLocation)
	{
		if (jsplitpane.isShowing()) {
			if (jsplitpane.getWidth() > 0 && jsplitpane.getHeight() > 0) {
				jsplitpane.setDividerLocation(proportionalLocation);
			} else {
				jsplitpane.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent ce) {
						jsplitpane.removeComponentListener(this);
						setJSplitPaneDividerLocation(jsplitpane, proportionalLocation);
					}
				});
			}
		} else {
			jsplitpane.addHierarchyListener(new HierarchyListener() {
				@Override
				public void hierarchyChanged(HierarchyEvent e) {
					if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && jsplitpane.isShowing()) {
						jsplitpane.removeHierarchyListener(this);
						setJSplitPaneDividerLocation(jsplitpane, proportionalLocation);
					}
				}
			});
		}
	}
	
	public void setPopupMenu(TreePath path) {
		JMenuItem jMenuItem = new JMenuItem("Refresh");
		popupMenu.add(jMenuItem);
		jMenuItem.setToolTipText("Refresh ");
		jMenuItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				if(frame.getProject()==null) return;
				refreshTree();
			}
		});
	}
	
	public void refreshTree() {
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		FileTreeNode root = (FileTreeNode)model.getRoot();
		model.reload(root);
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

}
