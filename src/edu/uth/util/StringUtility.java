package edu.uth.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtility is a utility class with a number of common string handling routines.
 */
public final class StringUtility {

	private StringUtility() {
	}

	public final static String SPACE = " ";
	public final static char SPACE_CHAR = SPACE.charAt(0);
	public final static String EMPTY_STRING = "";
	public final static String REFUSED = " - Refused";
	public final static String NEWLINE = new String(System.getProperty("line.separator"));

	public final static DecimalFormat DEFAULT_MONEY_FORMAT = new DecimalFormat("$#0.00");
	public final static DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	public final static DecimalFormat NCPDP_QUANTITY_FORMAT = new DecimalFormat("#0.000");

	public final static String SELECT = "--SELECT--";

	private static final Pattern EMAIL_PATTERN = Pattern
			.compile(
					"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

	private static final Pattern NO_SPACE = Pattern.compile("^(\\S*)$");

	private static final Pattern URL_PATTERN = Pattern
			.compile("^((http|https)://)?[\\w]+([\\-\\.]{1}[\\w]+)*\\.[a-zA-Z0-9]{1,}(:[0-9]{1,})?(\\/.*)?$", Pattern.CASE_INSENSITIVE);

	public final static String STRING_N0 = "N";
	public final static String STRING_YES = "Y";

	public final static String POSITIVE = "Positive";
	public final static String NEGETIVE = "Negative";
	public final static String COMMA = ",";
	public static final String N_A = "N/A";

	public static String CURLY_BRACKETS_ONLY = "{}";
	public static String QUESTION_MARK = "?";
	public static String AMPERSAND = "&";
	public static final String ELLIPSE = "...";

	/**
	 * Regular Expression constant to parse Patient Name
	 */
	public static final String NAME_REGEXP = "([a-zA-Z-'.\\s])*";

	/**
	 * Pad a string on the left with the specified character.
	 *
	 * @param source the string to be padded
	 * @param totalLength the total desired length of the returned string. If the source
	 *            string length is greater than totalLength, the left- most characters of
	 *            the source string will be truncated. For instance, padLeft("ABCDE", 3,
	 *            'x') will return "CDE".
	 * @param padChar the character to pad the string with.
	 * @return the padded string
	 */
	public static String padLeft(String source, int totalLength, char padChar) {
		try {
			final int sourceLength = source.length();
			if (sourceLength >= totalLength)
				return source.substring(sourceLength - totalLength);

			StringBuffer sb = new StringBuffer(totalLength);

			for (int i = totalLength - sourceLength; --i >= 0;) {
				sb.append(padChar);
			}

			sb.append(source);
			return sb.toString();
		} catch (Exception e) // most efficient way to handle a negative totalLength
		{ // or a null source
			return null;
		}
	}

	/**
	 * Pad a string on the left with the specified character.
	 *
	 * @param source the string to be padded
	 * @param totalLength the total desired length of the returned string. If the source
	 *            string length is greater than totalLength, the left- most characters of
	 *            the source string will be truncated. For instance, padLeft("ABCDE", 3,
	 *            'x') will return "CDE".
	 * @param padChar the character to pad the string with.
	 * @param target the StringBuffer in which to place the padded String.
	 * @param targetOffset the displacement into the StringBuffer at which to place the
	 *            padded String.
	 */
	public static void padLeft(String source,
			int totalLength,
			char padChar,
			StringBuffer target,
			int targetOffset) {
		try {
			final int requiredBuffLength = targetOffset + totalLength;

			for (int i = target.length(); i < requiredBuffLength; i++)
				target.append(padChar);

			final int sourceLength = source.length();
			if (sourceLength >= totalLength) {
				target.replace(targetOffset,
						requiredBuffLength,
						source.substring(sourceLength - totalLength));
				return;
			}

			final int delta = requiredBuffLength - sourceLength;

			for (int i = delta; --i >= targetOffset;) {
				target.setCharAt(i, padChar);
			}

			target.replace(delta,
					requiredBuffLength,
					source);
		} catch (Throwable e) // most efficient way to handle a negative totalLength
		{ // or a null source
			target.append("StringUtility.padLeft(" +
					source + ", " + totalLength + ", " + padChar +
					", " + target.toString() + ", " + targetOffset +
					") - Exception: " + e);
			System.err.println(target);
		}
	}

	/**
	 * Pad a string on the right with the specified character.
	 *
	 * @param source the string to be padded
	 * @param totalLength the total desired length of the returned string. If the source
	 *            string length is greater than totalLength, the right- most characters of
	 *            the source string will be truncated. For instance, padRight("ABCDE", 3,
	 *            'x') will return "ABC".
	 * @param padChar the character to pad the string with.
	 * @return the padded string
	 */
	public static String padRight(String source, int totalLength, char padChar) {
		try {
			final int sourceLength = source.length();
			if (sourceLength >= totalLength)
				return source.substring(0, totalLength);

			StringBuffer sb = new StringBuffer(totalLength);
			sb.append(source);

			for (int i = totalLength - sourceLength; --i >= 0;) {
				sb.append(padChar);
			}

			return sb.toString();
		} catch (Exception e) // most efficient way to handle a negative totalLength
		{ // or a null source
			return null;
		}
	}

	/**
	 * Pad a string on the right with the specified character.
	 *
	 * @param source the string to be padded
	 * @param totalLength the total desired length of the returned string. If the source
	 *            string length is greater than totalLength, the right- most characters of
	 *            the source string will be truncated. For instance, padRight("ABCDE", 3,
	 *            'x') will return "ABC".
	 * @param padChar the character to pad the string with.
	 * @param target the StringBuffer in which to place the padded String.
	 * @param targetOffset the displacement into the StringBuffer at which to place the
	 *            padded String.
	 */
	public static void padRight(String source,
			int totalLength,
			char padChar,
			StringBuffer target,
			int targetOffset) {
		try {
			final int requiredBuffLength = targetOffset + totalLength;

			for (int i = target.length(); i < requiredBuffLength; i++)
				target.append(padChar);

			final int sourceLength = source.length();
			if (sourceLength >= totalLength) {
				target.replace(targetOffset,
						requiredBuffLength,
						source.substring(0, totalLength));
				return;
			}

			final int stop = targetOffset + sourceLength;

			target.replace(targetOffset,
					stop,
					source);

			for (int i = requiredBuffLength; --i >= stop;) {
				target.setCharAt(i, padChar);
			}
		} catch (Throwable e) // most efficient way to handle a negative totalLength
		{ // or a null source
			target.append("StringUtility.padRight(" +
					source + ", " + totalLength + ", " + padChar +
					", " + target.toString() + ", " + targetOffset +
					") - Exception: " + e);
			System.err.println(target);
		}
	}

	/**
	 * Converts a String into a String Array
	 *
	 * @param source - String to be Converted into String Array
	 * @param token - Specific token used to separate the elements
	 * @return - Converted String Array
	 */
	public static final String[] stringToArray(String source, String token) {
		StringTokenizer st = new StringTokenizer(source, token);
		final int tokenCount = st.countTokens();
		String[] result = new String[tokenCount];

		for (int i = 0; i < tokenCount; i++) {
			result[i] = st.nextToken();
		}

		return result;
	}

	/**
	 * Converts a String into a double String Array
	 *
	 * @param source - String to be Converted into String Array
	 * @param rowToken - Specific token used to separate the primary array elements (or
	 *            "rows").
	 * @param columnToken - Specific token used to separate the secondary array elements
	 *            (or "columns").
	 * @return - Converted String Array
	 */
	public static final String[][] stringToArray(String source,
			String rowToken,
			String columnToken) {
		return stringToArray(source, rowToken, columnToken, "");
	}

	/**
	 * Converts a String into a double String Array
	 *
	 * @param source - String to be Converted into String Array
	 * @param rowToken - Specific token used to separate the primary array elements (or
	 *            "rows").
	 * @param columnToken - Specific token used to separate the secondary array elements
	 *            (or "columns").
	 * @return - Converted String Array
	 */
	public static final String[][] stringToArray(String source,
			String rowToken,
			String columnToken,
			String emptyTokenReplacement) {
		StringTokenizer st = new StringTokenizer(source, rowToken);
		final int rowCount = st.countTokens();
		String[][] result = new String[rowCount][];

		for (int r = 0; r < rowCount; r++) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken(), columnToken);
			final int colCount = st2.countTokens();
			result[r] = new String[colCount];

			for (int c = 0; c < colCount; c++) {
				String value = st2.nextToken();
				if (value.length() == 0)
					value = emptyTokenReplacement;
				result[r][c] = value;
			}
		}

