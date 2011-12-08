package com.mutabra.domain.battle;

import com.mutabra.domain.common.TargetType;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleField {
	private final Position position;
	private final BattleCreature creature;
	private final BattleHero hero;
	private final boolean enemySide;

	public BattleField(final Position position, final boolean enemySide) {
		this(position, null, null, enemySide);
	}

	public BattleField(final BattleHero hero, final boolean enemySide) {
		this(hero.getPosition(), null, hero, enemySide);
	}

	public BattleField(final BattleCreature creature, final boolean enemySide) {
		this(creature.getPosition(), creature, null, enemySide);
	}

	private BattleField(final Position position, final BattleCreature creature, final BattleHero hero, final boolean enemySide) {
		this.position = position;
		this.creature = creature;
		this.hero = hero;
		this.enemySide = enemySide;
	}

	public Position getPosition() {
		return position;
	}

	public BattleCreature getCreature() {
		return creature;
	}

	public BattleHero getHero() {
		return hero;
	}

	public boolean hasHero() {
		return hero != null;
	}

	public boolean hasSummon() {
		return creature != null;
	}

	public boolean isEnemySide() {
		return enemySide;
	}

	public boolean isSelected() {
		return !enemySide && hero != null;
	}

	@SuppressWarnings({"RedundantIfStatement"})
	public boolean supports(final TargetType targetType) {
		if (enemySide && !targetType.supportsEnemy()) {
			return false;
		}
		if (!enemySide && !targetType.supportsFriend()) {
			return false;
		}
		if (hero != null && !targetType.supportsHero()) {
			return false;
		}
		if (creature != null && !targetType.supportsSummon()) {
			return false;
		}
		if (hero != null && creature != null && !targetType.supportsEmpty()) {
			return false;
		}
		return true;
	}
}
