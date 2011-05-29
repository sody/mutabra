package com.mutabra.services.common;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import com.mutabra.services.CodedEntityServiceImpl;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardServiceImpl
		extends CodedEntityServiceImpl<Card, CardQuery>
		implements CardService {

	public CardServiceImpl(final EntityRepository repository) {
		super(repository, Card.class, CardQuery.class);
	}

	public Card create(final CardType cardType) {
		final Card card = create();
		card.setType(cardType);
		return card;
	}
}
