package com.mutabra.services.common;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import com.mutabra.services.CodedEntityServiceImpl;
import org.greatage.domain.EntityRepository;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardServiceImpl extends CodedEntityServiceImpl<Card> implements CardService {
	private final Class<? extends Card> realCardClass;

	public CardServiceImpl(final EntityRepository repository) {
		super(repository, Card.class);
		//noinspection unchecked
		realCardClass = repository.create(Card.class).getClass();
	}

	@Override
	public Card create(final String code) {
		return create(code, CardType.UNKNOWN);
	}

	public Card create(final String code, final CardType type) {
		return ReflectionUtils.newInstance(realCardClass, code, type);
	}
}
