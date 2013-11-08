/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.game;

import com.mutabra.domain.battle.Battle;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.game.GameBattle;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UpdateChecker extends AbstractComponent {

    @Inject
    private AccountContext accountContext;

    @InjectComponent
    private Zone updater;

    public Battle getBattle() {
        return accountContext.getBattle();
    }

    @OnEvent(EventConstants.REFRESH)
    Object check(final int round) {
        final Battle battle = getBattle();
        if (battle == null || !battle.isActive()) {
            // the battle is over
            return GameHome.class;
        }
        if (battle.getRound() != round) {
            // new round begins or battle was applied
            return GameBattle.class;
        }
        return updater;
    }
}
