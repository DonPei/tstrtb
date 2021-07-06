package edu.uth.swing.plaf.laf;

/**
 * This class establishes the Look and Feel of TRex-One Enterprise. All colors and
 * UIDelegates whould be specified here. Global changes to LookAndFeel is implemented
 * here.
 *
 * NOTE: TComponents often specify colors, insets, etc. and margainlize the usefullness of
 * this class. Over time, all display characteristics should be removed from the
 * TComponents where they are not needed for specific appearence. JComponents will offer a
 * more "pure" look and feel and smaller memory footprint.
 *
 * cobrien 3/10/2003
 */

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.DefaultEditorKit;

public class GhcLookAndFeel extends BasicLookAndFeel {

	private static final long serialVersionUID = -1;

	public static Color MENU_LINE_COLOR = new Color(50, 200, 50);

	public static Color MEDIUM_BACKGROUND_COLOR = new Color(214, 207, 189);
	public static Color DARK_BACKGROUND_COLOR = new Color(189, 170, 140);
	public static Color LIGHT_BACKGROUND_COLOR = new Color(236, 233, 223);
	public static Color CONTENT_BACKGROUND_COLOR = new Color(214, 207, 189);
	public static Color CONTENT_LIGHT_BACKGROUND_COLOR = new Color(236, 233, 223);
	public static Color BLANK_BACKGROUND_COLOR = new Color(189, 170, 140);
	public static Color BORDER_HIGHLIGHT_COLOR = new Color(236, 233, 223);
	public static Color BORDER_INNER_HIGHLIGHT_COLOR = new Color(214, 207, 189);
	public static Color BORDER_INNER_SHADOW_COLOR = new Color(189, 170, 140);
	public static Color TABLE_BACKGROUND_COLOR = new Color(236, 233, 223);

	// COLORS
	public static Color BORDER_SHADOW_COLOR = new Color(129, 112, 75);
	public static Color FOCUS_FOREGROUND_COLOR = new Color(51, 102, 204);
	public static Color LOGIN_LABEL_HIGHLIGHT_COLOR = new Color(0, 51, 102);
	public static Color TITLE_BACKGROUND_COLOR = new Color(102, 153, 204);
	public static Color REQUIRED_FIELD_INDICATOR_COLOR = new Color(255, 0, 0);
	public static Color SELECTION_BACKGROUND_COLOR = new Color(189, 170, 140);
	public static Color TABLE_SELECTED_DKBLUE_COLOR = new Color(0, 51, 102);
	public static Color DISABLED_TEXT_COLOR_COLOR = new Color(80, 80, 80);
	public static Color TEXT_COLOR_COLOR = Color.black;
	public static Color BUTTON_BORDER_HIGHLIGHT_COLOR = Color.white;
	public static Color PRACTICE_SYSTEM_YELLOW_COLOR = new Color(255, 255, 0);
	public static Color TITLE_BACKGROUND_GREEN_COLOR = new Color(129, 112, 75);;
	public static Color WHITE_BACKGOUND_COLOR = Color.WHITE;
	public static Color YELLOW_COLOR_COLOR = Color.YELLOW;
	public static Color IMAGE_BACKGROUND_COLOR = new Color(153, 153, 153);

	// TREX CONSTANTS
	public static ColorUIResource WHITE = new ColorUIResource(Color.white);
	public static ColorUIResource BLACK = new ColorUIResource(TEXT_COLOR_COLOR);
	public static ColorUIResource LIGHT_BACKGROUND = new ColorUIResource(LIGHT_BACKGROUND_COLOR);
	public static ColorUIResource MEDIUM_BACKGROUND = new ColorUIResource(MEDIUM_BACKGROUND_COLOR);
	public static ColorUIResource DARK_BACKGROUND = new ColorUIResource(DARK_BACKGROUND_COLOR);
	public static ColorUIResource CONTENT_BACKGROUND = new ColorUIResource(CONTENT_BACKGROUND_COLOR);
	public static ColorUIResource CONTENT_LIGHT_BACKGROUND = new ColorUIResource(CONTENT_LIGHT_BACKGROUND_COLOR);
	public static ColorUIResource BLANK_BACKGROUND = new ColorUIResource(BLANK_BACKGROUND_COLOR);
	public static ColorUIResource BORDER_HIGHLIGHT = new ColorUIResource(BUTTON_BORDER_HIGHLIGHT_COLOR);
	public static ColorUIResource BORDER_INNER_HIGHLIGHT = new ColorUIResource(BORDER_INNER_HIGHLIGHT_COLOR);
	public static ColorUIResource BORDER_SHADOW = new ColorUIResource(BORDER_SHADOW_COLOR);
	public static ColorUIResource BORDER_INNER_SHADOW = new ColorUIResource(BORDER_INNER_SHADOW_COLOR);
	public static ColorUIResource FOCUS_FOREGROUND = new ColorUIResource(FOCUS_FOREGROUND_COLOR);
	public static ColorUIResource LOGIN_LABEL_HIGHLIGHT = new ColorUIResource(LOGIN_LABEL_HIGHLIGHT_COLOR);
	public static ColorUIResource TITLE_BACKGROUND = new ColorUIResource(TITLE_BACKGROUND_COLOR);
	public static ColorUIResource REQUIRED_FIELD_INDICATOR = new ColorUIResource(REQUIRED_FIELD_INDICATOR_COLOR);
	public static ColorUIResource TABLE_SELECTED_DKBLUE = new ColorUIResource(TABLE_SELECTED_DKBLUE_COLOR);
	public static ColorUIResource TEXT_COLOR = new ColorUIResource(TEXT_COLOR_COLOR);
	public static ColorUIResource TABLE_BACKGROUND = new ColorUIResource(TABLE_BACKGROUND_COLOR);

