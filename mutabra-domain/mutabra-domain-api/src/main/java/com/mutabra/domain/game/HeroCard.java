package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.common.Card;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HeroCard extends BaseEntity {

	Hero getHero();

	Card getCard();

	void setCard(Card card);

	long getRating();

	void setRating(long rating);
}
