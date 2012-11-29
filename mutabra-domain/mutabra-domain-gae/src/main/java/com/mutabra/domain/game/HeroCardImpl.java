package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;

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

    public HeroCardImpl() {
    }

    public HeroCardImpl(final Hero hero) {
        this.hero = Keys.getKey(hero);
    }

    public Hero getHero() {
        return Keys.getInstance(hero);
    }

    public Card getCard() {
        return Keys.getInstance(card);
    }

    public void setCard(final Card card) {
        this.card = Keys.getKey(card);
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }

    @Override
    public Key<?> getParentKey() {
        return hero;
    }
}
