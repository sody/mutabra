package com.mutabra.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Duel extends BattleImpl {
	private static final String FIRST_COMMAND = "first";
	private static final String SECOND_COMMAND = "second";

	public Duel() {
		super(FIRST_COMMAND, SECOND_COMMAND);
	}

	public BattleCommand getFirstCommand() {
		return getCommand(FIRST_COMMAND);
	}

	public BattleCommand getSecondCommand() {
		return getCommand(SECOND_COMMAND);
	}
}
