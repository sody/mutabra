package com.mutabra.domain.battle;

import com.googlecode.objectify.Objectify;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE)
public class BattleImpl extends BaseEntityImpl implements Battle {
	private int round;
	private Date startedAt;
	private BattleState state;

	@Transient
	private List<BattleHero> heroesValueHolder = new ArrayList<BattleHero>();

	@Transient
	private List<BattleEffect> effectsValueHolder = new ArrayList<BattleEffect>();

	public BattleState getState() {
		return state;
	}

	public void setState(final BattleState state) {
		this.state = state;
	}

	public int getRound() {
		return round;
	}

	public void setRound(final int round) {
		this.round = round;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(final Date startedAt) {
		this.startedAt = startedAt;
	}

	public List<BattleHero> getHeroes() {
		return heroesValueHolder;
	}

	public List<BattleEffect> getEffects() {
		return effectsValueHolder;
	}

	public boolean isReady() {
		for (BattleHero hero : getHeroes()) {
			if (!hero.isExhausted()) {
				return false;
			}
			for (BattleCreature creature : hero.getCreatures()) {
				if (!creature.isExhausted()) {
					return false;
				}
			}
		}

		return true;
	}

	@PostLoad
	void loadRelations(final Objectify session) {
		heroesValueHolder = new ArrayList<BattleHero>(session.query(BattleHeroImpl.class).ancestor(this).list());
		effectsValueHolder = new ArrayList<BattleEffect>(session.query(BattleEffectImpl.class).ancestor(this).list());
	}

	@PrePersist
	void saveRelations(final Objectify session) {
		session.put(heroesValueHolder);
		session.put(effectsValueHolder);
	}
}
