/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.menu;

import com.mutabra.web.base.components.AbstractMenu;
import com.mutabra.web.internal.annotations.MainMenuItem;

/**
 * @author Ivan Khalopik
 */
public class MainMenu extends AbstractMenu<MainMenuItem> {

    @Override
    public String getMenuCssClass() {
        return "nav navbar-nav";
    }

    @Override
    protected Class<MainMenuItem> getMenuItemClass() {
        return MainMenuItem.class;
    }
}