	// other colors found in TRexUIManager
	public static ColorUIResource OUTER_HIGHLIGHT = WHITE;
	public static ColorUIResource INNER_HIGHLIGHT = new ColorUIResource(TABLE_BACKGROUND_COLOR);
	public static ColorUIResource INNER_SHADOW = BORDER_INNER_SHADOW;
	public static ColorUIResource DARK_BLUE_SELECTION = LOGIN_LABEL_HIGHLIGHT;
	public static ColorUIResource UNAVAILABLE_TEXT = BORDER_INNER_SHADOW;

	// TRex specific resources
	public final static InsetsUIResource BUTTON_MARGIN = new InsetsUIResource(4, 8, 4, 8);
	private final static BorderUIResource.BevelBorderUIResource SUNKEN_BORDER = new BorderUIResource.BevelBorderUIResource(
			BevelBorder.LOWERED, BORDER_HIGHLIGHT_COLOR,
			BORDER_INNER_HIGHLIGHT_COLOR,
			BORDER_SHADOW_COLOR,
			BORDER_INNER_SHADOW_COLOR);
	private final static BorderUIResource.EtchedBorderUIResource ETCHED_BORDER = new BorderUIResource.EtchedBorderUIResource(
			EtchedBorder.RAISED,
			BORDER_HIGHLIGHT_COLOR,
			BORDER_SHADOW_COLOR);

	public final static BorderUIResource.EmptyBorderUIResource EMPTY_BORDER = new BorderUIResource.EmptyBorderUIResource(0, 0, 0, 0);


	/**
	 * Basic button border with the color passed in.
	 *
	 * @param color
	 * @return border
	 */
	public static BasicBorders.ButtonBorder buttonBorder(Color color) {
		return new BasicBorders.ButtonBorder(color, color, color, color);
	}

	Object radioButtonBorder = new UIDefaults.ProxyLazyValue(
			"javax.swing.plaf.basic.BasicBorders",
			"getRadioButtonBorder");

	// font constants
	public final static int ELEVEN = 11;
	public final static int TWELVE = 12;

	public final static Font CONTENT_PLAIN_FONT = new FontUIResource(
			new Font("SansSerif", Font.PLAIN, TWELVE));
	public final static Font CONTENT_LINUX_PLAIN_FONT = new FontUIResource(
			new Font("SansSerif", Font.PLAIN, ELEVEN));

	public final static Font CONTENT_BOLD_FONT = new FontUIResource(
			new Font("SansSerif", Font.BOLD, TWELVE));
	public final static Font CONTENT_LINUX_BOLD_FONT = new FontUIResource(
			new Font("SansSerif", Font.BOLD, ELEVEN));

	public GhcLookAndFeel() {
		super();
	}

	public String getID() {
		return "Ghc-One";
	}

	public String getName() {
		return "Ghc-One Look And Feel";
	}

	public String getDescription() {
		return "The Cross Platform Look and Feel of Ghc Enterprise.";
	}

	public boolean isNativeLookAndFeel() {
		return false;
	}

	public boolean isSupportedLookAndFeel() {
		return true;
	}

	public static void setDefaultLookAndFeel() {

		UIManager.put("Panel.background", Color.WHITE);
		UIManager.put("Dialog.background", Color.RED);
		UIManager.put("CheckBox.background", Color.WHITE);
		UIManager.put("ComboBox.background", Color.WHITE);
		UIManager.put("ComboBox.buttonBackground", Color.WHITE);
		// UIManager.put("ComboBox.selectionBackground", Color.GREEN);
		// UIManager.put("ComboBox.border", BLACK_MATTE_BORDER);
		UIManager.put("TextArea.background", Color.WHITE);
		UIManager.put("RadioButton.background", Color.WHITE);
		UIManager.put("Box.background", Color.WHITE);
		UIManager.put("ToggleButton.background", Color.WHITE);
		UIManager.put("Separator.background", Color.WHITE);
		UIManager.put("ScrollPane.background", Color.WHITE);
		UIManager.put("SplitPane.background", Color.WHITE);
		UIManager.put("Slider.background", Color.WHITE);
		UIManager.put("TabbedPane.background", Color.WHITE);
		UIManager.put("Label.background", Color.WHITE);
		UIManager.put("OptionPane.background", Color.WHITE);
		UIManager.put("TextField.background", Color.WHITE);
		UIManager.put("TextPane.background", Color.WHITE);
		UIManager.put("EditorPane.background", Color.WHITE);
		UIManager.put("Table.background", Color.WHITE);
		// UIManager.put("Viewport.background", Color.WHITE);
		

		// System
		UIManager.put("desktop", BLANK_BACKGROUND);
		UIManager.put("window", CONTENT_BACKGROUND);
		UIManager.put("windowBorder", BORDER_SHADOW);
	}

