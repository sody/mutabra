package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;

import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Hero extends BaseEntity {

	Account getAccount();

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

	int getHealth();

	void setHealth(int health);

	List<HeroCard> getCards();

	Battle getBattle();

	void setBattle(Battle battle);

	Date getLastActive();

	void setLastActive(Date lastActive);

	boolean isReady();

	void setReady(boolean ready);
}
