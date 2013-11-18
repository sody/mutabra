package com.mutabra.web.components.layout;

import com.mutabra.web.internal.annotations.AuthMenuItem;
import com.mutabra.web.internal.model.MenuModel;
import com.mutabra.web.services.MenuModelSource;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
public class AuthLayout extends EmptyLayout {

    @Inject
    private MenuModelSource menuModelSource;

    public MenuModel getAuthMenuModel() {
        return menuModelSource.buildForEnum(getMessages(), AuthMenuItem.class);
    }
}
