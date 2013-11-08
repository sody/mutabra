/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleSkipTurnDisplay extends AbstractComponent {

    @Property
    @Parameter
    private BattleHero hero;

    public String getContainerClass() {
        return hero.isReady() ?
                "card disabled" :
                "card";
    }

    public String getCastLink() {
        return getResources().createEventLink("skip", hero).toAbsoluteURI();
    }

    public String getDescriptionSelector() {
        return "#" + BattleSkipTurnDescription.ID;
    }
}
