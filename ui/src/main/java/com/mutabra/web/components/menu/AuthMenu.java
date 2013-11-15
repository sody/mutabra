package com.mutabra.web.components.menu;

import com.mutabra.web.base.components.AbstractMenu;
import com.mutabra.web.internal.annotations.AuthMenuItemName;

import static com.mutabra.web.internal.annotations.AuthMenuItemName.SIGN_IN;

/**
 * @author Ivan Khalopik
 */
public class AuthMenu extends AbstractMenu<AuthMenuItemName> {

    public AuthMenu() {
        super(AuthMenuItemName.values());
    }

    @Override
    public String getMenuCssClass() {
        return "nav nav-pills";
    }

    @Override
    protected void setupDefault() {
        setup(SIGN_IN);
    }

    @Override
    protected void setup(final AuthMenuItemName menuItem) {
        create(translate(menuItem), menuItem.getPageClass());
    }
}
