package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.PrePersist;
import com.mutabra.domain.BaseEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity("battles")
public class Battle extends BaseEntity {

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

    @PostLoad
    void loadLinks() {
        final Map<ObjectId, BattleHero> heroByKey = new HashMap<ObjectId, BattleHero>();
        final Map<Long, BattleCreature> creatureById = new HashMap<Long, BattleCreature>();
        final Map<Long, BattleHero> heroByCreatureId = new HashMap<Long, BattleHero>();
        for (BattleHero battleHero : heroes) {
            heroByKey.put(battleHero.getId(), battleHero);
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                creatureById.put(battleCreature.getId(), battleCreature);
                heroByCreatureId.put(battleCreature.getId(), battleHero);
            }
        }

        for (BattleEffect battleEffect : effects) {
            final BattleTarget caster = battleEffect.getCaster();
            if (caster.getCreatureId() != null) {
                caster.setCreature(creatureById.get(caster.getCreatureId()));
                caster.setHero(heroByCreatureId.get(caster.getCreatureId()));
            } else if (caster.getHeroId() != null) {
                caster.setHero(heroByKey.get(caster.getHeroId()));
            }

            final BattleTarget target = battleEffect.getTarget();
            if (target.getCreatureId() != null) {
                target.setCreature(creatureById.get(target.getCreatureId()));
                target.setHero(heroByCreatureId.get(target.getCreatureId()));
            } else if (target.getHeroId() != null) {
                target.setHero(heroByKey.get(target.getHeroId()));
            }
        }
    }

    @PrePersist
    void generateIds() {
        for (BattleHero battleHero : heroes) {
            // assign creature identifiers
            // they should be unique within hero
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                if (battleCreature.getId() == null) {
                    battleCreature.assignId(battleHero.nextCreatureId());
                }

                // assign ability identifiers
                // they should be unique within creature
                for (BattleAbility battleAbility : battleCreature.getAbilities()) {
                    if (battleAbility.getId() == null) {
                        battleAbility.assignId(battleCreature.nextAbilityId());
                    }
                }
            }

            // assign card identifiers
            // they should be unique within hero
            for (BattleCard battleCard : battleHero.getCards()) {
                if (battleCard.getId() == null) {
                    battleCard.assignId(battleHero.nextCardId());
                }
            }
        }
    }
}
