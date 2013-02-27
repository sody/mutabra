package com.mutabra.domain.game;

/**
 * @author Ivan Khalopik
 */
public interface HeroAppearance {

    String getName();

    void setName(String name);

    String getFace(); //REF BY CODE

    void setFace(String code);

    String getRace(); //REF BY CODE

    void setRace(String code);
}
