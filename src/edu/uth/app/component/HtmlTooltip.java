package edu.uth.app.component;

import java.awt.FontMetrics;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.UIManager;


//https://stackoverflow.com/questions/20008536/java-multiline-tooltip-with-fixed-max-width-using-html-tag

public final class HtmlTooltip {

	private static int         sWrapLength  = 350;
	private static FontMetrics sFontMetrics = new JLabel().getFontMetrics(UIManager.getFont("ToolTip.font"));

	/**
	 * Add HTML tags to force it to wrap at a defined length. <pre>{@code
	 *  component.setToolTipText(toString(text));
	 * }</pre>
	 *
	 * @param html String
	 * @return update string ready to be used with setToolTipText
	 * @see JComponent#setToolTipText(String)
	 */
	public static String toString(final String html) {
		final int length = sFontMetrics.stringWidth(longest(html));
		return String.format("<html><p width=%d\"pt\">%s</html>",
				length > sWrapLength ? sWrapLength : length,
						html);
	}

	/**
	 * Given a complete HTML string, find the longest piece between tags.
	 *
	 * @param html String
	 * @return longest piece
	 */
	private static String longest(final String html) {
		return Arrays.asList(html.split("<"))
				.stream()
				.max(Comparator.comparingInt(String::length))
				.get();
	}

	private HtmlTooltip() {
	}
}
