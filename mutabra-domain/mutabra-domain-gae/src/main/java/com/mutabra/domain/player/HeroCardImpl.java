package com.mutabra.domain.player;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.security.AccountImpl;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.HERO_CARD)
public class HeroCardImpl extends BaseEntityImpl implements HeroCard {

	@Parent
	private Key<HeroImpl> hero;

	private Key<CardImpl> card;

	private long rating;

	public Hero getHero() {
		return Keys.getInstance(hero);
	}

	public void setHero(final Hero hero) {
		final Key<AccountImpl> parent = new Key<AccountImpl>(AccountImpl.class, hero.getAccount().getId());
		this.hero = new Key<HeroImpl>(parent, HeroImpl.class, hero.getId());
	}

	public Card getCard() {
		return Keys.getInstance(card);
	}

	public void setCard(final Card card) {
		this.card = new Key<CardImpl>(CardImpl.class, card.getId());
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}
}
