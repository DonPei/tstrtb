package edu.uth.swing.plaf.laf.uidelegates;

import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

// https://stackoverflow.com/questions/14159536/creating-jbutton-with-customized-look

public class GButtonUIMenu extends BasicButtonUI {
	@Override
	public void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();

	}
}
