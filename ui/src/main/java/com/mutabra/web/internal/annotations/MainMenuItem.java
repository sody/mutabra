/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.annotations;

import com.mutabra.web.pages.admin.Accounts;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.pages.game.hero.SwitchHero;

/**
 * @author Ivan Khalopik
 */
public enum MainMenuItem implements MenuItem {
    HOME(GameHome.class),
    HERO(SwitchHero.class),
    ADMIN(Accounts.class);

    private final Class<?> pageClass;

    private MainMenuItem(final Class<?> pageClass) {
        this.pageClass = pageClass;
    }

    public Class<?> getPageClass() {
        return pageClass;
    }
}
