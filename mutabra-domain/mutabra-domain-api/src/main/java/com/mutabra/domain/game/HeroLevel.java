package com.mutabra.domain.game;

/**
 * @author Ivan Khalopik
 */
public interface HeroLevel {

    String getCode(); //REF BY CODE

    void setCode(String code);

    long getRating();

    void setRating(long rating);

    long getNextLevelRating();

    void setNextLevelRating(long rating);
}
