package com.mutabra.domain.player;

import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;

import javax.persistence.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "HERO_CARD")
public class HeroCardImpl extends BaseEntityImpl implements HeroCard {

	@ManyToOne(targetEntity = HeroImpl.class)
	@JoinColumn(name = "ID_HERO", nullable = false)
	private Hero hero;

	@ManyToOne(targetEntity = CardImpl.class)
	@JoinColumn(name = "ID_CARD", nullable = false)
	private Card card;

	@Column(name = "RATING", nullable = false)
	private long rating;

	public Hero getHero() {
		return hero;
	}

	public void setHero(final Hero hero) {
		this.hero = hero;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(final Card card) {
		this.card = card;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}
}
