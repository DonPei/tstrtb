package edu.uth.app.common;

import java.awt.CardLayout;
import java.util.EnumMap;

import javax.swing.JPanel;

public class RtbCardPanel extends JPanel {
	private static final long serialVersionUID = 3258135768999737650L;

	/**
	 * Keys of permitted cards.
	 */
	public enum Key {
		INTRODUCTIONPANEL, TABLEPANEL, METAPANEL
	}

	private CardLayout cardLayout = null;

	private EnumMap<Key, JPanel> map;
	private Key currentKey = Key.INTRODUCTIONPANEL;

	public RtbCardPanel() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		map = new EnumMap<>(Key.class);
	}

	public void addCard(JPanel card, Key key) {
		add(card, key.name());
		map.put(key, card);
	}

	public void showCard(Key key) {
		if (key == currentKey) {
			return;
		}
		currentKey = key;
		cardLayout.show(this, key.name());
	}

	public void removeCard(Key key) {
		JPanel card = map.get(key);
		if (card == null) {
			return;
		}
		cardLayout.removeLayoutComponent(card);
	}

	public JPanel getCard(Key key) {
		return map.get(key);
	}

	public boolean isShowing(Key key) {
		return key == currentKey;
	}

}
