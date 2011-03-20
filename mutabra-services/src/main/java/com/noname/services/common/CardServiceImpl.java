package com.noname.services.common;

import com.mutabra.domain.Translation;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.Summon;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardServiceImpl
		extends CodedEntityServiceImpl<Card, CardQuery>
		implements CardService {

	public CardServiceImpl(final EntityRepository repository, final TranslationService translationService) {
		super(repository, translationService, Card.class, CardQuery.class);
	}

	@Override
	public Card create(final CardType cardType) {
		final Card card = create();
		card.setType(cardType);
		return card;
	}

	@Override
	protected void deleteTranslations(final Card card) {
		super.deleteTranslations(card);
		final Summon summon = card.getSummon();
		if (summon != null) {
			for (Translation translation : summon.getTranslations().values()) {
				getTranslationService().delete(translation);
			}
		}
		final Effect effect = card.getEffect();
		if (effect != null) {
			for (Translation translation : effect.getTranslations().values()) {
				getTranslationService().delete(translation);
			}
		}
	}

	@Override
	protected void saveTranslations(final Card card) {
		super.saveTranslations(card);
		final Summon summon = card.getSummon();
		if (summon != null) {
			getTranslationService().saveTranslations(summon, summon.getTranslations());
		}
		final Effect effect = card.getEffect();
		if (effect != null) {
			getTranslationService().saveTranslations(effect, effect.getTranslations());
		}
	}

	@Override
	protected void initializeTranslations(final Card card, final Locale locale) {
		super.initializeTranslations(card, locale);
		final Summon summon = card.getSummon();
		if (summon != null) {
			final Map<String, Translation> summonTranslations = getTranslationService().getTranslations(summon, locale);
			summon.setTranslations(summonTranslations);
		}
		final Effect effect = card.getEffect();
		if (effect != null) {
			final Map<String, Translation> summonTranslations = getTranslationService().getTranslations(effect, locale);
			effect.setTranslations(summonTranslations);
		}
	}

}
