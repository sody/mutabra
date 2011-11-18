package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_SUMMON)
public class BattleSummonImpl extends BaseEntityImpl implements BattleSummon {

	@Parent
	private Key<BattleMemberImpl> owner;

	private Key<HeroCardImpl> card;

	private int health;
	private int position;

	public BattleSummonImpl() {
	}

	public BattleSummonImpl(final BattleMember owner, final HeroCard card) {
		this.owner = Keys.getKey(owner);
		this.card = Keys.getKey(card);
		health = card.getCard().getSummon().getDefence();
	}

	public BattleMember getOwner() {
		return Keys.getInstance(owner);
	}

	public HeroCard getCard() {
		return Keys.getInstance(card);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	@Override
	public Key<?> getParentKey() {
		return owner;
	}
}
