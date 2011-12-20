package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleImpl;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.FaceImpl;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.LevelImpl;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.RaceImpl;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

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

	private int health;

	private Key<BattleImpl> battle;

	@Indexed
	private Date lastActive;

	@Unindexed
	private boolean ready;

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

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	public List<HeroCard> getCards() {
		return Keys.getChildren(HeroCard.class, HeroCardImpl.class, this);
	}

	public Battle getBattle() {
		return Keys.getInstance(battle);
	}

	public void setBattle(final Battle battle) {
		this.battle = Keys.getKey(battle);
	}

	public Date getLastActive() {
		return lastActive;
	}

	public void setLastActive(final Date lastActive) {
		this.lastActive = lastActive;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(final boolean ready) {
		this.ready = ready;
	}

	@Override
	public Key<?> getParentKey() {
		return account;
	}
}
