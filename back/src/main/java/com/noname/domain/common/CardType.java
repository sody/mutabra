package com.noname.domain.common;

import org.greatage.hibernate.type.OrderedEnum;

/**
 * @author Ivan Khalopik
 */
public enum CardType implements OrderedEnum {
	UNKNOWN(0),
	EFFECT(1),
	SUMMON(2);

	private final int order;

	CardType(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}
}
