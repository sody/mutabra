package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.FaceImpl;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.LevelImpl;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.RaceImpl;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.AccountImpl;

import javax.persistence.Entity;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.HERO)
public class HeroImpl extends BaseEntityImpl implements Hero {

	@Parent
	private Key<AccountImpl> account;

	private String name;

	private Key<RaceImpl> race;

	private Key<FaceImpl> face;

	private Key<LevelImpl> level;

	private long rating;

	private int attack;

	private int defence;

	private Key<BattleImpl> battle;

	public HeroImpl() {
	}

	public HeroImpl(final Account account) {
		this.account = Keys.getKey(account);
	}

	public Account getAccount() {
		return Keys.getInstance(account);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}

	public Face getFace() {
		return Keys.getInstance(face);
	}

	public void setFace(final Face face) {
		this.face = Keys.getKey(face);
	}

	public Race getRace() {
		return Keys.getInstance(race);
	}

	public void setRace(final Race race) {
		this.race = Keys.getKey(race);
	}

	public Level getLevel() {
		return Keys.getInstance(level);
	}

	public void setLevel(final Level level) {
		this.level = Keys.getKey(level);
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(final int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(final int defence) {
		this.defence = defence;
	}

	public Set<HeroCard> getCards() {
		return Keys.getChildren(HeroCard.class, HeroCardImpl.class, this);
	}

	public Battle getBattle() {
		return Keys.getInstance(battle);
	}

	public void setBattle(final Battle battle) {
		this.battle = Keys.getKey(battle);
	}

	@Override
	public Key<?> getParentKey() {
		return account;
	}
}
