package com.mutabra.domain.battle;

import com.mutabra.domain.common.TargetType;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleField {
	private final Position position;
	private final BattleSummon summon;
	private final BattleMember member;
	private final boolean enemySide;

	public BattleField(final Position position, final boolean enemySide) {
		this(position, null, null, enemySide);
	}

	public BattleField(final BattleMember member, final boolean enemySide) {
		this(member.getPosition(), null, member, enemySide);
	}

	public BattleField(final BattleSummon summon, final boolean enemySide) {
		this(summon.getPosition(), summon, null, enemySide);
	}

	private BattleField(final Position position, final BattleSummon summon, final BattleMember member, final boolean enemySide) {
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

	public boolean isSelected() {
		return !enemySide && member != null;
	}

	@SuppressWarnings({"RedundantIfStatement"})
	public boolean supports(final TargetType targetType) {
		if (enemySide && !targetType.supportsEnemySide()) {
			return false;
		}
		if (!enemySide && !targetType.supportsFriendSide()) {
			return false;
		}
		if (member != null && !targetType.supportsHeroPoint()) {
			return false;
		}
		if (summon != null && !targetType.supportsSummonPoint()) {
			return false;
		}
		if (member != null && summon != null && !targetType.supportsEmptyPoint()) {
			return false;
		}
		return true;
	}
}
