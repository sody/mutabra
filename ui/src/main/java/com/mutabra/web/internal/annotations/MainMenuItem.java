/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.annotations;

import com.mutabra.web.pages.admin.Accounts;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.pages.game.hero.SwitchHero;
import com.mutabra.web.services.MenuModelSource;

/**
 * @author Ivan Khalopik
 */
public enum MainMenuItem implements MenuModelSource.Item {
    HOME(GameHome.class),
    HERO(SwitchHero.class),
    ADMIN(Accounts.class, AdminMenuItem.class);

    private final Class<?> pageClass;
    private final Class<? extends MenuModelSource.Item> childMenuClass;

    private MainMenuItem(final Class<?> pageClass) {
        this(pageClass, null);
    }

    private MainMenuItem(final Class<?> pageClass,
                         final Class<? extends MenuModelSource.Item> childMenuClass) {
        this.pageClass = pageClass;
        this.childMenuClass = childMenuClass;
    }

    public Class<?> getPageClass() {
        return pageClass;
    }

    @Override
    public Class<? extends MenuModelSource.Item> getChildMenuClass() {
        return childMenuClass;
    }
}
