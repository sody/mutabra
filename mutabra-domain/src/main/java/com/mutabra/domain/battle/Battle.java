package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.PrePersist;
import com.mutabra.domain.BaseEntity;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "battles", noClassnameStored = true)
public class Battle extends BaseEntity {

    private boolean active;
    private int round;
    private Date startedAt;

    private List<BattleHero> heroes = new ArrayList<BattleHero>();
    private List<BattleEffect> effects = new ArrayList<BattleEffect>();
    //TODO: move to separate entity?
    private List<BattleLogEntry> log = new ArrayList<BattleLogEntry>();

    // internal id sequences
    private long creatureSequence;

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

    public List<BattleLogEntry> getLog() {
        return log;
    }

    /* HELPER METHODS */
    public boolean isExpired(final long expirationTime) {
        return startedAt.getTime() + expirationTime < System.currentTimeMillis();
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
        final Map<Long, BattleCard> cardById = new HashMap<Long, BattleCard>();
        final Map<Long, BattleAbility> abilityById = new HashMap<Long, BattleAbility>();

        for (BattleHero battleHero : heroes) {
            heroByKey.put(battleHero.getId(), battleHero);
            // assign parents
            battleHero.assignBattle(this);

            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                creatureById.put(battleCreature.getId(), battleCreature);

                // assign parents
                battleCreature.assignHero(battleHero);
                for (BattleAbility battleAbility : battleCreature.getAbilities()) {
                    battleAbility.assignCreature(battleCreature);

                    // assign parents
                    abilityById.put(battleAbility.getId(), battleAbility);
                }
            }

            for (BattleCard battleCard : battleHero.getCards()) {
                cardById.put(battleCard.getId(), battleCard);

                // assign parents
                battleCard.assignHero(battleHero);
            }
        }

        for (BattleEffect battleEffect : effects) {
            updateTarget(battleEffect.getCaster(), heroByKey, cardById, creatureById, abilityById);
            updateTarget(battleEffect.getTarget(), heroByKey, cardById, creatureById, abilityById);
        }
    }

    @PrePersist
    void generateIds() {
        for (BattleHero battleHero : heroes) {
            // assign creature identifiers
            // they should be unique within battle
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                if (battleCreature.getId() == null) {
                    battleCreature.assignId(nextId());
                }

                // assign ability identifiers
                // they should be unique within battle
                for (BattleAbility battleAbility : battleCreature.getAbilities()) {
                    if (battleAbility.getId() == null) {
                        battleAbility.assignId(nextId());
                    }
                }
            }

            // assign card identifiers
            // they should be unique within battle
            for (BattleCard battleCard : battleHero.getCards()) {
                if (battleCard.getId() == null) {
                    battleCard.assignId(nextId());
                }
            }
        }
    }

    private void updateTarget(final BattleTarget target,
                              final Map<ObjectId, BattleHero> heroByKey,
                              final Map<Long, BattleCard> cardById,
                              final Map<Long, BattleCreature> creatureById,
                              final Map<Long, BattleAbility> abilityById) {
        if (target.getCreatureId() != null) {
            target.setUnit(creatureById.get(target.getCreatureId()));
        } else if (target.getHeroId() != null) {
            target.setUnit(heroByKey.get(target.getHeroId()));
        } else if (target.getCardId() != null) {
            target.setSpell(cardById.get(target.getCardId()));
        } else if (target.getAbilityId() != null) {
            target.setSpell(abilityById.get(target.getAbilityId()));
        }
    }

    private long nextId() {
        return creatureSequence++;
    }
}
