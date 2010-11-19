package com.noname.services.common;

import com.noname.domain.common.Card;
import com.noname.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardQuery extends CodedEntityQuery<Card, CardQuery> implements CardFilter {

	public CardQuery() {
		super(Card.class);
	}

}
