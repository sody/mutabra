package com.mutabra.domain.player;

import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable
public class HeroCardImpl extends BaseEntityImpl implements HeroCard {

	@Persistent
	private HeroImpl hero;

	@Persistent
	private CardImpl card;

	@Persistent
	private long rating;

	public Hero getHero() {
		return hero;
	}

	public void setHero(final Hero hero) {
		this.hero = (HeroImpl) hero;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(final Card card) {
		this.card = (CardImpl) card;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}
}
