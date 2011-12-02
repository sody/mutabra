package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroImpl;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_MEMBER)
public class BattleMemberImpl extends BaseEntityImpl implements BattleMember {

	@Parent
	private Key<BattleImpl> battle;

	private Key<HeroImpl> hero;

	@Unindexed
	@Embedded
	private Position position = new Position();

	@Unindexed
	private int health;

	public BattleMemberImpl() {
	}

	public BattleMemberImpl(final Battle battle, final Hero hero) {
		this.battle = Keys.getKey(battle);
		this.hero = Keys.getKey(hero);
		health = hero.getHealth();
	}

	public Battle getBattle() {
		return Keys.getInstance(battle);
	}

	public Hero getHero() {
		return Keys.getInstance(hero);
	}

	public Set<BattleCard> getCards() {
		return Keys.getChildren(BattleCard.class, BattleCardImpl.class, this);
	}

	public Set<BattleCard> getDeck() {
		return Keys.getChildren(BattleCard.class, BattleCardImpl.class, this, "inHand =", false);
	}

	public Set<BattleCard> getHand() {
		return Keys.getChildren(BattleCard.class, BattleCardImpl.class, this, "inHand =", true);
	}

	public Set<BattleSummon> getSummons() {
		return Keys.getChildren(BattleSummon.class, BattleSummonImpl.class, this);
	}

	public Position getPosition() {
		return position;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	@Override
	public Key<?> getParentKey() {
		return battle;
	}
}
