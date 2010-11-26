package com.noname.game;

import com.noname.domain.player.Hero;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Battle {
	public Map<String, Locatable> field = CollectionUtils.newMap();

	private Player first;
	private Player second;

	public void setFirstPlayer(final Hero hero, final String location) {
		first = new Player(hero, location);
	}

	public void setSecondPlayer(final Hero hero, final String location) {
		second = new Player(hero, location);
	}

	public void begin() {
		field.put(first.getLocation(), first);
		field.put(second.getLocation(), second);
	}

	public void beginRound() {
		first.nextCard();
		second.nextCard();
	}

	public void endRound() {

	}
}
