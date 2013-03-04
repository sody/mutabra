package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.game.HeroImpl;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE)
public class BattleImpl extends BaseEntityImpl implements Battle {

    private boolean active;
    private int round;
    private Date startedAt;
    private List<BattleHero> heroes = new ArrayList<BattleHero>();
    private List<BattleEffect> effects = new ArrayList<BattleEffect>();

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
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
        return heroes;
    }

    public List<BattleEffect> getEffects() {
        return effects;
    }


    public boolean isAllReady() {
        for (BattleHero battleHero : heroes) {
            if (!battleHero.isAllReady()) {
                return false;
            }
        }
        return true;
    }

    public BattleHero createHero() {
        return new BattleHeroImpl();
    }

    public BattleCard createCard() {
        return new BattleCardImpl();
    }

    public BattleCreature createCreature() {
        return new BattleCreatureImpl();
    }

    public BattleAbility createAbility() {
        return new BattleAbilityImpl();
    }

    public BattleEffect createEffect() {
        return new BattleEffectImpl();
    }

    @PostLoad
    void loadLinks() {
        final Map<Key<HeroImpl>, BattleHero> heroByKey = new HashMap<Key<HeroImpl>, BattleHero>();
        final Map<Long, BattleCreature> creatureById = new HashMap<Long, BattleCreature>();
        final Map<Long, BattleHero> heroByCreatureId = new HashMap<Long, BattleHero>();
        for (BattleHero battleHero : heroes) {
            heroByKey.put(((BattleHeroImpl) battleHero).getHeroKey(), battleHero);
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                creatureById.put(battleCreature.getId(), battleCreature);
                heroByCreatureId.put(battleCreature.getId(), battleHero);
            }
        }

        for (BattleEffect battleEffect : effects) {
            final BattleTargetImpl caster = (BattleTargetImpl) battleEffect.getCaster();
            if (caster.getCreatureId() != null) {
                caster.setCreature(creatureById.get(caster.getCreatureId()));
                caster.setHero(heroByCreatureId.get(caster.getCreatureId()));
            } else if (caster.getHeroKey() != null) {
                caster.setHero(heroByKey.get(caster.getHeroKey()));
            }

            final BattleTargetImpl target = (BattleTargetImpl) battleEffect.getTarget();
            if (target.getCreatureId() != null) {
                target.setCreature(creatureById.get(target.getCreatureId()));
                target.setHero(heroByCreatureId.get(target.getCreatureId()));
            } else if (target.getHeroKey() != null) {
                target.setHero(heroByKey.get(target.getHeroKey()));
            }
        }
    }

    @PrePersist
    void generateIds(final Objectify session) {
        for (BattleHero battleHero : heroes) {
            // assign creature identifiers
            // they should be unique within hero
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                if (battleCreature.getId() == null) {
                    final long id = ((BattleHeroImpl) battleHero).nextCreatureId();
                    ((BattleCreatureImpl) battleCreature).assignId(id);
                }

                // assign ability identifiers
                // they should be unique within creature
                for (BattleAbility battleAbility : battleCreature.getAbilities()) {
                    if (battleAbility.getId() == null) {
                        final long id = ((BattleCreatureImpl) battleCreature).nextAbilityId();
                        ((BattleAbilityImpl) battleAbility).assignId(id);
                    }
                }
            }

            // assign card identifiers
            // they should be unique within hero
            for (BattleCard battleCard : battleHero.getCards()) {
                if (battleCard.getId() == null) {
                    final long id = ((BattleHeroImpl) battleHero).nextCardId();
                    ((BattleCardImpl) battleCard).assignId(id);
                }
            }
        }
    }
}
