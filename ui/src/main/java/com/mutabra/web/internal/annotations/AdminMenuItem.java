/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.annotations;

import com.mutabra.web.pages.admin.Accounts;
import com.mutabra.web.pages.admin.Cards;
import com.mutabra.web.pages.admin.Levels;
import com.mutabra.web.pages.admin.Races;
import com.mutabra.web.services.MenuModelSource;

/**
 * @author Ivan Khalopik
 */
public enum AdminMenuItem implements MenuModelSource.Item {
    ACCOUNTS(Accounts.class),
    LEVELS(Levels.class),
    RACES(Races.class),
    CARDS(Cards.class);

    private final Class<?> pageClass;

    private AdminMenuItem(final Class<?> pageClass) {
        this.pageClass = pageClass;
    }

    public Class<?> getPageClass() {
        return pageClass;
    }

    @Override
    public Class<? extends MenuModelSource.Item> getChildMenuClass() {
        return null;
    }
}
