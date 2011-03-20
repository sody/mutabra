package com.mutabra.game;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
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

	private final Battle battle;
	private final Hero hero;

	private String location;
	private int health;

	private PlayerAction action;

	public Player(final Battle battle, final Hero hero) {
		this.battle = battle;
		this.hero = hero;
		this.health = hero.getDefence();
		pack.addAll(hero.getCards());
		for (int i = 0; i < SLOT_SIZE - 1; i++) {
			nextCard();
		}
	}

	public Battle getBattle() {
		return battle;
	}

	public Hero getHero() {
		return hero;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		battle.setLocatable(location, this);
		this.location = location;
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
