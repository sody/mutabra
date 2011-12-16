package com.mutabra.domain.battle;

import com.mutabra.domain.common.TargetType;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleField {
	public static final Position[] DUEL_POSITIONS = {
			new Position(0, 0),
			new Position(0, 1),
			new Position(1, 0),
			new Position(1, 2),
			new Position(2, 0),
			new Position(2, 1),
	};

	private final Position position;
	private final BattleUnit unit;
	private final boolean enemySide;

	public BattleField(final Position position, final boolean enemySide) {
		this.unit = null;
		this.position = position;
		this.enemySide = enemySide;
	}

	public BattleField(final BattleUnit unit, final boolean enemySide) {
		this.unit = unit;
		this.position = unit.getPosition();
		this.enemySide = enemySide;
	}

	public Position getPosition() {
		return position;
	}

	public BattleUnit getUnit() {
		return unit;
	}

	public BattleCreature getCreature() {
		return (BattleCreature) unit;
	}

	public BattleHero getHero() {
		return (BattleHero) unit;
	}

	public boolean hasHero() {
		return unit != null && unit instanceof BattleHero;
	}

	public boolean hasCreature() {
		return unit != null && unit instanceof BattleCreature;
	}

	public boolean hasUnit() {
		return unit != null;
	}

	public boolean isEnemySide() {
		return enemySide;
	}

	@SuppressWarnings({"RedundantIfStatement"})
	public boolean supports(final TargetType targetType) {
		if (isEnemySide() && !targetType.supportsEnemy()) {
			return false;
		}
		if (!isEnemySide() && !targetType.supportsFriend()) {
			return false;
		}
		if (hasHero() && !targetType.supportsHero()) {
			return false;
		}
		if (hasCreature() && !targetType.supportsCreature()) {
			return false;
		}
		if (!hasUnit() && !targetType.supportsEmpty()) {
			return false;
		}
		return true;
	}
}
