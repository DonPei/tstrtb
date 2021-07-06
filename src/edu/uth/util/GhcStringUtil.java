package edu.uth.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GhcStringUtil {

	private static final String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_A_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	public static List<String> grabHTMLLinks(final String html) {
		Pattern patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
		Pattern patternLink = Pattern.compile(HTML_A_HREF_TAG_PATTERN);

		List<String> result = new ArrayList<String>();

		Matcher matcherTag = patternTag.matcher(html);

		while (matcherTag.find()) {
			String href = matcherTag.group(1); // href
			String linkText = matcherTag.group(2); // link text
			if (linkText != null && (!linkText.isEmpty())) {
				result.add(linkText);
			} else {
				result.add("null");
			}
			Matcher matcherLink = patternLink.matcher(href);

			while (matcherLink.find()) {
				String link = matcherLink.group(1);
				link = link.replaceAll("'", "");
				link = link.replaceAll("\"", "");
				result.add(link);
			}
		}
		return result;
	}

	/**
	 * Placeholder for internationalization of strings. Will eventually contain logic for
	 * resource bundle lookup of alternate language text. Labels on panels, buttons,
	 * menus, etc. should not be hardcoded, but rather, should invoke this method so that
	 * they can more easily support foreign language versions of this application.
	 */
	public static String getInternationalString(String str) {
		return str;
	}

}
