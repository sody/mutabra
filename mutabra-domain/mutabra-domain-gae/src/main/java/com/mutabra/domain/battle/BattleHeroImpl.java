package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.mutabra.domain.Keys;
import com.mutabra.domain.game.AccountImpl;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearanceImpl;
import com.mutabra.domain.game.HeroImpl;
import com.mutabra.domain.game.HeroLevel;
import com.mutabra.domain.game.HeroLevelImpl;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embeddable
public class BattleHeroImpl implements BattleHero {

    @Indexed
    private Key<HeroImpl> hero;

    private HeroAppearance appearance = new HeroAppearanceImpl();
    private HeroLevel level = new HeroLevelImpl();
    private int health;
    private int mentalPower;
    private Position position;
    private boolean ready;
    private List<BattleCard> cards = new ArrayList<BattleCard>();
    private List<BattleCreature> creatures = new ArrayList<BattleCreature>();

    private long cardSequence;
    private long creatureSequence;

    public Hero getHero() {
        return Keys.getInstance(hero);
    }

    public void setHero(final Hero hero) {
        this.hero = Keys.getKey(hero);
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
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

    public Key<HeroImpl> getHeroKey() {
        return hero;
    }

    public Key<AccountImpl> getAccountKey() {
        return hero.getParent();
    }

    long nextCardId() {
        return cardSequence++;
    }

    long nextCreatureId() {
        return creatureSequence++;
    }
}