	public UIDefaults getDefaults() {
		UIDefaults table = super.getDefaults();
		initTableDefaults(table);
		return table;
	}

	public void initialize() {
		super.initialize();
	}

	/**
	 * update the hex value/(html format) to RGB Java Swing
	 * 
	 * @param hex
	 * @return
	 */
	public static Color getColorFromHex(String hex) {
		if (hex.length() == 7 || hex.length() == 6) {
			if (hex.startsWith("#")) {
				hex = hex.substring(1);
			}
			int red = Integer.parseInt(hex.substring(0, 2), 16);
			int green = Integer.parseInt(hex.substring(2, 4), 16);
			int blue = Integer.parseInt(hex.substring(4), 16);
			return new Color(red, green, blue);
		}
		return null;
	}

	/**
	 * Components requireing a high dagree of customization should do so in their
	 * UIDelegate. This new delegate is then placed into the UIDefaults table.
	 * 
	 * @param table UIDefaults
	 */
	protected void initClassDefaults(UIDefaults table) {
		super.initClassDefaults(table);

		String packageName = "com.techrx.app.swing.plaf.TRexLookAndFeel.uidelegates.";

		// many of these do nothing but call super(). This is the pattern to
		// follow in the event a components needs modification. Controller and
		// View lives here.
		Object[] trexDefaults = {
				"PanelUI", packageName + "TPanelUI",
				"LabelUI", packageName + "TLabelUI",
				"TextAreaUI", packageName + "TTextAreaUI",
				"ScrollPaneUI", packageName + "TScrollPaneUI",
				"ButtonUI", packageName + "TButtonUI",
				"ComboBoxUI", packageName + "TComboBoxUI",
				"ScrollPaneUI", packageName + "TScrollPaneUI",
				"TextAreaUI", packageName + "TTextAreaUI",
				"TableUI", packageName + "TTableUI",
				"CheckBoxUI", packageName + "TCheckBoxUI",
				"RadioButtonUI", packageName + "TRadioButtonUI",
				"TextFieldUI", packageName + "TTextFieldUI",
				"ToggleButtonUI", packageName + "TToggleButtonUI",
				"TableUI", packageName + "TTableUI",
				"ScrollBarUI", packageName + "TScrollBarUI",
				"FileChooserUI", packageName + "TFileChooserUI"
		};

		table.putDefaults(trexDefaults);

	}

	/**
	 * These are system colors which are the base colors for all components. Individual
	 * components should be addressed, as needed, in the initComponentDefaults method.
	 * 
	 * @param table UIDefaults
	 */
	protected void initSystemColorDefaults(UIDefaults table) {

		Object[] trexDefaults = {
				// basic text
				"text", WHITE,
				"textText", BLACK,
				"textHighlight", TABLE_SELECTED_DKBLUE_COLOR,
				"textHighlightText", WHITE,
				"textInactiveText", LIGHT_BACKGROUND,

				// System properties
				"desktop", BLANK_BACKGROUND,
				"window", CONTENT_BACKGROUND,
				"windowBorder", BORDER_SHADOW,
				"windowText", BLACK,
				"menu", CONTENT_BACKGROUND,
				"menuText", BLACK,
				"control", CONTENT_BACKGROUND,
				"controlText", BLACK,
				"controlHighlight", BORDER_HIGHLIGHT,
				"controlLtHighlight", CONTENT_LIGHT_BACKGROUND,
				"controlShadow", BORDER_SHADOW,
				"controlDkShadow", DARK_BACKGROUND,
				"scrollbar", CONTENT_BACKGROUND,
				"info", CONTENT_BACKGROUND,
				"infoText", CONTENT_LIGHT_BACKGROUND,

				"activeCaption", BLACK,
				"activeCaptionText", BLACK,
				"activeCaptionBorder", BORDER_SHADOW,
				"inactiveCaption", BORDER_INNER_SHADOW,
				"inactiveCaptionText", BORDER_HIGHLIGHT,
				"inactiveCaptionBorder", BORDER_INNER_SHADOW,

				"scrollbar", CONTENT_BACKGROUND,
				"info", UNAVAILABLE_TEXT,
				"infoText", BLACK,

		};

		table.putDefaults(trexDefaults);
		table.put("menu", DARK_BACKGROUND);
		table.put("menuText", WHITE);
	}

