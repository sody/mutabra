package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroImpl;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_MEMBER)
public class BattleHeroImpl extends BattleUnitImpl implements BattleHero {

	@Parent
	private Key<BattleImpl> battle;

	@Unindexed
	private Key<HeroImpl> hero;

	@Unindexed
	private List<Key<CardImpl>> deck = new ArrayList<Key<CardImpl>>();

	@Unindexed
	private List<Key<CardImpl>> hand = new ArrayList<Key<CardImpl>>();

	@Unindexed
	private List<Key<CardImpl>> graveyard = new ArrayList<Key<CardImpl>>();

	@Unindexed
	private int mentalPower;

	public BattleHeroImpl() {
	}

	public BattleHeroImpl(final Battle battle, final Hero hero) {
		this.battle = Keys.getKey(battle);
		this.hero = Keys.getKey(hero);
	}

	public Battle getBattle() {
		return Keys.getInstance(battle);
	}

	public Hero getHero() {
		return Keys.getInstance(hero);
	}

	public List<Card> getDeck() {
		return Keys.getInstances(Card.class, deck);
	}

	public void setDeck(final List<Card> deck) {
		this.deck = new ArrayList<Key<CardImpl>>();
		for (Card card : deck) {
			this.deck.add(Keys.<Card, CardImpl>getKey(card));
		}
	}

	public List<Card> getHand() {
		return Keys.getInstances(Card.class, hand);
	}

	public void setHand(final List<Card> hand) {
		this.hand = new ArrayList<Key<CardImpl>>();
		for (Card card : hand) {
			this.hand.add(Keys.<Card, CardImpl>getKey(card));
		}
	}

	public List<Card> getGraveyard() {
		return Keys.getInstances(Card.class, graveyard);
	}

	public void setGraveyard(final List<Card> graveyard) {
		this.graveyard = new ArrayList<Key<CardImpl>>();
		for (Card card : graveyard) {
			this.graveyard.add(Keys.<Card, CardImpl>getKey(card));
		}
	}

	public List<BattleCreature> getCreatures() {
		return Keys.getChildren(BattleCreature.class, BattleCreatureImpl.class, this);
	}

	public int getMentalPower() {
		return mentalPower;
	}

	public void setMentalPower(final int mentalPower) {
		this.mentalPower = mentalPower;
	}

	@Override
	public Key<?> getParentKey() {
		return battle;
	}
}
