package com.noname.game;

import org.greatage.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Battle {
	private final Map<String, Locatable> field = CollectionUtils.newConcurrentMap();
	private final Map<String, Player> players = CollectionUtils.newConcurrentMap();

	public Battle(final User... users) {
		for (User user : users) {
			this.players.put(user.getName(), new Player(this, user.getHero()));
		}
	}

	public boolean isReady() {
		for (Player player : players.values()) {
			if (player.getLocation() == null) {
				return false;
			}
		}
		return true;
	}

	public void setLocatable(final String location, final Locatable locatable) {
		field.put(location, locatable);
	}

	public Locatable getLocatable(final String location) {
		return field.get(location);
	}

	public Collection<Player> getAllPlayers() {
		return players.values();
	}

	public Player getPlayer(final String name) {
		return players.get(name);
	}

	public void begin() {
	}

	public void beginRound() {
		for (Player player : players.values()) {
			player.nextCard();
		}
	}

	public void endRound() {

	}
}
