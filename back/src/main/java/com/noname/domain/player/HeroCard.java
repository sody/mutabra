package com.noname.domain.player;

import com.noname.domain.BaseEntity;
import com.noname.domain.common.Card;

import javax.persistence.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "HERO_CARD")
public class HeroCard extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "ID_HERO", nullable = false)
	private Hero hero;

	@ManyToOne
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