		return result;
	}

	/**
	 * Converts a String Array into a String, each element seperated by a specific token
	 * (i.e. Email,Address ... etc)
	 *
	 * @param strString - String Array to be converted to String
	 * @param token - Specific token used to separate the elements in the String
	 * @return - String containing all the elements of Array seperated by the given token
	 */
	public static final String arrayToString(String[] strString, String token) {
		final StringBuilder builder = new StringBuilder();

		if (strString.length > 0)
			builder.append(strString[0]);

		for (int i = 1; i < strString.length; i++) {
			builder.append(token).append(strString[i]);
		}

		return builder.toString();
	}

	/**
	 * Converts a double String Array into a String, each "row" and "column" separated by
	 * a specific token (i.e. col1a,col2a,col3a;col1b,col2c,col3c...)
	 *
	 * @param strString - Double String Array to be converted to String
	 * @param rowToken - Specific token used to separate the primary array elements, or
	 *            "rows". (String[x][] - "x" indicates the primary array).
	 * @param columnToken - Specific token used to separate the secondary array elements,
	 *            or "columns". (String[][x] - "x" indicates the secondary array).
	 * @return - String containing all the elements of Array seperated by the given tokens
	 */
	public static final String arrayToString(String[][] source,
			String rowToken,
			String columnToken) {
		final StringBuilder sb = new StringBuilder();

		final int rowCount = source.length;
		final int lastRow = rowCount - 1;

		for (int r = 0; r < rowCount; r++) {
			final int colCount = source[r].length;
			final int lastCol = colCount - 1;

			for (int c = 0; c < colCount; c++) {
				String element = source[r][c];
				sb.append(element == null ? "" : element);
				if (c != lastCol)
					sb.append(columnToken);
			}

			if (r != lastRow)
				sb.append(rowToken);
		}

		return sb.toString();
	}

	/**
	 * Converts a Collection<String> into a String, each element separated by a specific
	 * token
	 *
	 * @param strCollection Collection to be converted to String
	 * @param token Specific token used to separate the elements in the String
	 * @param emptyDefault Value to return if the collection is null or empty
	 * @return String containing all the elements of the Collection separated by the given
	 *         token
	 */
	public static final String collectionToString(Collection<String> strCollection, String token, String emptyDefault) {
		if (strCollection == null || strCollection.isEmpty()) {
			return emptyDefault;
		}

		final StringBuilder strBuilder = new StringBuilder();
		Iterator<String> strIter = strCollection.iterator();
		// Has to be at least one element in list, or we wouldn't make it this far, so no
		// need to check hasNext() here.
		strBuilder.append(strIter.next());

		while (strIter.hasNext()) {
			strBuilder.append(token).append(strIter.next());
		}

		return strBuilder.toString();
	}

	/**
	 * Remove the specified character from the source String.
	 * 
	 * @param source The source from which to remove.
	 * @param removeChar The character to remove from the source
	 * @return a String with all the removeChar's removed.
	 */
	public static String remove(String source, char removeChar) {
		if (source == null)
			return null;

		final int sourceLength = source.length();

		StringBuffer sb = new StringBuffer(sourceLength);

		for (int i = 0; i < sourceLength; i++) {
			final char currentChar = source.charAt(i);

			if (currentChar != removeChar)
				sb.append(currentChar);
		}

		return sb.toString();
	}

	public static String trimWithDefault(String toTrim, String defaultIfNull) {
		if (toTrim == null)
			return defaultIfNull;

		return toTrim.trim();
	}

	public static String trimToNull(String s) {
		String trim = (s == null) ? null : s.trim();
		if (trim == null || trim.length() == 0)
			return null;
		if (trim.equals("null") || trim.equals("&nbsp;"))
			return null;
		return trim;
	}
	

	/**
	 * Citrix adds the session value to the OS printer name, which makes using the printer impossible.
	 * The Citrix team can't configure this without disrupting other teams, so we will remove the 
	 * Session here.
	 * 
	 * @param printerName printer name to check
	 * @return trimmed printer name
	 */
	public static String trimPrinterName(String printerName) {
		String[] printerNames = printerName.split("[(]from .*");
		return printerNames[0].trim();
	}
	
	/**
	 * Returns true if object is null, or if object.toString().trim().length() == 0.
	 */
	public static boolean isNothing(Object object) {
		return (object == null || object.toString().trim().length() == 0);
	}

	/**
	 * Converts a <code>String</code> with a value of <quote>Y</quote> or <quote>N</quote>
	 * into a <code>boolean.
	 * &#64;param yn the string to convert
	 * @param default the value to return if <code>yn</code> is null
	 */
	public static boolean booleanFromYN(String yn, boolean defaultVal) {
		if (yn == null)
			return defaultVal;

		return (yn.toUpperCase().compareTo("Y") == 0)
				? true : false;
	}

	/**
	 * Converts a <code>String</code> with a value of <quote>Yes</quote> or
	 * <quote>No</quote> into a <code>boolean.
	 * &#64;param yn the string to convert
	 * @param default the value to return if <code>yn</code> is null
	 */
	public static boolean booleanFromYesNo(String yn, boolean defaultVal) {
		if (yn == null)
			return defaultVal;

		return yn.toUpperCase().equals("YES");

		/*
		 * return (yn.toUpperCase().compareTo("Yes") == 0) ? true : false;
		 */
	}

	/**
	 * Converts a <code>String</code> with a value of <quote>Y</quote> or <quote>N</quote>
	 * into a <code>Boolean, or null if String is null.
	 * 
	 * @param yn the string to convert
	 */
	public static Boolean booleanFromYN(String yn) {
		if (yn == null)
			return null;

		return (yn.toUpperCase().compareTo("Y") == 0)
				? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Converts a <code>String</code> with a value of <quote>Y</quote> or <quote>N</quote>
	 * into a <code>Boolean. If the value is null, the default will be returned.
	 * 
	 * @param yn the string to convert
	 * @param Boolean defaultValue
	 */
	public static Boolean booleanFromYN(String yn, Boolean defaultValue) {
		if (yn == null)
			return defaultValue;

		return (yn.toUpperCase().compareTo("Y") == 0)
				? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Converts a <code>String</code> with a value of <quote>Yes</quote> or
	 * <quote>No</quote> into a <code>Boolean, or null if String is null.
	 * 
	 * @param yn the string to convert
	 */
	public static Boolean booleanFromYesNo(String yn) {
		if (yn == null)
			return null;

		return (yn.toUpperCase().compareTo("YES") == 0)
				? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Evaluates a string and attempts to convert to a Boolean. Valid inputs are Y, N, T
	 * and F (depending on value of allowTF).
	 * 
	 * @param yntf the string to convert
	 * @param allowTF true if "T" and "F" are valid inputs, false otherwise
	 */
	public static Boolean evalToBoolean(String yntf, boolean allowTF) {
		if (yntf == null)
			return null;
		// We want uppercase and no spaces.
		yntf = yntf.trim().toUpperCase();
		if (yntf.length() == 0)
			return null;
		switch (yntf.charAt(0)) {
			case 'Y':
				return Boolean.TRUE;
			case 'T':
				return allowTF ? Boolean.TRUE : null;
			case 'N':
				return Boolean.FALSE;
			case 'F':
				return allowTF ? Boolean.FALSE : null;
			default:
				return null;
		}
	}

	/**
	 * Evaluates a string and attempts to convert to a Boolean. Valid inputs are Y, N, T
	 * and F.
	 * 
	 * @param yntf the string to convert
	 */
	public static Boolean evalToBoolean(String yntf) {
		return evalToBoolean(yntf, true);
	}

	/**
	 * Converts a <code>boolean</code> into a string with a value of <quote>Y</quote> if
	 * true otherwise <quote>N</quote>.
	 * 
	 * @param value the <code>boolean</code> to convert
	 */
	public static String booleanToYN(boolean value) {
		return value ? "Y" : "N";
	}

	/**
	 * Converts a <code>Boolean</code> into a string with a value of <quote>Y</quote> if
	 * true otherwise <quote>N</quote>. If the value is null, null is returned.
	 * 
	 * @param value the <code>Boolean</code> to convert
	 */
	public static String booleanToYN(Boolean value) {
		if (value == null)
			return null;
		return value.booleanValue() ? "Y" : "N";
	}
	
	/**
	 * Converts a <code>Boolean</code> into a string with a value of <quote>Y</quote> if
	 * true otherwise <quote>N</quote>. If it is null, and there is a default value preference
	 * then default will be set.
	 * 
	 * @param value the <code>Boolean</code> to convert
	 */
	public static String booleanToYN(Boolean value, String defaultValue) {
		return (value == null)?defaultValue:booleanToYN(value);
	}
	
	/**
	 * Converts a <code>Boolean</code> into a string with a value of <quote>Y</quote> if
	 * true otherwise <quote>N</quote>. If the value is null, null is returned.
	 * 
	 * @param value the <code>Boolean</code> to convert
	 */
	public static String booleanToYesNoAny(Boolean value) {
		if (value == null)
			return "Any";
		return value.booleanValue() ? "Yes" : "No";
	}

	/**
	 * Converts a <code>Boolean</code> into a string with a value of <quote>1</quote> if
	 * true otherwise <quote>0</quote>. If the value is null, null is returned.
	 * 
	 * @param value the <code>Boolean</code> to convert
	 */
	public static String booleanToOneZero(Boolean value) {
		if (value == null)
			return null;
		return value.booleanValue() ? "1" : "0";
	}

	/**
	 * Converts a <code>String</code> with a value of <quote>1</quote> or <quote>0</quote>
	 * into a <code>Boolean, or null if String is null.
	 * 
	 * @param yn the string to convert
	 */
	public static Boolean booleanFromOneZero(String yn) {
		if (yn == null)
			return null;

		return (yn.compareTo("1") == 0)
				? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Converts a Boolean into a string with a value of <quote>Yes</quote> if true
	 * otherwise <quote>No</quote>.
	 * 
	 * If the Boolean is null, it returns null.
	 * 
	 * @param value Boolean
	 * @return String
	 */
	public static String booleanToYesNoNull(Boolean value) {
		if (value != null) {
			return booleanToYesNo(value);
		} else {
			return null;
		}
	}

	/**
	 * Converts a <code>boolean</code> into a string with a value of <quote>Yes</quote> if
	 * true otherwise <quote>No</quote>.
	 * 
	 * @param value the <code>boolean</code> to convert
	 *
	 */
	public static String booleanToYesNo(boolean value) {
		return value ? "Yes" : "No";
	}

	public static Integer stringToInteger(String s) {

		if (s == null || s.trim().equals(""))
			return null;

		try {
			return new Integer(s);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * If o is null or o.toString(), "" is returned, otherwise o.toString() is returned.
	 * This eliminates null-checking before calling toString() on an object reference, and
	 * prevents 'null' from appearing when a null reference is used in a string
	 * concatenation expression (ex: string1 + string2 + string3).
	 */
	public static String makeString(Object o) {
		if (o == null)
			return "";
		else if (o.toString() == null)
			return "";
		else
			return o.toString();
	}

	/**
	 * Returns the defaultString if the object is null.
	 */
	public static String makeString(Object object, String defaultString) {
		if (object != null) {
			return object.toString();
		}
		return defaultString;
	}

	/**
	 * If s is null, null is returned, otherwise s.trim() is returned.
	 */
	public static String trim(String s) {
		if (s == null)
			return null;
		return s.trim();
	}

	/**
	 * Trims a string to the specified maximum length
	 * 
	 * @param s The string to trim.
	 * @param maxLength The maximum length to return. Must be greater than zero.
	 * @return The trimmed string that is no longer than the maxLength.
	 * @throws IllegalArgumentException when maxLength is less than 1
	 */
	public static String trimToLength(final String s, final int maxLength) {
		if (maxLength < 1) {
			throw new IllegalArgumentException("maxLength of " + maxLength + "is less than 1");
		}

		if (s == null) {
			return null;
		}

		StringBuilder trimmed = new StringBuilder(s.trim());
		if (trimmed.length() > maxLength) {
			return trimmed.substring(0, maxLength);
		}
		return trimmed.toString();
	}

	/**
	 * Change the first char of the input String to upper case
	 */
	public static String toUpperCaseFirst(String value) {
		if (value == null)
			return null;
		String inString = value.toString();
		int length = inString.length();
		if (length == 0)
			return value;
		String outString = inString.substring(0, 1).toUpperCase();
		if (length > 1) {
			outString = outString + inString.substring(1, length);
		}
		return outString;
	}

	/**
	 * Change the first char of the input String to lower case
	 */
	public static String toLowerCaseFirst(String value) {
		if (value == null)
			return null;
		String inString = value.toString();
		int length = inString.length();
		if (length == 0)
			return value;
		String outString = inString.substring(0, 1).toLowerCase();
		if (length > 1) {
			outString = outString + inString.substring(1, length);
		}
		return outString;
	}

	/**
	 * This will scan the String "base" and replace all occurrences of the wildcard
	 * character "wildCard" with the replacement string "replacement".
	 */
	public static String replaceWildCard(String base, char wildCard, String replacement) {
		if (base == null || base.trim().length() == 0)
			return base;
		StringBuffer result = new StringBuffer(80);
		for (int j = 0; j < base.length(); j++) {
			char c = base.charAt(j);
			if (c == wildCard) {
				result.append(replacement);
			} else {
				result.append(c);
			}
		}

		return result.toString();
	}

	/**
	 * Takes String and replaces the "find" string with the "replace" string
	 * 
	 * @param original, string to operate on
	 * @param find, string to look for, this must have a length > 0
	 * @param replace, string to replace the found string with
	 * @returns String, if no matches exist or params or null then the original string is
	 *          returned
	 */
	public static String findAndReplace(String original, String find, String replace, boolean ignoreCase) {

		if (original == null)
			return null;

		StringBuffer buffer = new StringBuffer(original);

		int match = 0;

		if (buffer == null || find == null || replace == null || find.length() <= 0)
			return original;

		for (int index = 0; index < buffer.length(); index++) {
			if (Character.toUpperCase(find.charAt(match)) == Character.toUpperCase(buffer.charAt(index))) {
				if (ignoreCase || (find.charAt(match)) == (buffer.charAt(index))) {
					match++;
					if (match == find.length()) {
						buffer.replace(index - match + 1, index + 1, replace);
						match = 0;
						index = index + replace.length() - find.length();
					}
				} else
					match = 0;
			} else
				match = 0;
		}
		return buffer.toString();
	}

	/**
	 * String Utility Method to find the first target match index in original string.
	 * this method is case insensitive match for a char and will preserve the original string's length
	 * @param originalStr the string that need to be matched
	 * @param target the target character
	 * @return the first matched char index, if it can not find, will return -1
	 */
	public static int indexOfIgnoreCase(String originalStr, char target) {
		int index;
		String targetStr = Character.toString(target);
		int indexLower = !originalStr.contains(targetStr.toLowerCase())
				? Integer.MAX_VALUE : originalStr.indexOf(targetStr.toLowerCase());
		int indexUpper = !originalStr.contains(targetStr.toUpperCase())
				? Integer.MAX_VALUE : originalStr.indexOf(targetStr.toUpperCase());
		if (indexLower == Integer.MAX_VALUE && indexUpper == Integer.MAX_VALUE) {
			index = -1;
		} else {
			index = Math.min(indexLower, indexUpper);
		}
		return index;
	}

	/**
	 * This will take a String ("value") and parse it into tokens using the given
	 * delimiter character. It returns a String[] array containing the found tokens. If
	 * the delimiter character occurs in two consecutive positions in the String, that is
	 * considered to be a null token. If only whitespace occurs between two delimiter
	 * characters, that is also considered a null token. If the first non-whitespace
	 * character in the String is the delimiter character, then the first token is
	 * considered to be a null token.
	 * <p>
	 *
	 * If maxTokens is a value greater than 0, then everything beyond the (maxTokens-1)th
	 * delimiter is treated as a single token, even if it also contains delimiter
	 * characters.
	 * <p>
	 *
	 * <PRE>
	 * Examples:
	 *
	 *     getTokens("Smith, John, Main St., Apt. 5", ',', 0);
	 *
	 * would return: {"Smith", "John", "Main St", "Apt. 5"}
	 *
	 *     getTokens(", , Main St., Apt. 5", ',', 3);
	 *
	 * would return: {null, null, "Main St., Apt. 5"}  // notice the effect of maxTokens = 3
	 *
	 *     getTokens("Smith,,Main St., Apt. 5", ',', 3);
	 *
	 * would return: {"Smith", null, "Main St., Apt. 5}
	 *
	 * </PRE>
	 * 
	 * Notice that each individual token is trimmed before it is placed into the array.
	 *
	 */
	public static String[] getTokens(String value, char delimiter, int maxTokens) {

		if (isNothing(value))
			return new String[0];

		String delim = String.valueOf(delimiter);

		StringTokenizer st = new StringTokenizer(value, delim, true);
		ArrayList<String> list = new ArrayList<String>();
		int j = 0;

		String lastToken = "";
		while (st.hasMoreTokens()) {

			// read the next token
			String token = st.nextToken();

			// if we've already collected maxTokens-1 tokens, everything else goes
			// into the last token.
			if (maxTokens > 0 && (j / 2) >= maxTokens - 1) {
				lastToken += token;
				continue;
			}

			token = token.trim();
			if ((j % 2) == 0) {
				if (token.equals(delim)) {
					list.add(null);
					j++;
				} else {
					if (token.length() == 0) {
						list.add(null);
					} else {
						list.add(token);
					}
				}
			}
			j++;
		}

		if (lastToken.length() > 0) {
			lastToken = lastToken.trim();
			list.add(lastToken);
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String getStackTraceAsString(Exception e) {
		ByteArrayOutputStream ostr = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(ostr));
		return ostr.toString();
	}

	/**
	 * Return the portion of the class name for the given object that is after all package
	 * qualifiers.
	 *
	 * @param obj An instance of any class
	 * @return The non-package portion of the Class name
	 */
	public static String getUnqualifiedClassName(Object obj) {
		String className = null;

		if (obj != null) {
			String fullClassName = obj.getClass().getName();
			className = StringUtility.getUnqualifiedClassName(fullClassName);
		}

		return className;
	}

	/**
	 * Return the portion of a class name (given as a String) after all package
	 * qualifiers.
	 *
	 * @param String The name of a Class in String form (as from Class.getName())
	 * @return The non-package portion of the Class name
	 */
	public static String getUnqualifiedClassName(String classNameStr) {
		String className = null;

		if (classNameStr != null) {
			int lastPeriod = classNameStr.lastIndexOf(".");
			if (lastPeriod > 0) {
				className = classNameStr.substring(lastPeriod + 1);
			} else {
				className = classNameStr;
			}
		}

		return className;
	}

	/**
	 * Get a substring, contained no more that given number of characters.
	 *
	 * @param str String to be trimmed
	 * @param num Maximum length of the string to be return
	 */

	public static String leftSubstring(String str, int num) {
		if (str == null)
			return null;
		if (str.length() < num)
			return str;
		else
			return str.substring(0, num);
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

	/**
	 * Compare two strings.
	 */
	public static int compareStrings(String a, String b) {
		int result = 0;
		boolean aIsNothing = isNothing(a);
		boolean bIsNothing = isNothing(b);

		if (aIsNothing && bIsNothing)
			result = 0;
		else if (aIsNothing)
			result = 1;
		else if (bIsNothing)
			result = -1;
		else
			result = a.compareTo(b);

		return result;
	}

	/**
	 * Regular formatName method is to format name in regular full name format
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param suffix
	 * 
	 * @return String fullName
	 */
	
	public static String formatName(final String firstName, final String middleName, final String lastName, final String suffix) {
		String fullName = null;
		StringBuilder builder = null;
		if (!isNothing(firstName)) {
			if (builder == null) {
				builder = new StringBuilder();
			}
			builder.append(firstName);
		}
		if (!isNothing(middleName)) {
			if (builder == null) {
				builder = new StringBuilder();
			} else {
				builder.append(" ");
			}
			builder.append(middleName);
		}
		if (!isNothing(lastName)) {
			if (builder == null) {
				builder = new StringBuilder();
			} else {
				builder.append(" ");
			}
			builder.append(lastName);
		}
		if (!isNothing(suffix)) {
			if (builder == null) {
				builder = new StringBuilder();
			} else {
				builder.append(" ");
			}
			builder.append(suffix);
		}
		if(builder!=null){
			fullName = StringUtility.trim(builder.toString());
		}
		return fullName;
	}
	
	/**
	 * Overloaded formatName is to format name with lastNameFirst in a comma separated
	 * Otherwise it will call general formatName method
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param suffix
	 * @param lastNameFirst
	 * 
	 * @return String fullName
	 */
	public static String formatName(final String firstName, final String middleName, final String lastName, final String suffix,
			final Boolean lastNameFirst) {
		String fullName = null;
		StringBuilder builder = null;
		if ((Boolean.TRUE).equals(lastNameFirst)) {
			if (!isNothing(lastName)) {
				if (builder == null) {
					builder = new StringBuilder();
				}
				builder.append(lastName);
			}
			if (!isNothing(firstName)) {
				if (builder == null) {
					builder = new StringBuilder();
				} else {
					builder.append(", ");
				}
				builder.append(firstName);
			}
			if (!isNothing(middleName)) {
				if (builder == null) {
					builder = new StringBuilder();
				} else {
					if (!isNothing(firstName)) {
						builder.append(" ");
					} else {
						builder.append(", ");
					}
				}
				builder.append(middleName);
			}
			if (!isNothing(suffix)) {
				if (builder == null) {
					builder = new StringBuilder();
				} else {
					if (!isNothing(firstName) || !isNothing(middleName)) {
						builder.append(" ");
					} else {
						builder.append(", ");
					}
				}
				builder.append(suffix);
			}
			if(builder!=null){
				fullName = StringUtility.trim(builder.toString());
			}
		} else {
			fullName = formatName(firstName, middleName, lastName, suffix);
		}
		return fullName;
	}

	/**
	 * Add "prefix" to the overloaded formatName method with lastNameFirst option.
	 * Append ", prefix" to the end of fullName if lastNameFist is true, otherwise append "prefix" in front of the fullName
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param suffix
	 * @param prefix
	 * @param lastNameFirst
	 * @return
	 */
	public static String formatName(final String firstName, final String middleName, final String lastName,
									final String prefix, final String suffix, final Boolean lastNameFirst) {
		String fullName = null;
		if (isNothing(prefix)) {
			fullName = formatName(firstName, middleName, lastName, suffix, lastNameFirst);
		} else {
			if ((Boolean.TRUE).equals(lastNameFirst)) {
				fullName = formatName(firstName, middleName, lastName, suffix, lastNameFirst);
			} else {
				fullName = formatName(firstName, middleName, lastName, suffix);
			}
			if (isNothing(fullName)) {
				fullName = prefix;
			} else if (Boolean.TRUE.equals(lastNameFirst)) {
				fullName = fullName + ", " + prefix;
			} else {
				fullName = prefix + " " + fullName;
			}
		}
		return fullName;
	}

	/**
	 * Returns a String representation of a decimal value based on the DecimalFormat
	 * provided as a parameter.
	 * 
	 * @param toFormat Number containing the value to format
	 * @param decimalFormat DecimalFormat to use
	 * @param defaultIfNull the value to return if toFormat is null
	 * @return formatted value
	 */
	public static String formatDecimalValue(Number toFormat, DecimalFormat decimalFormat, String defaultIfNull) {
		if (toFormat == null)
			return defaultIfNull;

		return decimalFormat.format(toFormat.doubleValue());
	}

	public static String ltrim(String str, String charToBeTrimmed) {
		if (isNothing(str) || isNothing(charToBeTrimmed))
			return str;
		// find first non charToBeTrimmed on left side of the string
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			if (!charToBeTrimmed.equals(str.substring(i, i + 1))) {
				index = i;
				break;
			}
		}

		return str.substring(index);
	}

	public static boolean isNumeric(String toTest) {
		boolean isNumeric = false;
		if(toTest != null) {
			isNumeric = Pattern.matches("\\d+", toTest);
		}
		return isNumeric;

	}

	/**
	 * Format NDC number as xxxxx-xxxx-xx
	 * 
	 * @param ndc return ndc number with hyphens i.e. xxxxx-xxxx-xx
	 * @return
	 */
	public static String formatNDCToDisplay(String ndc) {
		if ((ndc != null) && (ndc.length() == 11)) {
			StringBuffer buf = new StringBuffer(13);
			buf.append(ndc.substring(0, 5)).append("-");
			buf.append(ndc.substring(5, 9)).append("-").append(ndc.substring(9));
			ndc = buf.toString();
		}

		return ndc;
	}

	/**
	 * Formats social security number for display as xxx-xx-xxxx.
	 * 
	 * @param ssn SSN number with no hyphens
	 * @return Formatted SSN
	 */
	public static String formatSSN(String ssn) {
		if (ssn != null && ssn.length() == 9) {
			StringBuilder sb = new StringBuilder(11);
			sb.append(ssn.substring(0, 3)).append("-").append(ssn.substring(3, 5)).append("-").append(ssn.substring(5));
			ssn = sb.toString();
		}
		return ssn;
	}

	/**
	 * Takes a phone number as a ten digit string and formats it for display.
	 * 
	 * @param tenDigitString
	 * @return Formatted phone number or original input is input was not a String of
	 *         length 10.
	 */
	public static String formatPhoneNumber(String tenDigitString) {
		if (tenDigitString == null || tenDigitString.length() != 10)
			return tenDigitString;
		return "(" + tenDigitString.substring(0, 3) + ") " +
				tenDigitString.substring(3, 6) +
				"-" + tenDigitString.substring(6);
	}

	/*
	 * public static String capWords(String s) { if (isNothing(s)) { return s; } else {
	 * StringBuffer sb = new StringBuffer(); StringTokenizer st = new StringTokenizer(s,
	 * " "); while (st.hasMoreTokens()) { String token = st.nextToken(); if (isNothing(s))
	 * { if (sb.length() > 0) { sb.append(" "); } } else { if (sb.length() > 0) {
	 * sb.append(" "); } sb.append(Character.toUpperCase(token.charAt(0))); if
	 * (token.length() > 1) { sb.append(token.substring(1).toLowerCase()); } } } return
	 * sb.toString(); } }
	 */

	/**
	 * Wraps certain characters in an input string in prefixes and suffixes to handle
	 * situations where special characters need to be escaped and similar situations. For
	 * example, to escape all quotation marks with a backslash character, a call such as
	 * inputString = wrapSpecialCharacters(inputString, new char[] {'"'}, "\\", null)
	 * would be used.
	 *
	 * @param inputString String potentially containing characters in need of wrapping
	 * @param charactersToWrap Array of characters which should be wrapped in prefix and
	 *            suffix
	 * @param escapePrefix Prefix to place before characters needing wrapped (may be null)
	 * @param escapeSuffix Suffix to place after characters needing wrapped (may be null)
	 * @return String with special characters wrapped
	 */
	public static String wrapSpecialCharacters(String inputString, char[] charactersToWrap, String escapePrefix, String escapeSuffix) {
		// If the input is nothing or zero length or both the escape prefix and suffix are
		// nothing, just return the input.
		// Not using isNothing on the input because it's possible that the user may wish
		// to escape space characters, in which case
		// a string containing only space characters would still be eligible for escaping.
		if (isNothing(inputString) || (isNothing(escapePrefix) && isNothing(escapeSuffix)) || isNothing(charactersToWrap)) {
			return inputString;
		}

		if (escapePrefix == null) {
			escapePrefix = "";
		}
		if (escapeSuffix == null) {
			escapeSuffix = "";
		}

		StringBuilder buffer = new StringBuilder(inputString);
		for (int inputIndex = buffer.length() - 1; inputIndex >= 0; inputIndex--) {
			for (int escapeCharIndex = 0; escapeCharIndex < charactersToWrap.length; escapeCharIndex++) {
				if (buffer.charAt(inputIndex) == charactersToWrap[escapeCharIndex]) {
					buffer.insert(inputIndex + 1, escapeSuffix);
					buffer.insert(inputIndex, escapePrefix);
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * Truncates the loyalty number to 11 digits
	 * 
	 * @param loyaltyNum The loyalty number
	 * @return The loyalty number truncated to no more than 11 digits. If the loyalty
	 *         number is less than 11 digits or null, the input value is returned
	 *         unchanged
	 */
	public static String truncateLoyaltyNum(String loyaltyNum) {
		return (!StringUtility.isNothing(loyaltyNum) && loyaltyNum.length() > 11) ? loyaltyNum.substring(0, 11) : loyaltyNum;
	}

	/**
	 * This method takes code and description and returns code after separating based on
	 * the "=" delimiter
	 * 
	 * @param codeDescription
	 * @return String
	 */
	public static String getCode(String codeAndDescription) {
		String[] code;
		/* delimiter */
		String delimiter = "=";
		/* given string will be split by the argument delimiter provided. */
		if (codeAndDescription != null) {
			code = codeAndDescription.split(delimiter);
			/* print substrings */
			for (int i = 0; i < code.length; i++) {
				codeAndDescription = code[0];
			}
		}
		return codeAndDescription;
	}

	/**
	 * Converts Extended ASCII Character to Printable ASCII in a String
	 * 
	 * @param message
	 * @return String
	 */
	public synchronized static String extendedASCIIToPrintableASCII(String message) {
		final StringBuffer stringBuffer = new StringBuffer(message.length() * 2);
		final StringCharacterIterator iterator = new StringCharacterIterator(message);

		char currentCharacter = iterator.current();

		while (currentCharacter != StringCharacterIterator.DONE) {
			int asciiValue = (int) currentCharacter;

			if (asciiValue >= 32
					&& asciiValue <= 127) {
				stringBuffer.append(currentCharacter);
			} else {
				boolean isExtendedASCII = false;
				if (asciiValue >= 128
						&& asciiValue <= 255) {
					if (asciiValue == 138
							|| asciiValue == 154) {
						stringBuffer.append("S");
						isExtendedASCII = true;
					}
					if (asciiValue == 142
							|| asciiValue == 158) {
						stringBuffer.append("Z");
						isExtendedASCII = true;
					}
					if ((asciiValue >= 192 && asciiValue <= 197)
							|| (asciiValue >= 224 && asciiValue <= 229)) {
						stringBuffer.append("A");
						isExtendedASCII = true;
					}
					if (asciiValue == 198
							|| asciiValue == 230) {
						stringBuffer.append("AE");
						isExtendedASCII = true;
					}
					if (asciiValue == 199
							|| asciiValue == 231) {
						stringBuffer.append("C");
						isExtendedASCII = true;
					}
					if ((asciiValue >= 200 && asciiValue <= 203)
							|| (asciiValue >= 232 && asciiValue <= 235)) {
						stringBuffer.append("E");
						isExtendedASCII = true;
					}
					if ((asciiValue >= 204 && asciiValue <= 207)
							|| (asciiValue >= 236 && asciiValue <= 239)) {
						stringBuffer.append("I");
						isExtendedASCII = true;
					}
					if (asciiValue == 208
							|| asciiValue == 240) {
						stringBuffer.append("D");
						isExtendedASCII = true;
					}
					if (asciiValue == 209
							|| asciiValue == 241) {
						stringBuffer.append("N");
						isExtendedASCII = true;
					}
					if ((asciiValue >= 210 && asciiValue <= 214)
							|| (asciiValue >= 242 && asciiValue <= 246) || asciiValue == 216
							|| asciiValue == 248) {
						stringBuffer.append("O");
						isExtendedASCII = true;
					}
					if ((asciiValue >= 217 && asciiValue <= 220)
							|| (asciiValue >= 249 && asciiValue <= 252)) {
						stringBuffer.append("U");
						isExtendedASCII = true;
					}
					if (asciiValue == 159
							|| asciiValue == 221 || asciiValue == 253 || asciiValue == 255) {
						stringBuffer.append("Y");
						isExtendedASCII = true;
					}
					if (!isExtendedASCII) {
						stringBuffer.append("");
					}
				}
				if (asciiValue >= 0
						&& asciiValue <= 31 && !isExtendedASCII) {
					stringBuffer.append(" ");
				}
			}
			currentCharacter = iterator.next();
		}
		return stringBuffer.toString();
	}

	/**
	 * Given a String containing an NDC value (with or without hyphens), splits the String
	 * into the manufacturer, product and size components.
	 * 
	 * @param ndcString NDC to split
	 * @return Array of Strings where the first element contains the manufacturer, second
	 *         is the product and third is the size
	 */
	public static String[] splitNDCString(String ndcString) {
		if (ndcString == null) {
			return null;
		}

		ndcString = remove(ndcString, '-');

		if (ndcString.length() != 11 || !StringUtility.isNumeric(ndcString)) {
			return null;
		}

		String[] splitArr = new String[3];
		splitArr[0] = ndcString.substring(0, 5);
		splitArr[1] = ndcString.substring(5, 9);
		splitArr[2] = ndcString.substring(9, 11);
		return splitArr;
	}

	/**
	 * Validating given email address
	 * 
	 * @param emailId to validate
	 * @return Weather email id is valid or not.
	 */
	public static boolean isEmailValid(String email) {
		if (email == null || email.length() == 0)
			return true;
		email = email.trim();

		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	/**
	 * Replaces all occurrences of a set of tokens with the associated values provided in
	 * a Map. This method also can handle situations where certain tokens activate a
	 * comment block and proper nesting is important (e.g. in XML, nesting comment blocks
	 * doesn't work, so if there are two pairs of tokens that both can start and end a
	 * comment block, nesting those tags will produce undesirable results).
	 * 
	 * For the simple use case, pass null for both toggleOnSuffix and toggleOffSuffix. For
	 * use cases where nesting needs to be avoided, provide the "on" and "off" suffix
	 * values to use. For example, consider the following tokens: cond1_comment_on,
	 * cond1_comment_off, cond2_comment_on and cond2_comment_off. The _on tokens are
	 * assigned a value of "&lt;!--" and the _off tokens are assigned a value of "--&gt;"
	 * (in other words, the start and stop markers for comments in an XML document). Now
	 * consider the following input text:
	 * 
	 * <pre>
	 * &lt;someXml&gt;
	 * 	&lt;aTag/&gt;
	 * 	&#64;cond1_comment_on@&lt;aTag/&gt;
	 * 	&#64;cond2_comment_on@&lt;aTag/&gt;@cond2_comment_off@
	 * 	&lt;aTag/&gt;@cond1_comment_off@
	 * &lt;/someXml&gt;
	 * </pre>
	 * 
	 * In that scenario, we need to ignore the cond2 tokens since they are already nested
	 * inside a comment block. To achieve that, pass "_on" as the toggleOnSuffix value and
	 * "_off" as the toggleOffSuffix value. That will cause this method to ignore all
	 * tokens ending with either suffix value until the matching "off" token is found for
	 * the token that started the comment block.
	 * 
	 * @param inputText Text containing tokens to be replaced
	 * @param tokenDelimiter Character that identifies/encloses tokens (e.g. @myToken@ has
	 *            '@' as the delimiter)
	 * @param tokenValues Map keyed by token names (without delimiters) containing the
	 *            replacement values
	 * @param toggleOnSuffix null for simple use case; see method description for details
	 * @param toggleOffSuffix null for simple use case; see method description for details
	 * @return
	 */
	public static String replaceTokens(String inputText, char tokenDelimiter, Map<String, String> tokenValues, String toggleOnSuffix,
			String toggleOffSuffix) {
		Pattern tokenPattern = Pattern.compile(tokenDelimiter + "(.+?)" + tokenDelimiter);
		Matcher tokenMatcher = tokenPattern.matcher(inputText);
		StringBuffer sb = new StringBuffer();
		String toggleOffToken = null;
		while (tokenMatcher.find()) {
			String token = tokenMatcher.group(1);
			String replacementValue = tokenValues.get(token);
			tokenMatcher.appendReplacement(sb, "");
			if (toggleOnSuffix != null && toggleOffSuffix != null) {
				// Logic for handling comment toggles
				if (toggleOffToken != null && token.equals(toggleOffToken)) {
					toggleOffToken = null;
				}
				if (toggleOffToken != null && (token.endsWith(toggleOnSuffix) || token.endsWith(toggleOffSuffix))) {
					replacementValue = "";
				}
				if (toggleOffToken == null && token.endsWith(toggleOnSuffix) && !StringUtility.isNothing(replacementValue)) {
					toggleOffToken = token.substring(0, token.lastIndexOf(toggleOnSuffix)) + toggleOffSuffix;
				}
			}
			sb.append(replacementValue);
		}
		tokenMatcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Convenience method for simple use case of replaceTokens(String, char, Map, String,
	 * String).
	 * 
	 * @param inputText
	 * @param tokenDelimiter
	 * @param tokenValues
	 * @return
	 */
	public static String replaceTokens(String inputText, char tokenDelimiter, Map<String, String> tokenValues) {
		return replaceTokens(inputText, tokenDelimiter, tokenValues, null, null);
	}

	/**
	 * Line wraps text in a String to a certain line length with prefix and suffix Strings
	 * delimiting each line. Words in the input should be delimited by space characters.
	 * Note that words which exceed the maximum line length will be unbroken on a line by
	 * themselves.
	 * 
	 * @param inputStr The String to be line wrapped
	 * @param maxLineLength The maximum length each line should be after wrapping
	 * @param linePrefix The String to prepend before each wrapped line - usually null or
	 *            the empty string
	 * @param lineSuffix The String to append after each wrapped line - usually a newline
	 *            character
	 * @return Line wrapped text
	 */
	public static String wrapLinesToLength(String inputStr, int maxLineLength, String linePrefix, String lineSuffix) {
		if (StringUtility.isNothing(inputStr))
			return inputStr;
		if (linePrefix == null)
			linePrefix = EMPTY_STRING;
		if (lineSuffix == null)
			lineSuffix = EMPTY_STRING;
		StringBuilder wrappedContent = new StringBuilder(inputStr.length() * 2);
		StringBuilder currentWord = new StringBuilder(maxLineLength);
		StringBuilder currentLine = new StringBuilder(maxLineLength);

		try {
			for (int i = 0; i < inputStr.length(); i++) {
				char c = inputStr.charAt(i);
				currentWord.append(c);
				if (c == ' ') {
					if (currentLine.length() + currentWord.length() > maxLineLength) {
						wrappedContent.append(linePrefix);
						wrappedContent.append(currentLine.toString().trim());
						wrappedContent.append(lineSuffix);
						currentLine.setLength(0);
					}
					currentLine.append(currentWord.toString());
					currentWord.setLength(0);
				}
			}
			wrappedContent.append(linePrefix);
			wrappedContent.append(currentLine.toString());
			if (currentLine.length() + currentWord.length() > maxLineLength) {
				wrappedContent.append(lineSuffix);
				wrappedContent.append(linePrefix);
			}
			wrappedContent.append(currentWord.toString());
			wrappedContent.append(lineSuffix);

			return wrappedContent.toString();
		} catch (Throwable t) {
			// If anything at all goes wrong, just return the input string.
			return inputStr;
		}
	}

	/**
	 * Given a single String, split it into lines, returned as an array.
	 * 
	 * @param inStr Input String
	 * @param lineLength Max length of a line
	 * @param numPieces Number of lines to return (note that extra text will be discarded)
	 * @return
	 */
	public static String[] splitStringIntoLines(String inStr, int lineLength, int numPieces) {
		String[] pieces = new String[numPieces];
		if (inStr == null)
			return null;
		int index = 0;
		int piece = 0;
		for (int i = 0; i < numPieces; i++) {
			int lastIndex = lineLength + index;
			if (lastIndex > inStr.length())
				lastIndex = inStr.length();

			String result = inStr.substring(index, inStr.length());
			if (result.length() > lineLength) {
				int lastSpace = inStr.lastIndexOf(" ", lastIndex);
				if (lastSpace != -1) {
					lastIndex = lastSpace + 1;
				}
				result = inStr.substring(index, lastIndex);
			}
			index = lastIndex;
			pieces[piece++] = result;
		}
		return pieces;
	}

	/**
	 * Splits a version number string (e.g. 13.0.3.12345) into an array of int values.
	 * This method handles strings with 1 to 4 segments (e.g. 13, 13.0, 13.0.3 and
	 * 13.0.3.12345). A non-parsable segment will be interpreted as a zero.
	 * 
	 * @param versionStr Version number string (e.g. 13.0.3.12345)
	 * @return Array of int values corresponding to segments of the input String (e.g.
	 *         [13, 0, 3, 12345])
	 */
	public static int[] splitVersionString(String versionStr) {
		String[] stringElements = versionStr.split("\\.");
		int[] versionElements = new int[stringElements.length];
		for (int index = Math.min(stringElements.length - 1, 3); index >= 0; index--) {
			try {
				versionElements[index] = Integer.parseInt(stringElements[index], 10);
			} catch (NumberFormatException e) {
				versionElements[index] = 0;
			}
		}
		return versionElements;
	}

	/**
	 * Removes white space from both ends of this string and converts all of the
	 * characters in this string to upper case.
	 * 
	 * @param value String to manipulate
	 * @return String value with trim(without spaces at right and at left) and in
	 *         UpperCase or value itself.
	 */
	public static String trimStringAndConvertToUpperCase(String value) {
		if (value == null
				|| value.trim().length() == 0) {
			return value;
		} else {
			return value.trim().toUpperCase();
		}
	}

	/**
	 * Given an array of bytes, converts it to a hex string representation. Adapted from
	 * http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in
	 * -java
	 * 
	 * @param bytes Array of byte values
	 * @return String representation of the byte value array as hex digits
	 */
	public static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String byteArrayToHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	private static final Pattern HEX_DIGIT_PATTERN = Pattern.compile("^[0-9A-Fa-f]*$");

	/**
	 * Given a hexadecimal string, converts it to an array of bytes. This method is
	 * essentially is the opposite of the byteArrayToHexString(byte[]) method.
	 * 
	 * @param hexString String to convert
	 * @return Array of bytes
	 */
	public static byte[] hexStringToByteArray(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0 || HEX_DIGIT_PATTERN.matcher(hexString).matches() == false) {
			throw new IllegalArgumentException(
					"Input string must not be null, must have an even number of characters and all characters must be hex digits (0 - F).");
		}
		byte[] byteArr = new byte[hexString.length() / 2];
		for (int bArrIndex = byteArr.length - 1; bArrIndex >= 0; bArrIndex--) {
			byteArr[bArrIndex] = (byte) ((((byte) Character.digit(hexString.charAt(bArrIndex * 2), 16)) << 4)
					+ ((byte) Character.digit(hexString.charAt((bArrIndex * 2) + 1), 16)));
		}
		return byteArr;
	}

	/**
	 * Wraps a given string in single quotation marks making it appropriate for use in a
	 * SQL statement.
	 * 
	 * @param s String to wrap
	 * @return Wrapped String
	 */
	public static String wrapStringForSQL(String s) {
		return "'" + s + "'";
	}

	/**
	 * Given an 8 digit facility ID string (e.g. 01400351), returns the division portion
	 * as a String.
	 * 
	 * @param facId
	 * @return Division portion of a facility ID String.
	 * @throws IllegalArgumentException if an invalid facility ID is passed in.
	 */
	public static String extractDivIdFromFacilityId(String facId) {
		if (isNothing(facId) || facId.length() != 8) {
			throw new IllegalArgumentException("Invalid facility ID");
		}
		return facId.substring(0, 3);
	}

	/**
	 * Given an 8 digit facility ID string (e.g. 01400351), returns the store portion as a
	 * String.
	 * 
	 * @param facId
	 * @return Store portion of a facility ID String.
	 * @throws IllegalArgumentException if an invalid facility ID is passed in.
	 */
	public static String extractStoreIdFromFacilityId(String facId) {
		if (isNothing(facId) || facId.length() != 8) {
			throw new IllegalArgumentException("Invalid facility ID");
		}
		return facId.substring(3);
	}
	
	/**
	 * Given an 8 digit facility ID string (e.g. 01400351), returns the 3-digit store number as a
	 * String.
	 * 
	 * @param facId
	 * @return Store portion of a facility ID String.
	 * @throws IllegalArgumentException if an invalid facility ID is passed in.
	 */
	public static String getThreeDigitStoreNumFromFacilityId(String facId) {
		if (isNothing(facId) || facId.length() != 8) {
			throw new IllegalArgumentException("Invalid facility ID");
		}
		return facId.substring(5);
	}

	/**
	 * Given a string return substring until the first encountered delimiter
	 * 
	 * @param fullString
	 * @param delimiter
	 * @return String Value from 0 to delimiter
	 */
	public static String findPrefix(String fullString, String delimiter) {

		if (isNothing(delimiter)) {
			throw new IllegalArgumentException("No Delimiter passed");
		}
		return ((fullString.split(delimiter, 2))[0]).trim();
	}

	/**
	 * Given a string returns a partially masked string.
	 * <p>
	 * To guarantee the string will be masked we validate that String.length() is larger
	 * than numChars multiply by two. When length is still smaller we set numChars to 0
	 * and mask the entire string. When input is null the method will return a null value.
	 * </p>
	 * 
	 * @param input String value to mask
	 * @param numChars Number of characters to not mask starting from 0. Value must be a
	 *            positive number
	 * @param maskValue Replacement character that will be used to mask input
	 * @return String masked by maskValue
	 * @throws IllegalArgumentException When a $ or \ is passed as maskValue. An
	 *             IllegalArgumentException will also be thrown when a null or empty
	 *             string is passed to maskValue
	 */
	public static String maskStringToNumCharacters(String input, int numChars, String maskValue) {
		if (isNothing(input)) {
			return input;
		}
		if (isNothing(maskValue)) {
			throw new IllegalArgumentException("maskValue cannot accept a null or empty string");
		} else if (maskValue.contains("$") || maskValue.contains("\\")) {
			throw new IllegalArgumentException("maskValue cannot be a $ or \\");
		}
		if (numChars < 0) {
			throw new IllegalArgumentException("numChars must be a positive integer");
		}
		if (input.length() <= numChars * 2) {
			numChars = 0;
		}
		return input.substring(0, numChars) + input.substring(numChars).replaceAll("[\\w]", maskValue);
	}

	/**
	 * returns true if given substring exists in delimiter separated string
	 * 
	 * @param input String value
	 * @param currentValue current value of the attribute
	 * @param delimiterString delimiter
	 * @return boolean
	 */
	public static boolean valueExistsInDelimitedString(String input, String currentValue, String delimiterString) {
		if (!StringUtility.isNothing(currentValue)) {
			StringTokenizer st = new StringTokenizer(input, delimiterString);
			while (st.hasMoreElements()) {
				String value = st.nextElement().toString();
				if (!StringUtility.isNothing(value) && currentValue.equalsIgnoreCase(StringUtility.trim(value))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * returns true if given Number exists in delimiter separated string
	 * 
	 * @param appOptionString values from the app option
	 * @param currentValue current value of the attribute
	 * @param delimiterString delimiter
	 * @return boolean
	 */
	public static boolean numberExistsInDelimitedString(String appOptionString, Number currentValue, String delimiterString) {
		if (!StringUtility.isNothing(currentValue)) {
			StringTokenizer st = new StringTokenizer(appOptionString, delimiterString);
			while (st.hasMoreElements()) {
				String valueStr = st.nextElement().toString();
				if (!StringUtility.isNothing(valueStr) && StringUtility.isNumeric(StringUtility.trim(valueStr))) {
					long valueLong = Long.parseLong(StringUtility.trim(valueStr));
					if (currentValue.longValue() == valueLong) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Main Method to test any method in the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Object object = new String();
		String name = StringUtility.getUnqualifiedClassName(object);
		System.out.println(name);
		System.out.println(StringUtility.getUnqualifiedClassName(null));
	}

	/**
	 * Converts all the whitespace separated words in a String into capitalized words,
	 * that is each word is made up of a title case character and then a series of lower
	 * case characters.
	 */
	public static String capitalizeFully(String str) {
		return capitalizeFully(str, null);
	}
	
	/**
	 * Converts all the whitespace separated words in a String into capitalized words,
	 * that is each word is made up of a title case character and then a series of
	 * lower case characters.
	 */
	public static String capitalizeFully(String str, char[] delimiters) {
		int delimLen = (delimiters == null ? -1 : delimiters.length);
		if (str == null || str.length() == 0 || delimLen == 0) {
			return str;
		}
		str = str.toLowerCase();
		return capitalize(str, delimiters);
	}

	/**
	 * Capitalizes all the delimiter separated words in a String. Only the first letter of
	 * each word is changed.
	 */
	public static String capitalize(String str, char[] delimiters) {
		int delimLen = (delimiters == null ? -1 : delimiters.length);
		if (str == null || str.length() == 0 || delimLen == 0) {
			return str;
		}
		
		int strLen = str.length();
		StringBuffer buffer = new StringBuffer(strLen);
		boolean capitalizeNext = true;
		
		for (int i = 0; i < strLen; i++) {
			char ch = str.charAt(i);

			if (isDelimiter(ch, delimiters)) {
				buffer.append(ch);
				capitalizeNext = true;
			} else if (capitalizeNext) {
				buffer.append(Character.toTitleCase(ch));
				capitalizeNext = false;
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	/**
	 * Is the character a delimiter.
	 *
	 * @param ch the character to check
	 * @param delimiters the delimiters
	 * @return true if it is a delimiter
	 */
	private static boolean isDelimiter(char ch, char[] delimiters) {
		if (delimiters == null) {
			return Character.isWhitespace(ch);
		}
		
		for (int i = 0, isize = delimiters.length; i < isize; i++) {
			if (ch == delimiters[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validating given URL
	 * 
	 * @param URL to validate
	 * @return Weather URL valid or not.
	 */
	public static boolean isUrlValid(String url) {
		if (isNothing(url)) {
			return false;
		}
		url = url.trim();
		
		Matcher urlMatcher = URL_PATTERN.matcher(url);
		return isStringWithOutSpaces(url) && urlMatcher.matches();
	}

	/**
	 * Validating if the given string is with space or not.
	 * 
	 * @param input to validate
	 * @return true when input string has space.
	 */
	public static boolean isStringWithOutSpaces(String input) {
		if(isNothing(input)) {
			return false;
		}
		return NO_SPACE.matcher(input).matches();
	}

	
	/**
	 * Converts a String object to a Long Object.
	 * 
	 * @param stringValue
	 * @return Long
	 */
	public static Long toLong(String stringValue) {
		return toLong(stringValue, null);
	}

	/**
	 * Converts a String object to a stringValue Object.
	 * If default value is null it returns null otherwise it converts string passed to Long. 
	 * 
	 * @param stringValue
	 * @param defaultVal
	 * @return Long
	 */
	public static Long toLong(String stringValue, Long defaultVal) { 

		if (StringUtility.isNothing(stringValue) || !StringUtility.isNumeric(stringValue)) {
			return defaultVal;
		}

		return Long.valueOf(stringValue);
	}

	/**
	 * Converts a String object to a String with HTML tag value if it consist of a valid
	 * URL. If default value is null it returns null otherwise it converts string passed.
	 * 
	 * @param stringText
	 * @return String
	 * @throws IOException while getting the host from the URL passed
	 */
	public static String convertStringToHtmlTagHyperlink(String stringText) throws IOException {
		if (!isNothing(stringText)) {
			String stringToHtmlTagHyperlink = EMPTY_STRING;
			String replacedStringText = stringText.replaceAll(",", SPACE);
			ArrayList<String> textList = new ArrayList<String>(Arrays.asList(replacedStringText.split("\\s+")));

			for (String textVal : textList) {
				String replacedStringTextAtIndex = textVal;
				if (isUrlValid(textVal)) {
					if (textVal.toLowerCase().startsWith("www")) {
						replacedStringTextAtIndex = "https://" + textVal;
					}
					URL hyperLinkText = new URL(replacedStringTextAtIndex);
					String hostName = hyperLinkText.getHost();
					replacedStringTextAtIndex = "<A HREF='" + replacedStringTextAtIndex + "'>" + hostName + "</A>";
				}
				stringToHtmlTagHyperlink += replacedStringTextAtIndex + SPACE;
			}
			return stringToHtmlTagHyperlink;
		}
		return stringText;
	}

	public static boolean isStringExistingInAnotherString(String inputStr, String str, String delimiter) {
		if (!StringUtility.isNothing(inputStr) && !StringUtility.isNothing(str) && !StringUtility.isNothing(delimiter)) {
			List<String> strings = Arrays.asList(inputStr.split(delimiter));

			for (String string : strings) {
				if (string.trim().equalsIgnoreCase(str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Efficently converts java.sql.Clob into a string
	 * @param clob
	 * @return String
	 */
	public static String clobToString(Clob clob) {
		String rtnString = null;
		if (clob != null) {
			try {
				Reader reader = null;
				reader = clob.getCharacterStream();
				char[] arr = new char[8 * 1024];
				StringBuilder buffer = new StringBuilder();
				int numCharsRead;
				while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
					buffer.append(arr, 0, numCharsRead);
				}
				reader.close();
				rtnString = buffer.toString();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return rtnString;
	}

	/**
	 * Given a String containing the NDC that must be 11-digit with or without hyphens, or
	 * any digit NDC that must contains hyphens, splits the String into the manufacturer,
	 * product and size components.
	 * 
	 * @param ndcString NDC to split
	 * @return Array of Strings where the first element contains the manufacturer, second
	 *         is the product and third is the size
	 */
	public static String[] splitGenericNDCString(String ndcString) {
		if (ndcString == null) {
			return null;
		}
		String[] strArray = splitNDCString(ndcString);
		if (strArray != null) {
			return strArray;
		}
		strArray = Pattern.compile("-").split(ndcString);
		if (strArray.length > 0) {
			strArray[0] = getSegment(strArray[0], 5);
		}
		if (strArray.length > 1) {
			strArray[1] = getSegment(strArray[1], 4);
		}
		if (strArray.length > 2) {
			strArray[2] = getSegment(strArray[2], 2);
		}
		return strArray;
	}

	/**
	 * Check if the length of an input string s is the same as the given size. If true,
	 * return the string s. Otherwise, create and return a new string by adding zeros up
	 * to the given size before string s.
	 * 
	 * @param s
	 * @param size
	 * @return string
	 */
	private static String getSegment(String s, int size) {
		while (s.length() < size) {
			s = "0" + s;
		}
		return s;
	}
	
	/**
	 * Convert a String to Ellipsis String for given no. of characters.
	 * 
	 * @param text          - The String to Ellipse.
	 * @param maxCharacters - The left after which Ellipse will be added.
	 * @return Ellipsis text if applicable, else the original text as String.
	 */
	public static String createEllipseText(String text, int maxCharacters) {
		if (!isNothing(text) && text.length() > maxCharacters) {
			return text.substring(0, maxCharacters) + ELLIPSE;
		} else {
			return text;
		}
	}
}