/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleCreatureDescription extends AbstractComponent implements ClientElement {
    public static final String ID_PREFIX = "d_creature";

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleCreature creature;

    public String getClientId() {
        return ID_PREFIX + encode(BattleCreature.class, creature);
    }

    public String getName() {
        return translate(creature, BattleCreature.NAME);
    }

    public String getDescription() {
        return translate(creature, BattleCreature.DESCRIPTION);
    }
}
