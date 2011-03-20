package com.mutabra.domain.player;

import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.security.Account;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "HERO")
public class HeroImpl extends BaseEntityImpl implements Hero {

	@ManyToOne
	@JoinColumn(name = "ID_ACCOUNT", nullable = false)
	private Account account;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "ID_FACE", nullable = false)
	private Face face;

	@ManyToOne
	@JoinColumn(name = "ID_RACE", nullable = false)
	private Race race;

	@ManyToOne
	@JoinColumn(name = "ID_LEVEL", nullable = false)
	private Level level;

	@Column(name = "RATING", nullable = false)
	private long rating;

	@Column(name = "ATTACK", nullable = false)
	private int attack;

	@Column(name = "DEFENCE", nullable = false)
	private int defence;

	@OneToMany(mappedBy = "hero")
	private Set<HeroCard> cards = new HashSet<HeroCard>();

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
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
		this.face = face;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(final Race race) {
		this.race = race;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
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
