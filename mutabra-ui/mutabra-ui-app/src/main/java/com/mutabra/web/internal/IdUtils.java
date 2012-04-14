package com.mutabra.web.internal;

import com.mutabra.domain.common.Card;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IdUtils {

	public static String generateId(final Card card) {
		return "card_" + card.getCode();
	}

	public static String generateDescriptionId(final Card card) {
		return "description_" + card.getCode();
	}
}
