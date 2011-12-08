package com.mutabra.domain.battle;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.game.Hero;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleHero extends BattleUnit {

	Battle getBattle();

	Hero getHero();

	List<Card> getDeck();

	void setDeck(List<Card> deck);

	List<Card> getHand();

	void setHand(List<Card> hand);

	List<Card> getGraveyard();

	void setGraveyard(List<Card> graveyard);

	List<BattleCreature> getCreatures();

	int getMentalPower();

	void setMentalPower(int mentalPower);
}
