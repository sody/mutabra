package com.mutabra.domain.player;

import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.db.Tables;
import com.mutabra.domain.common.*;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.AccountImpl;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.HERO)
public class HeroImpl extends BaseEntityImpl implements Hero {

	@Persistent
	private AccountImpl account;

	@Persistent
	private String name;

	@Persistent
	private FaceImpl face;

	@Persistent
	private RaceImpl race;

	@Persistent
	private LevelImpl level;

	@Persistent
	private long rating;

	@Persistent
	private int attack;

	@Persistent
	private int defence;

	@Persistent
	private Set<HeroCard> cards = new HashSet<HeroCard>();

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = (AccountImpl) account;
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
		return face;
	}

	public void setFace(final Face face) {
		this.face = (FaceImpl) face;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(final Race race) {
		this.race = (RaceImpl) race;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = (LevelImpl) level;
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
		return cards;
	}
}
