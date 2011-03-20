package com.mutabra.domain.player;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.security.Account;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Hero extends BaseEntity {

	Account getAccount();

	void setAccount(Account account);

	String getName();

	void setName(String name);

	long getRating();

	void setRating(long rating);

	Face getFace();

	void setFace(Face face);

	Race getRace();

	void setRace(Race race);

	Level getLevel();

	void setLevel(Level level);

	int getAttack();

	void setAttack(int attack);

	int getDefence();

	void setDefence(int defence);

	Set<HeroCard> getCards();
}
