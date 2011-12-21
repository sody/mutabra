package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroImpl;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
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
	private int mentalPower;

	@Unindexed
	private List<Key<CardImpl>> deck = new ArrayList<Key<CardImpl>>();

	@Unindexed
	private List<Key<CardImpl>> hand = new ArrayList<Key<CardImpl>>();

	@Unindexed
	private List<Key<CardImpl>> graveyard = new ArrayList<Key<CardImpl>>();

	@Transient
	private List<Card> deckValueHolder = new ArrayList<Card>();

	@Transient
	private List<Card> handValueHolder = new ArrayList<Card>();

	@Transient
	private List<Card> graveyardValueHolder = new ArrayList<Card>();

	@Transient
	private List<BattleCreature> creaturesValueHolder = new ArrayList<BattleCreature>();

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

	public int getMentalPower() {
		return mentalPower;
	}

	public void setMentalPower(final int mentalPower) {
		this.mentalPower = mentalPower;
	}

	public List<Card> getDeck() {
		return deckValueHolder;
	}

	public List<Card> getHand() {
		return handValueHolder;
	}

	public List<Card> getGraveyard() {
		return graveyardValueHolder;
	}

	public List<BattleCreature> getCreatures() {
		return creaturesValueHolder;
	}

	@Override
	public Key<?> getParentKey() {
		return battle;
	}

	@PostLoad
	void loadRelations(final Objectify session) {
		deckValueHolder = new ArrayList<Card>(session.get(deck).values());
		handValueHolder = new ArrayList<Card>(session.get(hand).values());
		graveyardValueHolder = new ArrayList<Card>(session.get(graveyard).values());
		creaturesValueHolder = new ArrayList<BattleCreature>(session.query(BattleCreatureImpl.class).ancestor(this).list());
	}

	@PrePersist
	void saveRelations(final Objectify session) {
		deck = Keys.getKeys(CardImpl.class, deckValueHolder);
		hand = Keys.getKeys(CardImpl.class, handValueHolder);
		graveyard = Keys.getKeys(CardImpl.class, graveyardValueHolder);
		session.put(creaturesValueHolder);
	}
}
