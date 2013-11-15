package com.mutabra.web.components.menu;

import com.mutabra.web.base.components.AbstractMenu;
import com.mutabra.web.internal.annotations.AuthMenuItem;

/**
 * @author Ivan Khalopik
 */
public class AuthMenu extends AbstractMenu<AuthMenuItem> {

    @Override
    public String getMenuCssClass() {
        return "nav nav-pills";
    }

    @Override
    protected Class<AuthMenuItem> getMenuItemClass() {
        return AuthMenuItem.class;
    }
}
