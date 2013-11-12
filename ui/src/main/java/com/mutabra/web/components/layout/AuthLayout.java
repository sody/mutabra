package com.mutabra.web.components.layout;

import com.mutabra.web.internal.annotations.AuthMenuItemName;
import com.mutabra.web.pages.auth.RestoreAuth;
import com.mutabra.web.pages.auth.SignInAuth;
import com.mutabra.web.pages.auth.SignUpAuth;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author Ivan Khalopik
 */
public class AuthLayout extends EmptyLayout {

    @Property
    private AuthMenuItemName menuItem;

    private AuthMenuItemName activeMenuItem;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private MetaDataLocator locator;

    public AuthMenuItemName[] getMenuItems() {
        return AuthMenuItemName.values();
    }

    public String getMenuItemCssClass() {
        return menuItem == activeMenuItem ? "active" : null;
    }

    public Link getMenuItemLink() {
        switch (menuItem) {
            case SIGN_UP:
                return linkSource.createPageRenderLink(SignUpAuth.class);
            case RESTORE:
                return linkSource.createPageRenderLink(RestoreAuth.class);
            case SIGN_IN:
            default:
                return linkSource.createPageRenderLink(SignInAuth.class);
        }
    }

    public String getMenuItemLabel() {
        return label(menuItem);
    }

    @SetupRender
    void setupActiveMenuItem() {
        final ComponentResources pageResources = getResources().getPage().getComponentResources();
        activeMenuItem = locator.findMeta("menu.item", pageResources, AuthMenuItemName.class);
    }
}
