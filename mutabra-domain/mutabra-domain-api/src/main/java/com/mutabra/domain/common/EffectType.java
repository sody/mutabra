package com.mutabra.domain.common;

import org.greatage.hibernate.type.OrderedEnum;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum EffectType implements OrderedEnum {
	UNKNOWN(0),
	SUMMON(1),
	EFFECT(2);

	private final int order;

	EffectType(final int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}
}
