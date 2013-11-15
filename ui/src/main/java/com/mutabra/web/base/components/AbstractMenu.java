package com.mutabra.web.base.components;

import com.mutabra.web.internal.annotations.MenuItem;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractMenu<T extends Enum<T> & MenuItem> extends AbstractComponent {
    public static final String MENU_ITEM_META_KEY = "menu.item";

    private T menuItem;
    private T activeMenuItem;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private MetaDataLocator locator;

    public T[] getMenuItems() {
        return getMenuItemClass().getEnumConstants();
    }

    public T getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(final T menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuCssClass() {
        return "nav";
    }

    public String getMenuItemCssClass() {
        return activeMenuItem == menuItem ? "active" : null;
    }

    public Link getMenuItemLink() {
        return linkSource.createPageRenderLink(menuItem.getPageClass());
    }

    public String getMenuItemLabel() {
        return message(menuItem);
    }

    protected abstract Class<T> getMenuItemClass();

    @SetupRender
    void setupActiveMenuItem() {
        final ComponentResources pageResources = getResources().getPage().getComponentResources();
        activeMenuItem = locator.findMeta(MENU_ITEM_META_KEY, pageResources, getMenuItemClass());
    }
}
