package com.noname.services.common;

import com.noname.domain.Translation;
import com.noname.domain.common.*;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;
import org.greatage.util.I18nUtils;

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
		final Card card;
		switch (cardType) {
			case SUMMON:
				card = new SummonCard();
				break;
			case EFFECT:
				card = new EffectCard();
				break;
			default:
				card = new Card();
		}
		initializeTranslations(card, I18nUtils.ROOT_LOCALE);
		return card;
	}

	@Override
	protected void deleteTranslations(final Card card) {
		super.deleteTranslations(card);
		if (card instanceof SummonCard) {
			final Summon summon = ((SummonCard) card).getSummon();
			for (Translation translation : summon.getTranslations().values()) {
				getTranslationService().delete(translation);
			}
		}
		if (card instanceof EffectCard) {
			final Effect effect = ((EffectCard) card).getEffect();
			for (Translation translation : effect.getTranslations().values()) {
				getTranslationService().delete(translation);
			}
		}
	}

	@Override
	protected void saveTranslations(final Card card) {
		super.saveTranslations(card);
		if (card instanceof SummonCard) {
			final Summon summon = ((SummonCard) card).getSummon();
			getTranslationService().saveTranslations(summon, summon.getTranslations());
		}
		if (card instanceof EffectCard) {
			final Effect effect = ((EffectCard) card).getEffect();
			getTranslationService().saveTranslations(effect, effect.getTranslations());
		}
	}

	@Override
	protected void initializeTranslations(final Card card, final Locale locale) {
		super.initializeTranslations(card, locale);
		if (card instanceof SummonCard) {
			final Summon summon = ((SummonCard) card).getSummon();
			final Map<String, Translation> summonTranslations = getTranslationService().getTranslations(summon, locale);
			summon.setTranslations(summonTranslations);
		}
		if (card instanceof EffectCard) {
			final Effect effect = ((EffectCard) card).getEffect();
			final Map<String, Translation> summonTranslations = getTranslationService().getTranslations(effect, locale);
			effect.setTranslations(summonTranslations);
		}
	}

}
