package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.game.HeroCard;
import com.mutabra.domain.game.HeroCardImpl;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_CARD)
public class BattleCardImpl extends BaseEntityImpl implements BattleCard {

	@Parent
	private Key<BattleMemberImpl> owner;

	@Unindexed
	private Key<HeroCardImpl> card;

	@Indexed
	private BattleCardState state;

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

	public BattleCardState getState() {
		return state;
	}

	public void setState(final BattleCardState state) {
		this.state = state;
	}

	@Override
	public Key<?> getParentKey() {
		return owner;
	}
}
