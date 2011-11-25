package com.mutabra.domain.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattlePlace {
	private final Position position;
	private final BattleSummon summon;
	private final BattleMember member;
	private final boolean enemySide;

	public BattlePlace(final Position position, final boolean enemySide) {
		this(position, null, null, enemySide);
	}

	public BattlePlace(final BattleMember member, final boolean enemySide) {
		this(member.getPosition(), null, member, enemySide);
	}

	public BattlePlace(final BattleSummon summon, final boolean enemySide) {
		this(summon.getPosition(), summon, null, enemySide);
	}

	private BattlePlace(final Position position, final BattleSummon summon, final BattleMember member, final boolean enemySide) {
		this.position = position;
		this.summon = summon;
		this.member = member;
		this.enemySide = enemySide;
	}

	public Position getPosition() {
		return position;
	}

	public BattleSummon getSummon() {
		return summon;
	}

	public BattleMember getMember() {
		return member;
	}

	public boolean hasHero() {
		return member != null;
	}

	public boolean hasSummon() {
		return summon != null;
	}

	public boolean isEnemySide() {
		return enemySide;
	}
}
