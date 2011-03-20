package com.mutabra.services.common;

import com.mutabra.domain.common.Card;
import com.mutabra.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardQuery extends CodedEntityQuery<Card, CardQuery> implements CardFilter {

	public CardQuery() {
		super(Card.class);
	}

}
