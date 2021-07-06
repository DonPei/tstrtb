package edu.uth.app.zta.dialog;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import edu.uth.app.common.CommonDialog;
import edu.uth.app.zta.ZtaFrame;
import edu.uth.kit.project.ProjectZta;

public class LoadFileDialog  extends CommonDialog {
	private ZtaFrame 	frame 			= null;
	public JTextField 	velTF 			= null;
	private int 		velFileType	= 0; 	// 0 csv
	public int 			iUnit 			= 1;	// 0 ft; 1 m 2 km
	public int 			iId				= 10;	

	private JProgressBar progressBar = null;

	private LoadFileTask loadFileTask = null;
	
	public LoadFileDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		setEnableOKButton(false);
		setDialogWindowSize(800, 140);
		this.frame 		= (ZtaFrame)aParent;
	}

	protected JPanel createContents() {
		progressBar = new JProgressBar(0, 100);
		//progressBar.setValue(0);
		//progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(progressBar, BorderLayout.CENTER);
		return panel;
	}

	public boolean loading() {
		return okAction();
	}
	protected boolean okAction() {
		boolean inValid = false;
		//loadFileTask = new LoadFileTask();

		//loadFileTask.addPropertyChangeListener(this);
		// Execute task in worker thread.
		//loadFileTask.execute();
		return true;
	}
	
	protected boolean preCancle() {
		if (loadFileTask != null  &&  !loadFileTask.isDone()) {
			//LOG.fine("Calculator Task is running. Cancel it.");
			loadFileTask.cancel(true);
			progressBar.setVisible(false);;
		} else {
			setVisible( false );
			dispose();
		}
		return true; 
	}
	
	public void cancelItAll() {
		setVisible( false );
		dispose();
	}
	
	
	public class LoadFileTask extends SwingWorker<Void, Void> {
		public LoadFileTask() {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
		}

		@Override
		protected Void doInBackground() throws InterruptedException {
			//while (isCancelled()) return null;			
			//frame.getProject().readCsvFile();			
			return null;
		}

		@Override
		protected void done() {
			//progressBar.setVisible(false);
			//setVisible( false );
			//dispose();
			cancelItAll();
			//List<String[]> rows = getProject().getRows();
			//frame.updateWorld(true);
			
			if (!isCancelled()) {
	            JOptionPane.showMessageDialog(null,
	                    "File has been uploaded successfully!", "Message",
	                    JOptionPane.INFORMATION_MESSAGE);
	        }
			
		}
	}
	
	class ProgressListener implements PropertyChangeListener {
		private JProgressBar bar;

		ProgressListener() {} 

		ProgressListener(JProgressBar b) {
			this.bar = b;
			bar.setValue(0);
		}

		public void propertyChange(PropertyChangeEvent evt) {
			//Determine whether the property is progress type
			if ("progress".equals(evt.getPropertyName())) { 
				bar.setValue((int) evt.getNewValue());
			}
		}
	}
	
	
}
