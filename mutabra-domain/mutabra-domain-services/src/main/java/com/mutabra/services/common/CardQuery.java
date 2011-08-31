package com.mutabra.services.common;

import com.mutabra.domain.common.Card;
import com.mutabra.services.CodedEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardQuery extends CodedEntityQuery<Card, CardQuery> {

	public CardQuery(final EntityRepository repository) {
		super(repository, Card.class);
	}
}
