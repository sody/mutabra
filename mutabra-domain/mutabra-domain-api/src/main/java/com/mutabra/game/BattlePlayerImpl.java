package com.mutabra.game;

import com.mutabra.domain.player.Hero;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattlePlayerImpl extends LocatableImpl implements BattlePlayer {
	private final BattleCommand command;
	private final Hero hero;

	private int health;

	public BattlePlayerImpl(final BattleCommand command, final Hero hero) {
		super(command.getField(), hero.getName());
		this.command = command;
		this.hero = hero;

		health = hero.getDefence();
	}

	public BattleCommand getCommand() {
		return command;
	}

	public Hero getHero() {
		return hero;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}
}
