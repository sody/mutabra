package com.noname.services.common;

import com.noname.domain.common.Card;
import com.noname.domain.common.CardType;
import com.noname.domain.common.EffectCard;
import com.noname.domain.common.SummonCard;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 */
public class CardServiceImpl
		extends CodedEntityServiceImpl<Card, CardQuery>
		implements CardService {

	public CardServiceImpl(final EntityRepository repository, final TranslationService translationService) {
		super(repository, translationService, Card.class, CardQuery.class);
	}

	@Override
	public Card create(CardType cardType) {
		switch (cardType) {
			case SUMMON:
				return new SummonCard();
			case EFFECT:
				return new EffectCard();
			default:
				return super.create();
		}
	}
}
