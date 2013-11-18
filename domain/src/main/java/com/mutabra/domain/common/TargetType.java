/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.common;

import com.mutabra.domain.CodeUtils;
import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum TargetType implements Translatable {

    /**
     * Has no target.
     */
    NOBODY(false, false, false, false, false, false),

    /**
     * Any single point on battle field.
     */
    SINGLE(false, true, true, true, true, true),

    /**
     * Any empty single point on the battle field.
     */
    SINGLE_EMPTY(false, true, true, true, false, false),

    /**
     * Any single point with unit(hero or summoned creature) on the battle field.
     */
    SINGLE_UNIT(false, true, true, false, true, true),

    /**
     * Any single point with hero on the battle field.
     */
    SINGLE_HERO(false, true, true, false, true, false),

    /**
     * Any single point with summoned creature on the battle field.
     */
    SINGLE_CREATURE(false, true, true, false, false, true),

    /**
     * Any single point on the enemy-side of the battle field.
     */
    SINGLE_ENEMY(false, true, false, true, true, true),

    /**
     * Any empty single point on the enemy-side of the battle field.
     */
    SINGLE_ENEMY_EMPTY(false, true, false, true, false, false),

    /**
     * Any single point with unit(hero or summoned creature) on the enemy-side of the battle field.
     */
    SINGLE_ENEMY_UNIT(false, true, false, false, true, true),

    /**
     * Any single point with hero on the enemy-side of the battle field.
     */
    SINGLE_ENEMY_HERO(false, true, false, false, true, false),

    /**
     * Any single point with summoned creature on the enemy-side of the battle field.
     */
    SINGLE_ENEMY_CREATURE(false, true, false, false, false, true),

    /**
     * Any single point on the friend-side of the battle field.
     */
    SINGLE_FRIEND(false, false, true, true, true, true),

    /**
     * Any empty single point on the friend-side of the battle field.
     */
    SINGLE_FRIEND_EMPTY(false, false, true, true, false, false),

    /**
     * Any single point with unit(hero or summoned creature) on the friend-side of the battle field.
     */
    SINGLE_FRIEND_UNIT(false, false, true, false, true, true),

    /**
     * Any single point with hero on the friend-side of the battle field.
     */
    SINGLE_FRIEND_HERO(false, false, true, false, true, false),

    /**
     * Any single point with summoned creature on the friend-side of the battle field.
     */
    SINGLE_FRIEND_CREATURE(false, false, true, false, false, true),


    /**
     * All points on battle field.
     */
    ALL(true, true, true, true, true, true),

    /**
     * All empty points on the battle field.
     */
    ALL_EMPTY(true, true, true, true, false, false),

    /**
     * All points with unit(hero or summoned creature) on the battle field.
     */
    ALL_UNIT(true, true, true, false, true, true),

    /**
     * All points with hero on the battle field.
     */
    ALL_HERO(true, true, true, false, true, false),

    /**
     * All points with summoned creature on the battle field.
     */
    ALL_CREATURE(true, true, true, false, false, true),

    /**
     * All points on the enemy-side of the battle field.
     */
    ALL_ENEMY(true, true, false, true, true, true),

    /**
     * All empty points on the enemy-side of the battle field.
     */
    ALL_ENEMY_EMPTY(true, true, false, true, false, false),

    /**
     * All points with unit(hero or summoned creature) on the enemy-side of the battle field.
     */
    ALL_ENEMY_UNIT(true, true, false, false, true, true),

    /**
     * All points with hero on the enemy-side of the battle field.
     */
    ALL_ENEMY_HERO(true, true, false, false, true, false),

    /**
     * ALL points with summoned creature on the enemy-side of the battle field.
     */
    ALL_ENEMY_CREATURE(true, true, false, false, false, true),

    /**
     * All points on the friend-side of the battle field.
     */
    ALL_FRIEND(true, false, true, true, true, true),

    /**
     * All empty points on the friend-side of the battle field.
     */
    ALL_FRIEND_EMPTY(true, false, true, true, false, false),

    /**
     * All points with unit(hero or summoned creature) on the friend-side of the battle field.
     */
    ALL_FRIEND_UNIT(true, false, true, false, true, true),

    /**
     * All points with hero on the friend-side of the battle field.
     */
    ALL_FRIEND_HERO(true, false, true, false, true, false),

    /**
     * All points with summoned creature on the friend-side of the battle field.
     */
    ALL_FRIEND_CREATURE(true, false, true, false, false, true);

    private static final String BASENAME = "target-type";

    private final String code;

    private final int order;

    private final boolean massive;

    private final boolean supportsEnemy;
    private final boolean supportsFriend;

    private final boolean supportsEmpty;
    private final boolean supportsHero;
    private final boolean supportsCreature;

    private TargetType(final boolean massive,
                       final boolean supportsEnemy,
                       final boolean supportsFriend,
                       final boolean supportsEmpty,
                       final boolean supportsHero,
                       final boolean supportsCreature) {
        this.massive = massive;
        this.supportsEnemy = supportsEnemy;
        this.supportsFriend = supportsFriend;
        this.supportsEmpty = supportsEmpty;
        this.supportsHero = supportsHero;
        this.supportsCreature = supportsCreature;

        code = CodeUtils.generateCode(this);

        order = (massive ? 32 : 0) +
                (supportsEnemy ? 16 : 0) +
                (supportsFriend ? 8 : 0) +
                (supportsEmpty ? 4 : 0) +
                (supportsHero ? 2 : 0) +
                (supportsCreature ? 1 : 0);
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }

    public int getOrder() {
        return order;
    }

    public boolean isMassive() {
        return massive;
    }

    public boolean supportsEnemy() {
        return supportsEnemy;
    }

    public boolean supportsFriend() {
        return supportsFriend;
    }

    public boolean supportsEmpty() {
        return supportsEmpty;
    }

    public boolean supportsHero() {
        return supportsHero;
    }

    public boolean supportsCreature() {
        return supportsCreature;
    }
}
