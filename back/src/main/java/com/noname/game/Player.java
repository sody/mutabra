package com.noname.game;

import com.noname.domain.player.Hero;
import com.noname.domain.player.HeroCard;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Player implements Locatable {
	private static final int SLOT_SIZE = 4;

	private final List<HeroCard> pack = CollectionUtils.newList();
	private final List<HeroCard> slot = CollectionUtils.newList();

	private final Hero hero;
	private final String location;

	private int health;

	private PlayerAction action;

	public Player(final Hero hero, final String location) {
		this.hero = hero;
		this.location = location;
		this.health = hero.getDefence();
		pack.addAll(hero.getCards());
		for (int i = 0; i < SLOT_SIZE - 1; i++) {
			nextCard();
		}
	}

	public Hero getHero() {
		return hero;
	}

	public String getLocation() {
		return location;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	public List<HeroCard> getSlot() {
		return slot;
	}

	public void cast(final HeroCard card, final String location) {
		action = new PlayerAction(card, location);
	}

	public void commit() {
		if (action != null) {
			slot.remove(action.getCard());
			action.execute();
		}
		action = null;
	}

	public void nextCard() {
		if (!CollectionUtils.isEmpty(pack)) {
			final HeroCard card = pack.remove(new Random().nextInt(pack.size()));
			if (slot.size() >= SLOT_SIZE) {
				slot.remove(0);
			}
			slot.add(card);
		}
	}
}
