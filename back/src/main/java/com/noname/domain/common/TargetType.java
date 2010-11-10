package com.noname.domain.common;

import org.greatage.hibernate.type.OrderedEnum;

/**
 * @author Ivan Khalopik
 */
public enum TargetType implements OrderedEnum {
	UNKNOWN(0),

	SINGLE(1),
	SINGLE_ENEMY(2),
	SINGLE_FRIEND(3),

	ALL(4),
	ALL_ENEMY(5),
	ALL_FRIEND(6),

	PLAYER(7),
	PLAYER_ENEMY(8),
	PLAYER_FRIEND(9);

	private final int order;

	TargetType(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}
}
