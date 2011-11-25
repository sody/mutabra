package com.mutabra.domain.common;

import org.greatage.hibernate.type.OrderedEnum;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum TargetType implements OrderedEnum {

	/**
	 * Has no target.
	 */
	NOBODY(false, false, false, false, false, false),

	/**
	 * Any single point on battle field.
	 */
	SINGLE(false, true, true, true, true, true),

	/**
	 * Any empty single point on the battle field.
	 */
	SINGLE_EMPTY(false, true, true, true, false, false),

	/**
	 * Any busy(with hero or summoned creature) single point on the battle field.
	 */
	SINGLE_BUSY(false, true, true, false, true, true),

	/**
	 * Any single point with hero on the battle field.
	 */
	SINGLE_HERO(false, true, true, false, true, false),

	/**
	 * Any single point with summoned creature on the battle field.
	 */
	SINGLE_SUMMON(false, true, true, false, false, true),

	/**
	 * Any single point on the enemy-side of the battle field.
	 */
	SINGLE_ENEMY(false, true, false, true, true, true),

	/**
	 * Any empty single point on the enemy-side of the battle field.
	 */
	SINGLE_ENEMY_EMPTY(false, true, false, true, false, false),

	/**
	 * Any busy(with hero or summoned creature) single point on the enemy-side of the battle field.
	 */
	SINGLE_ENEMY_BUSY(false, true, false, false, true, true),

	/**
	 * Any single point with hero on the enemy-side of the battle field.
	 */
	SINGLE_ENEMY_HERO(false, true, false, false, true, false),

	/**
	 * Any single point with summoned creature on the enemy-side of the battle field.
	 */
	SINGLE_ENEMY_SUMMON(false, true, false, false, false, true),

	/**
	 * Any single point on the your-side of the battle field.
	 */
	SINGLE_FRIEND(false, false, true, true, true, true),

	/**
	 * Any empty single point on the your-side of the battle field.
	 */
	SINGLE_FRIEND_EMPTY(false, false, true, true, false, false),

	/**
	 * Any busy(with hero or summoned creature) single point on the your-side of the battle field.
	 */
	SINGLE_FRIEND_BUSY(false, false, true, false, true, true),

	/**
	 * Any single point with hero on the your-side of the battle field.
	 */
	SINGLE_FRIEND_HERO(false, false, true, false, true, false),

	/**
	 * Any single point with summoned creature on the your-side of the battle field.
	 */
	SINGLE_FRIEND_SUMMON(false, false, true, false, false, true),


	/**
	 * All points on battle field.
	 */
	ALL(true, true, true, true, true, true),

	/**
	 * All empty points on the battle field.
	 */
	ALL_EMPTY(true, true, true, true, false, false),

	/**
	 * All busy(with hero or summoned creature) points on the battle field.
	 */
	ALL_BUSY(true, true, true, false, true, true),

	/**
	 * All points with hero on the battle field.
	 */
	ALL_HERO(true, true, true, false, true, false),

	/**
	 * All points with summoned creature on the battle field.
	 */
	ALL_SUMMON(true, true, true, false, false, true),

	/**
	 * All points on the enemy-side of the battle field.
	 */
	ALL_ENEMY(true, true, false, true, true, true),

	/**
	 * All empty points on the enemy-side of the battle field.
	 */
	ALL_ENEMY_EMPTY(true, true, false, true, false, false),

	/**
	 * All busy(with hero or summoned creature) points on the enemy-side of the battle field.
	 */
	ALL_ENEMY_BUSY(true, true, false, false, true, true),

	/**
	 * All points with hero on the enemy-side of the battle field.
	 */
	ALL_ENEMY_HERO(true, true, false, false, true, false),

	/**
	 * ALL points with summoned creature on the enemy-side of the battle field.
	 */
	ALL_ENEMY_SUMMON(true, true, false, false, false, true),

	/**
	 * All points on the your-side of the battle field.
	 */
	ALL_FRIEND(true, false, true, true, true, true),

	/**
	 * All empty points on the your-side of the battle field.
	 */
	ALL_FRIEND_EMPTY(true, false, true, true, false, false),

	/**
	 * All busy(with hero or summoned creature) points on the your-side of the battle field.
	 */
	ALL_FRIEND_BUSY(true, false, true, false, true, true),

	/**
	 * All points with hero on the your-side of the battle field.
	 */
	ALL_FRIEND_HERO(true, false, true, false, true, false),

	/**
	 * All points with summoned creature on the your-side of the battle field.
	 */
	ALL_FRIEND_SUMMON(true, false, true, false, false, true);

	private final int order;

	private final boolean massive;

	private final boolean supportsEnemySide;
	private final boolean supportsFriendSide;

	private final boolean supportsEmptyPoint;
	private final boolean supportsHeroPoint;
	private final boolean supportsSummonPoint;

	TargetType(final boolean massive,
			   final boolean supportsEnemySide,
			   final boolean supportsFriendSide,
			   final boolean supportsEmptyPoint,
			   final boolean supportsHeroPoint,
			   final boolean supportsSummonPoint) {
		this.massive = massive;
		this.supportsEnemySide = supportsEnemySide;
		this.supportsFriendSide = supportsFriendSide;
		this.supportsEmptyPoint = supportsEmptyPoint;
		this.supportsHeroPoint = supportsHeroPoint;
		this.supportsSummonPoint = supportsSummonPoint;

		order = (massive ? 32 : 0) +
				(supportsEnemySide ? 16 : 0) +
				(supportsFriendSide ? 8 : 0) +
				(supportsEmptyPoint ? 4 : 0) +
				(supportsHeroPoint ? 2 : 0) +
				(supportsSummonPoint ? 1 : 0);
	}

	public int getOrder() {
		return order;
	}

	public boolean isMassive() {
		return massive;
	}

	public boolean supportsEnemySide() {
		return supportsEnemySide;
	}

	public boolean supportsFriendSide() {
		return supportsFriendSide;
	}

	public boolean supportsEmptyPoint() {
		return supportsEmptyPoint;
	}

	public boolean supportsHeroPoint() {
		return supportsHeroPoint;
	}

	public boolean supportsSummonPoint() {
		return supportsSummonPoint;
	}
}
