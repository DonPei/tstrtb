package edu.uth.app.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import lombok.Getter;
import lombok.Setter;

import edu.uth.swing.ui.UiUtil;

@Getter
@Setter
public abstract class CommonDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public int width = 400;
	public int height = 500;
	public boolean enableOKButton = true;
	public boolean enableApplyButton = false;
	public boolean enableSaveButton = false;
	public String applyText = "Apply";
	public String okText = "OK";
	public String saveText = "Save";

	public CommonDialog(JFrame aParent, String aTitle, boolean modal) {
		super(aParent, aTitle, modal);
		
//		addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				JDialog newDialog = (JDialog)e.getComponent();             
//				Dimension dimDialog = newDialog.getSize();
//
//				setPreferredSize(dimDialog);       
//				SwingUtilities.updateComponentTreeUI(aParent);
//				
////				repaint();
////				revalidate();
//				pack();
//
//				//System.out.println("Resized to " + e.getComponent().getSize());
//			}
//			@Override
//			public void componentMoved(ComponentEvent e) {
//				//System.out.println("Moved to " + e.getComponent().getLocation());
//			}
//		});
	}

	public void showDialog(){
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setResizable(true);
		addCancelByEscapeKey();

		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		panel.setBorder( UiUtil.getStandardBorder() );

		JComponent jc = createContents();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		jc.setBorder(border);

		panel.add(jc, BorderLayout.CENTER);
		JComponent command = getCommandRow();
		if(command!=null) panel.add( getCommandRow(), BorderLayout.SOUTH );

		getContentPane().removeAll();
		getContentPane().add( panel );

		setSize(width, height);
		UiUtil.centerOnParentAndShow( this );
		setVisible(true);
	}

	protected abstract JComponent createContents();
	protected abstract boolean okAction();
	public void setDialogWindowSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	protected boolean saveAction() { return true; }
	protected boolean preCancle() { return true; }

	protected JComponent getCommandRow() {
		JButton ok = new JButton(okText);
		ok.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(okAction()) dispose();
			}
		});
		JButton save = new JButton(saveText);
		save.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(saveAction()) dispose();
			}
		});
		JButton apply = new JButton(applyText);
		apply.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				okAction();
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				preCancle();
				dispose();
			}
		});

		this.getRootPane().setDefaultButton( ok );
		List<JComponent> buttons = new ArrayList<JComponent>();
		if (enableApplyButton)
			buttons.add(apply);
		if (enableSaveButton)
			buttons.add(save);
		if (enableOKButton)
			buttons.add(ok);
		buttons.add( cancel );
		return UiUtil.getCommandRow( buttons );
	}

	private void addCancelByEscapeKey(){
		String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
		int noModifiers = 0;
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, noModifiers, false);
		InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(escapeKey, CANCEL_ACTION_KEY);
		AbstractAction cancelAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		};
		this.getRootPane().getActionMap().put(CANCEL_ACTION_KEY, cancelAction);
	}
}
