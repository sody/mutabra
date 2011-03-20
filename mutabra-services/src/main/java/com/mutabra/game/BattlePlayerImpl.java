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

	@Override
	public BattleCommand getCommand() {
		return command;
	}

	@Override
	public Hero getHero() {
		return hero;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(final int health) {
		this.health = health;
	}
}
