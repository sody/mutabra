package com.mutabra.domain.player;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.common.Card;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HeroCard extends BaseEntity {

	Hero getHero();

	void setHero(final Hero hero);

	Card getCard();

	void setCard(final Card card);

	long getRating();

	void setRating(final long rating);
}
