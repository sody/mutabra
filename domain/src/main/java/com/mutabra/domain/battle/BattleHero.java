/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroLevel;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleHero implements BattleUnit {

    @Indexed
    private ObjectId id;

    private HeroAppearance appearance = new HeroAppearance();
    private HeroLevel level = new HeroLevel();
    private int health;
    private int mentalPower;
    private BattlePosition position;
    private boolean ready;
    private List<BattleCard> cards = new ArrayList<BattleCard>();
    private List<BattleCreature> creatures = new ArrayList<BattleCreature>();

    @Transient
    private Battle battle;

    protected BattleHero() {
    }

    public BattleHero(final Battle battle, final ObjectId id) {
        this.battle = battle;
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public Battle getBattle() {
        return battle;
    }

    public HeroAppearance getAppearance() {
        return appearance;
    }

    public HeroLevel getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public int getMentalPower() {
        return mentalPower;
    }

    public void setMentalPower(final int mentalPower) {
        this.mentalPower = mentalPower;
    }

    public BattlePosition getPosition() {
        return position;
    }

    public void setPosition(final BattlePosition position) {
        this.position = position;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(final boolean ready) {
        this.ready = ready;
    }

    public List<BattleCard> getCards() {
        return cards;
    }

    public List<BattleCreature> getCreatures() {
        return creatures;
    }

    public boolean isHero() {
        return true;
    }

    public boolean isAllReady() {
        for (BattleCreature battleCreature : creatures) {
            if (!battleCreature.isReady()) {
                return false;
            }
        }
        return isReady();
    }

    public List<BattleCard> getDeck() {
        final List<BattleCard> deck = new ArrayList<BattleCard>();
        for (BattleCard battleCard : cards) {
            if (battleCard.getType() == BattleCardType.DECK) {
                deck.add(battleCard);
            }
        }
        return deck;
    }

    public List<BattleCard> getHand() {
        final List<BattleCard> hand = new ArrayList<BattleCard>();
        for (BattleCard battleCard : cards) {
            if (battleCard.getType() == BattleCardType.HAND) {
                hand.add(battleCard);
            }
        }
        return hand;
    }

    public List<BattleCard> getGraveyard() {
        final List<BattleCard> graveyard = new ArrayList<BattleCard>();
        for (BattleCard battleCard : cards) {
            if (battleCard.getType() == BattleCardType.GRAVEYARD) {
                graveyard.add(battleCard);
            }
        }
        return graveyard;
    }

    /* HELPER METHODS */
    void assignBattle(final Battle battle) {
        this.battle = battle;
    }
}
