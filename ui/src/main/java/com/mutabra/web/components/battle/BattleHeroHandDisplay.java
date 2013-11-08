/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleHeroHandDisplay extends AbstractComponent implements ClientElement {
    public static final String ID_PREFIX = "h_hero";

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleHero hero;

    @Property
    private BattleCard card;

    public String getClientId() {
        return ID_PREFIX + encode(BattleHero.class, hero);
    }

    public String getContainerClass() {
        return "row actions active";
    }
}