	protected void initComponentDefaults(UIDefaults table) {
		// catch the basics
		super.initComponentDefaults(table);

		// *** TextComponents
		Object fieldInputMap = new UIDefaults.LazyInputMap(new Object[] {
				"control C", DefaultEditorKit.copyAction,
				"control V", DefaultEditorKit.pasteAction,
				"control X", DefaultEditorKit.cutAction,
				"COPY", DefaultEditorKit.copyAction,
				"PASTE", DefaultEditorKit.pasteAction,
				"CUT", DefaultEditorKit.cutAction,
				"control INSERT", DefaultEditorKit.copyAction,
				"shift INSERT", DefaultEditorKit.pasteAction,
				"shift DELETE", DefaultEditorKit.cutAction,
				"control A", DefaultEditorKit.selectAllAction,
				"control BACK_SLASH", "unselect"/* DefaultEditorKit.unselectAction */,
				"shift LEFT", DefaultEditorKit.selectionBackwardAction,
				"shift RIGHT", DefaultEditorKit.selectionForwardAction,
				"control LEFT", DefaultEditorKit.previousWordAction,
				"control RIGHT", DefaultEditorKit.nextWordAction,
				"control shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
				"control shift RIGHT", DefaultEditorKit.selectionNextWordAction,
				"HOME", DefaultEditorKit.beginLineAction,
				"END", DefaultEditorKit.endLineAction,
				"shift HOME", DefaultEditorKit.selectionBeginLineAction,
				"shift END", DefaultEditorKit.selectionEndLineAction,
				"typed \010", DefaultEditorKit.deletePrevCharAction,
				"DELETE", DefaultEditorKit.deleteNextCharAction,
				"RIGHT", DefaultEditorKit.forwardAction,
				"LEFT", DefaultEditorKit.backwardAction,
				"KP_RIGHT", DefaultEditorKit.forwardAction,
				"KP_LEFT", DefaultEditorKit.backwardAction,
				"ENTER", JTextField.notifyAction,
				"control shift O", "toggle-componentOrientation"/*
																 * DefaultEditorKit.
																 * toggleComponentOrientation
																 */
		});

		Object multilineInputMap = new UIDefaults.LazyInputMap(new Object[] {
				"control C", DefaultEditorKit.copyAction,
				"control V", DefaultEditorKit.pasteAction,
				"control X", DefaultEditorKit.cutAction,
				"COPY", DefaultEditorKit.copyAction,
				"PASTE", DefaultEditorKit.pasteAction,
				"CUT", DefaultEditorKit.cutAction,
				"control INSERT", DefaultEditorKit.copyAction,
				"shift INSERT", DefaultEditorKit.pasteAction,
				"shift DELETE", DefaultEditorKit.cutAction,
				"shift LEFT", DefaultEditorKit.selectionBackwardAction,
				"shift RIGHT", DefaultEditorKit.selectionForwardAction,
				"control LEFT", DefaultEditorKit.previousWordAction,
				"control RIGHT", DefaultEditorKit.nextWordAction,
				"control shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
				"control shift RIGHT", DefaultEditorKit.selectionNextWordAction,
				"control A", DefaultEditorKit.selectAllAction,
				"control BACK_SLASH", "unselect"/* DefaultEditorKit.unselectAction */,
				"HOME", DefaultEditorKit.beginLineAction,
				"END", DefaultEditorKit.endLineAction,
				"shift HOME", DefaultEditorKit.selectionBeginLineAction,
				"shift END", DefaultEditorKit.selectionEndLineAction,
				"control HOME", DefaultEditorKit.beginAction,
				"control END", DefaultEditorKit.endAction,
				"control shift HOME", DefaultEditorKit.selectionBeginAction,
				"control shift END", DefaultEditorKit.selectionEndAction,
				"UP", DefaultEditorKit.upAction,
				"DOWN", DefaultEditorKit.downAction,
				"typed \010", DefaultEditorKit.deletePrevCharAction,
				"DELETE", DefaultEditorKit.deleteNextCharAction,
				"RIGHT", DefaultEditorKit.forwardAction,
				"LEFT", DefaultEditorKit.backwardAction,
				"KP_RIGHT", DefaultEditorKit.forwardAction,
				"KP_LEFT", DefaultEditorKit.backwardAction,
				"PAGE_UP", DefaultEditorKit.pageUpAction,
				"PAGE_DOWN", DefaultEditorKit.pageDownAction,
				"shift PAGE_UP", "selection-page-up",
				"shift PAGE_DOWN", "selection-page-down",
				"ctrl shift PAGE_UP", "selection-page-left",
				"ctrl shift PAGE_DOWN", "selection-page-right",
				"shift UP", DefaultEditorKit.selectionUpAction,
				"shift DOWN", DefaultEditorKit.selectionDownAction,
				"ENTER", DefaultEditorKit.insertBreakAction,
				// "TAB", DefaultEditorKit.insertTabAction,
				// "TAB", "focusOutForward",
				// "shift TAB", "focusOutBackward",
				"control T", "next-link-action",
				"control shift T", "previous-link-action",
				"control SPACE", "activate-link-action",
				"control shift O", "toggle-componentOrientation"/*
																 * DefaultEditorKit.
																 * toggleComponentOrientation
																 */
		});

		boolean windows = false;
		String osName = System.getProperty("os.name");
		if ((osName != null) && (osName.indexOf("Windows") > -1)) {
			windows = true;
		}

		// then overwrite our preferences
		Object[] trexDefaults = {

				"Component.background", CONTENT_BACKGROUND,
				"Box.background", CONTENT_BACKGROUND,

				// TextField properties
				"TextField.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"TextField.background", WHITE,
				"TextField.inactiveForeground", BLACK, // BLANK_BACKGROUND,
				"TextField.shadow", BORDER_INNER_SHADOW,
				"TextField.darkShadow", BORDER_SHADOW,
				"TextField.light", OUTER_HIGHLIGHT,
				"TextField.highlight", BORDER_HIGHLIGHT,
				"TextField.border", SUNKEN_BORDER,

				// PasswordField properties
				"PasswordField.background", WHITE,

				// TextArea properties
				"TextArea.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"TextArea.background", WHITE,
				"TextArea.inactiveForeground", BLACK,

				// Tree properties
				"Tree.hash", BORDER_SHADOW,
				"Tree.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"Tree.foreground", TEXT_COLOR,

				"ProgressBar.border", SUNKEN_BORDER,
				"ProgressBar.background", DARK_BACKGROUND,

				// ComboBox properties
				"ComboBox.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"ComboBox.foreground", BLACK,
				"ComboBox.background", WHITE,
				"ComboBox.border", SUNKEN_BORDER,
				"ComboBox.focus", LOGIN_LABEL_HIGHLIGHT,
				"ComboBox.controlForeground", BLACK,
				"ComboBox.buttonBackground", CONTENT_BACKGROUND,
				"ComboBox.buttonShadow", BORDER_SHADOW,
				"ComboBox.buttonDarkShadow", BLACK,
				"ComboBox.buttonHighlight", BORDER_HIGHLIGHT,
				"ComboBox.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] {
						"ESCAPE", "hidePopup",
						"PAGE_UP", "pageUpPassThrough",
						"PAGE_DOWN", "pageDownPassThrough",
						"HOME", "homePassThrough",
						"END", "endPassThrough",
						"DOWN", "selectNext",
						"KP_DOWN", "selectNext",
						"UP", "selectPrevious",
						"KP_UP", "selectPrevious",
						"ENTER", "enterPressed",
						"F4", "togglePopup"
				}),

