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
@Entity(name = Tables.BATTLE_CARD)
public class BattleCardImpl extends BaseEntityImpl implements BattleCard {

	@Parent
	private Key<BattleMemberImpl> owner;

	private Key<HeroCardImpl> card;

	public BattleCardImpl() {
	}

	public BattleCardImpl(final BattleMember owner, final HeroCard card) {
		this.owner = Keys.getKey(owner);
		this.card = Keys.getKey(card);
	}

	public BattleMember getOwner() {
		return Keys.getInstance(owner);
	}

	public HeroCard getCard() {
		return Keys.getInstance(card);
	}

	@Override
	public Key<?> getParentKey() {
		return owner;
	}
}
