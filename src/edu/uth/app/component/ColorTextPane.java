package edu.uth.app.component;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

//http://www.java2s.com/Code/Java/Swing-JFC/ExtensionofJTextPanethatallowstheusertoeasilyappendcoloredtexttothedocument.htm

public class ColorTextPane extends JTextPane {


  public void append(Color c, String s) { // better implementation--uses StyleContext
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
    int len = getDocument().getLength(); // same value as getText().length();
    setCaretPosition(len); // place caret at the end (with no selection)
    setCharacterAttributes(aset, false);
    replaceSelection(s); // there is no selection, so inserts at caret
  }
  
  
  public void append(String text) {
	    try {
	    	
	    	SimpleAttributeSet attributeSet = new SimpleAttributeSet();  
	        StyleConstants.setItalic(attributeSet, true);  
	        StyleConstants.setForeground(attributeSet, Color.red);  
	        StyleConstants.setBackground(attributeSet, Color.blue);
	        
	        Document doc = getDocument();
	        doc.insertString(doc.getLength(), text, attributeSet);  
	        
//	        // Move the insertion point to the end
//	        setCaretPosition(doc.getLength());
//
//	        // Insert the text
//	        replaceSelection(text);
//
//	        // Convert the new end location
//	        // to view co-ordinates
//	        Rectangle r = modelToView(doc.getLength());

	        // Finally, scroll so that the new text is visible
//	        if (r != null) {
//	          scrollRectToVisible(r);
//	        }
	      } catch (BadLocationException e) {
	        System.out.println("Failed to append text: " + e);
	      }
	    }
}