				"Desktop.ancestorInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"ctrl F5", "restore",
						"ctrl F4", "close",
						"ctrl F7", "move",
						"ctrl F8", "resize",
						"RIGHT", "right",
						"KP_RIGHT", "right",
						"LEFT", "left",
						"KP_LEFT", "left",
						"UP", "up",
						"KP_UP", "up",
						"DOWN", "down",
						"KP_DOWN", "down",
						"ESCAPE", "escape",
						"ctrl F9", "minimize",
						"ctrl F10", "maximize",
						"ctrl F6", "selectNextFrame",
						"ctrl TAB", "selectNextFrame",
						"ctrl alt F6", "selectNextFrame",
						"shift ctrl alt F6", "selectPreviousFrame",
						"ctrl F12", "navigateNext",
						"shift ctrl F12", "navigatePrevious"
				}),

				"List.focusInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"ctrl C", "copy",
						"ctrl V", "paste",
						"ctrl X", "cut",
						"COPY", "copy",
						"PASTE", "paste",
						"CUT", "cut",
						"UP", "selectPreviousRow",
						"KP_UP", "selectPreviousRow",
						"shift UP", "selectPreviousRowExtendSelection",
						"shift KP_UP", "selectPreviousRowExtendSelection",
						"DOWN", "selectNextRow",
						"KP_DOWN", "selectNextRow",
						"shift DOWN", "selectNextRowExtendSelection",
						"shift KP_DOWN", "selectNextRowExtendSelection",
						"LEFT", "selectPreviousColumn",
						"KP_LEFT", "selectPreviousColumn",
						"shift LEFT", "selectPreviousColumnExtendSelection",
						"shift KP_LEFT", "selectPreviousColumnExtendSelection",
						"RIGHT", "selectNextColumn",
						"KP_RIGHT", "selectNextColumn",
						"shift RIGHT", "selectNextColumnExtendSelection",
						"shift KP_RIGHT", "selectNextColumnExtendSelection",
						"ctrl SPACE", "selectNextRowExtendSelection",
						"HOME", "selectFirstRow",
						"shift HOME", "selectFirstRowExtendSelection",
						"END", "selectLastRow",
						"shift END", "selectLastRowExtendSelection",
						"PAGE_UP", "scrollUp",
						"shift PAGE_UP", "scrollUpExtendSelection",
						"PAGE_DOWN", "scrollDown",
						"shift PAGE_DOWN", "scrollDownExtendSelection",
						"ctrl A", "selectAll",
						"ctrl SLASH", "selectAll",
						"ctrl BACK_SLASH", "clearSelection"
				}),

				// CheckBox properties
				"CheckBox.background", CONTENT_BACKGROUND,
				"CheckBox.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"CheckBox.border", ETCHED_BORDER,
				"CheckBox.highlight", OUTER_HIGHLIGHT,
				"CheckBox.shadow", BORDER_INNER_SHADOW,
				"CheckBox.darkShadow", BORDER_SHADOW,
				"CheckBox.focus", LOGIN_LABEL_HIGHLIGHT,
				"CheckBox.interiorBackground", WHITE,

				// RadioButton properties
				"RadioButton.background", CONTENT_BACKGROUND,
				"RadioButton.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"RadioButton.border", ETCHED_BORDER,
				"RadioButton.focus", LOGIN_LABEL_HIGHLIGHT,
				"RadioButton.interiorBackground", WHITE,
				"RadioButton.darkShadow", BORDER_SHADOW,
				"RadioButton.selectionForeground", Color.blue,
				"RadioButton.selectionBackground", Color.red,
				"RadioButton.focusInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"SPACE", "pressed",
						"released SPACE", "released"
				}),

				"FormattedTextField.focusInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"ctrl C", DefaultEditorKit.copyAction,
						"ctrl V", DefaultEditorKit.pasteAction,
						"ctrl X", DefaultEditorKit.cutAction,
						"COPY", DefaultEditorKit.copyAction,
						"PASTE", DefaultEditorKit.pasteAction,
						"CUT", DefaultEditorKit.cutAction,
						"shift LEFT", DefaultEditorKit.selectionBackwardAction,
						"shift KP_LEFT", DefaultEditorKit.selectionBackwardAction,
						"shift RIGHT", DefaultEditorKit.selectionForwardAction,
						"shift KP_RIGHT", DefaultEditorKit.selectionForwardAction,
						"ctrl LEFT", DefaultEditorKit.previousWordAction,
						"ctrl KP_LEFT", DefaultEditorKit.previousWordAction,
						"ctrl RIGHT", DefaultEditorKit.nextWordAction,
						"ctrl KP_RIGHT", DefaultEditorKit.nextWordAction,
						"ctrl shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
						"ctrl shift KP_LEFT", DefaultEditorKit.selectionPreviousWordAction,
						"ctrl shift RIGHT", DefaultEditorKit.selectionNextWordAction,
						"ctrl shift KP_RIGHT", DefaultEditorKit.selectionNextWordAction,
						"ctrl A", DefaultEditorKit.selectAllAction,
						"HOME", DefaultEditorKit.beginLineAction,
						"END", DefaultEditorKit.endLineAction,
						"shift HOME", DefaultEditorKit.selectionBeginLineAction,
						"shift END", DefaultEditorKit.selectionEndLineAction,
						"typed \010", DefaultEditorKit.deletePrevCharAction,
						"DELETE", DefaultEditorKit.deleteNextCharAction,
						"RIGHT", DefaultEditorKit.forwardAction,
						"LEFT", DefaultEditorKit.backwardAction,
						"KP_RIGHT", DefaultEditorKit.forwardAction,
						"KP_LEFT", DefaultEditorKit.backwardAction,
						"ENTER", JTextField.notifyAction,
						"ctrl BACK_SLASH", "unselect",
						"control shift O", "toggle-componentOrientation",
						"ESCAPE", "reset-field-edit",
						"UP", "increment",
						"KP_UP", "increment",
						"DOWN", "decrement",
						"KP_DOWN", "decrement",
				}),

				"RootPane.defaultButtonWindowKeyBindings", new Object[] {
						"ENTER", "press",
						"released ENTER", "release",
						"ctrl ENTER", "press",
						"ctrl released ENTER", "release"
				},

				// Table properties
				"Viewport.background", BLANK_BACKGROUND,
				"Viewport.shadow", BORDER_SHADOW,
				"Viewport.highlight", BORDER_HIGHLIGHT,
				"TableHeader.background", CONTENT_BACKGROUND,
				"TableHeader.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"Table.font", CONTENT_PLAIN_FONT,
				"Table.background", TABLE_BACKGROUND,
				"Table.inactiveForeground", BORDER_INNER_SHADOW,

				// Button properties
				"Button.background", CONTENT_BACKGROUND,
				"Button.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"Button.margin", BUTTON_MARGIN,
				"Button.shadow", BORDER_INNER_SHADOW,
				"Button.darkShadow", BORDER_SHADOW,
				"Button.light", CONTENT_BACKGROUND,
				"Button.highlight", OUTER_HIGHLIGHT,
				"Button.focus", LOGIN_LABEL_HIGHLIGHT,
				"Button.dashedRectGapX", new Integer(5),
				"Button.dashedRectGapY", new Integer(4),
				"Button.dashedRectGapWidth", new Integer(10),
				"Button.dashedRectGapHeight", new Integer(8),

				// ToggleButton properties
				"ToggleButton.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"ToggleButton.background", CONTENT_BACKGROUND,
				"ToggleButton.foreground", BLACK,
				"ToggleButton.shadow", BORDER_INNER_SHADOW,
				"ToggleButton.darkShadow", BORDER_SHADOW,
				"ToggleButton.light", CONTENT_LIGHT_BACKGROUND,
				"ToggleButton.highlight", OUTER_HIGHLIGHT,
				"ToggleButton.focus", BORDER_SHADOW,
				"ToggleButton.select", BORDER_SHADOW,
				"ToggleButton.textShiftOffset", new Integer(1),
				"ToggleButton.border", radioButtonBorder,
				"ToggleButton.focusInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"SPACE", "pressed",
						"released SPACE", "released"
				}),

				// Separator
				"Separator.background", OUTER_HIGHLIGHT,
				"Separator.foreground", BORDER_INNER_SHADOW_COLOR,

				// ScrollPane properties
				"ScrollPane.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"ScrollPane.background", BLANK_BACKGROUND,
				"ScrollPane.foreground", TEXT_COLOR,
				"ScrollPane.ancestorInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"RIGHT", "unitScrollRight",
						"KP_RIGHT", "unitScrollRight",
						"DOWN", "unitScrollDown",
						"KP_DOWN", "unitScrollDown",
						"LEFT", "unitScrollLeft",
						"KP_LEFT", "unitScrollLeft",
						"UP", "unitScrollUp",
						"KP_UP", "unitScrollUp",
						"PAGE_UP", "scrollUp",
						"PAGE_DOWN", "scrollDown",
						"ctrl PAGE_UP", "scrollLeft",
						"ctrl PAGE_DOWN", "scrollRight",
						"ctrl HOME", "scrollHome",
						"ctrl END", "scrollEnd"
				}),

				"SplitPane.background", CONTENT_BACKGROUND,
				"SplitPane.ancestorInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"UP", "negativeIncrement",
						"DOWN", "positiveIncrement",
						"LEFT", "negativeIncrement",
						"RIGHT", "positiveIncrement",
						"KP_UP", "negativeIncrement",
						"KP_DOWN", "positiveIncrement",
						"KP_LEFT", "negativeIncrement",
						"KP_RIGHT", "positiveIncrement",
						"HOME", "selectMin",
						"END", "selectMax",
						"F8", "startResize",
						"F6", "toggleFocus",
						"ctrl TAB", "focusOutForward",
						"ctrl shift TAB", "focusOutBackward"
				}),

				// Slider
				"Slider.foreground", table.get("control"),
				"Slider.background", table.get("control"),
				"Slider.highlight", table.get("controlLtHighlight"),
				"Slider.shadow", table.get("controlShadow"),
				"Slider.focus", table.get("controlDkShadow"),
				"Slider.border", null,
				"Slider.focusInsets", new InsetsUIResource(2, 2, 2, 2),
				"Slider.focusInputMap",
				new UIDefaults.LazyInputMap(new Object[] {
						"RIGHT", "positiveUnitIncrement",
						"KP_RIGHT", "positiveUnitIncrement",
						"DOWN", "negativeUnitIncrement",
						"KP_DOWN", "negativeUnitIncrement",
						"PAGE_DOWN", "negativeBlockIncrement",
						"LEFT", "negativeUnitIncrement",
						"KP_LEFT", "negativeUnitIncrement",
						"UP", "positiveUnitIncrement",
						"KP_UP", "positiveUnitIncrement",
						"PAGE_UP", "positiveBlockIncrement",
						"HOME", "minScroll",
						"END", "maxScroll"
				}),
				"Slider.focusInputMap.RightToLeft",
				new UIDefaults.LazyInputMap(new Object[] {
						"RIGHT", "negativeUnitIncrement",
						"KP_RIGHT", "negativeUnitIncrement",
						"LEFT", "positiveUnitIncrement",
						"KP_LEFT", "positiveUnitIncrement",
				}),

				// List properties
				"List.background", CONTENT_LIGHT_BACKGROUND,

				// Tree properties
				"Tree.background", CONTENT_LIGHT_BACKGROUND,

				"Label.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"TabbedPane.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"Menu.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"MenuItem.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,

				// Panel
				"Panel.font", windows ? CONTENT_PLAIN_FONT : CONTENT_LINUX_PLAIN_FONT,
				"Panel.background", CONTENT_BACKGROUND,
				"Panel.foreground", TEXT_COLOR,

				// OptionPane.
				"OptionPane.background", CONTENT_BACKGROUND,
				"OptionPane.foreground", TEXT_COLOR,
				"OptionPane.messageForeground", TEXT_COLOR,
				"OptionPane.windowBindings", new Object[] {
						"ESCAPE", "close",
						"RIGHT", "toggleFocus",
						"LEFT", "toggleFocus" },

				// Text Key Mappings
				"TextField.focusInputMap", fieldInputMap,
				"PasswordField.focusInputMap", fieldInputMap,
				"TextArea.focusInputMap", multilineInputMap,
				"TextPane.focusInputMap", multilineInputMap,
				"EditorPane.focusInputMap", multilineInputMap,
		};
		table.putDefaults(trexDefaults);
		// table.put("TextField.border", BLACK_MATTE_BORDER);
		// table.put("PasswordField.border", BLACK_MATTE_BORDER);
		// table.put("TextArea.border", EMPTY_BORDER);
		// table.put("TextArea.inactiveBackground", LIGHT_GRAY);
		// table.put("ComboBox.border", BLACK_MATTE_BORDER);
		// table.put("ComboBox.selectionBackground", ONEHUNDRED_BLUE_COLOR);
		// table.put("ComboBox.selectionForeground", BLACK);
		// table.put("CheckBox.background", null);
		// table.put("CheckBox.border", BLACK_MATTE_BORDER);
		// table.put("RadioButton.disabledText", SIXHUNDRED_BLACK_COLOR);
		// table.put("Viewport.border", BLACK_MATTE_BORDER);
		// table.put("TableHeader.cellBorder", GRAY_LINE_BORDER);
		// table.put("TableHeader.background", EIGHTHUNDRED_BLUE_COLOR);
		// table.put("TableHeader.font", windows ? CONTENT_BOLD_FONT :
		// CONTENT_LINUX_BOLD_FONT);
		// table.put("TableHeader.foreground", WHITE_BACKGOUND_COLOR);
		// table.put("Table.background", WHITE);
		// table.put("Table.selectionBackground", ONEHUNDRED_BLUE_COLOR);
		// table.put("Table.selectionForeground", BLACK);
		// table.put("Button.background", EIGHTHUNDRED_BLUE_COLOR);
		// table.put("Button.border", buttonBorder(EIGHTHUNDRED_BLUE_COLOR));
		// table.put("ScrollPane.border", BLACK_MATTE_BORDER);
		// table.put("TabbedPane.shadow", BLACK);
		// table.put("TabbedPane.light", LIGHT_GRAY);
		// table.put("TabbedPane.highlight", BLACK);
		// table.put("TabbedPane.darkShadow", LIGHT_GRAY);
		// table.put("MenuItem.background", WHITE);
		// table.put("MenuItem.foreground", BLACK);
		// table.put("Panel.background", LIGHT_GRAY);
		// table.put("ToolBar.background", DARK_BACKGROUND);
		// table.put("ScrollBar.border", BLACK_MATTE_BORDER);
		// table.put("ToolTip.background", WHITE);
		// table.put("Separator.foreground", SIXHUNDRED_BLACK);
		// table.put("Button.foreground", WHITE);
		// table.put("ToggleButton.font", windows ? CONTENT_BOLD_FONT :
		// CONTENT_LINUX_BOLD_FONT);
		// table.put("ToggleButton.background", EIGHTHUNDRED_BLUE_COLOR);
		// table.put("ToggleButton.foreground", WHITE);
		// table.put("ToggleButton.border", buttonBorder(EIGHTHUNDRED_BLUE_COLOR));
		// table.put("ToggleButton.select", NINEHUNDRED_BLUE_COLOR);
		// table.put("TextField.selectionBackground", ONEHUNDRED_BLUE_COLOR);
		// table.put("TextField.selectionForeground", BLACK);
		// table.put("TextField.foreground", BLACK);
		// table.put("CheckBox.darkShadow", FOURHUNDRED_BLACK_COLOR);
	}

	////////////////////////////////////////////////////////////////////////////
	// TABLE
	/**
	 * This is the place to specify table specific behaviors.
	 * 
	 * @param table UIDefaults
	 */
	private void initTableDefaults(UIDefaults table) {

		Object[] tableDefaults = {
				"Table.ancestorInputMap",

				new UIDefaults.LazyInputMap(new Object[] {
						"RIGHT", "selectNextColumn",
						"KP_RIGHT", "selectNextColumn",
						"LEFT", "selectPreviousColumn",
						"KP_LEFT", "selectPreviousColumn",
						"DOWN", "selectNextRow",
						"KP_DOWN", "selectNextRow",
						"UP", "selectPreviousRow",
						"KP_UP", "selectPreviousRow",
						"shift RIGHT", "selectNextColumnExtendSelection",
						"shift KP_RIGHT", "selectNextColumnExtendSelection",
						"shift LEFT", "selectPreviousColumnExtendSelection",
						"shift KP_LEFT", "selectPreviousColumnExtendSelection",
						"shift DOWN", "selectNextRowExtendSelection",
						"shift KP_DOWN", "selectNextRowExtendSelection",
						"shift UP", "selectPreviousRowExtendSelection",
						"shift KP_UP", "selectPreviousRowExtendSelection",
						"PAGE_UP", "scrollUpChangeSelection",
						"PAGE_DOWN", "scrollDownChangeSelection",
						"HOME", "selectFirstColumn",
						"END", "selectLastColumn",
						"shift PAGE_UP", "scrollUpExtendSelection",
						"shift PAGE_DOWN", "scrollDownExtendSelection",
						"shift HOME", "selectFirstColumnExtendSelection",
						"shift END", "selectLastColumnExtendSelection",
						"ctrl PAGE_UP", "scrollLeftChangeSelection",
						"ctrl PAGE_DOWN", "scrollRightChangeSelection",
						"ctrl HOME", "selectFirstRow",
						"ctrl END", "selectLastRow",
						"ctrl shift PAGE_UP", "scrollRightExtendSelection",
						"ctrl shift PAGE_DOWN", "scrollLeftExtendSelection",
						"ctrl shift HOME", "selectFirstRowExtendSelection",
						"ctrl shift END", "selectLastRowExtendSelection",
						"TAB", "selectNextColumnCell",
						"shift TAB", "selectPreviousColumnCell",
						// "ENTER", "selectNextRowCell",
						"ENTER", "stopEditing",
						// "shift ENTER", "selectPreviousRowCell",
						"ctrl A", "selectAll",
						// "ESCAPE", "stopEditing",
						// "ESCAPE", "cancel",
						// "SPACE", "startEditing",
						"F2", "startEditing"
				})
		};

		table.putDefaults(tableDefaults);
	}
}

