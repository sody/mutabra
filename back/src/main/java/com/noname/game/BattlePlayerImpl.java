package com.noname.game;

import com.noname.domain.player.Hero;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class BattlePlayerImpl extends LocatableImpl implements BattlePlayer {
	private final BattleCommand command;
	private final Hero hero;

	private int health;
	private Location location;

	public BattlePlayerImpl(final BattleCommand command, final Hero hero) {
		super(command.getField());
		this.command = command;
		this.hero = hero;

		health = hero.getDefence();
	}

	@Override
	public BattleCommand getCommand() {
		return command;
	}

	@Override
	public String getName() {
		return hero.getName();
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
