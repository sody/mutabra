package com.mutabra.domain.battle;

import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroLevel;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleHero {

    Hero getHero(); //LAZY REF

    void setHero(Hero hero);

    HeroAppearance getAppearance();

    HeroLevel getLevel();

    int getHealth();

    void setHealth(int health);

    int getMentalPower();

    void setMentalPower(int mentalPower);

    Position getPosition();

    void setPosition(Position position);

    boolean isReady();

    void setReady(boolean ready);

    List<BattleCard> getCards();

    List<BattleCreature> getCreatures();

    /* HELPERS */
    boolean isAllReady();

    List<BattleCard> getDeck();

    List<BattleCard> getHand();

    List<BattleCard> getGraveyard();
}
