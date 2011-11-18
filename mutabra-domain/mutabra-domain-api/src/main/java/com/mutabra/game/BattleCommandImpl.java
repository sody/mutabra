package com.mutabra.game;

import com.mutabra.domain.game.Hero;
import org.greatage.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleCommandImpl implements BattleCommand {
	private final Battle battle;
	private final String name;

	private final BattleField field;
	private final Map<String, BattlePlayer> players;

	public BattleCommandImpl(final Battle battle, final String name) {
		this.battle = battle;
		this.name = name;

		field = new BattleFieldImpl(this);
		players = CollectionUtils.newConcurrentMap();
	}

	public Battle getBattle() {
		return battle;
	}

	public String getName() {
		return name;
	}

	public BattleField getField() {
		return field;
	}

	public Collection<BattlePlayer> getPlayers() {
		return players.values();
	}

	public BattlePlayer getPlayer(final String name) {
		return players.get(name);
	}

	public void addPlayer(final String name, final Hero hero) {
		players.put(name, new BattlePlayerImpl(this, hero));
	}
}
