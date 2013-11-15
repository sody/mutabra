package com.mutabra.web.base.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractMenu<T> extends AbstractComponent {

    private final T[] menuItems;
    private T menuItem;
    private T activeMenuItem;

    private String label;
    private Link link;
    private String cssClass;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private MetaDataLocator locator;

    protected AbstractMenu(final T[] menuItems) {
        this.menuItems = menuItems;
    }

    public T[] getMenuItems() {
        return menuItems;
    }

    public T getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(final T menuItem) {
        this.menuItem = menuItem;
        // setup menu item data
        setupMenuItem(menuItem);
    }

    public String getMenuCssClass() {
        return "nav";
    }

    public String getMenuItemCssClass() {
        return cssClass;
    }

    public Link getMenuItemLink() {
        return link;
    }

    public String getMenuItemLabel() {
        return label;
    }

    private void setupMenuItem(final T menuItem) {
        if (activeMenuItem == null && menuItem != null) {
            final ComponentResources pageResources = getResources().getPage().getComponentResources();
            //noinspection unchecked
            activeMenuItem = (T) locator.findMeta("menu.item", pageResources, menuItem.getClass());
        }
        // setup css class
        if (activeMenuItem != null && activeMenuItem.equals(menuItem)) {
            cssClass = "active";
        } else {
            cssClass = null;
        }

        if (menuItem == null) {
            // setup default item
            setupDefault();
        } else {
            // setup item
            setup(menuItem);
        }
    }

    protected abstract void setupDefault();

    protected abstract void setup(final T menuItem);

    protected void create(final String label, final Link link) {
        this.label = label;
        this.link = link;
    }

    protected void create(final String label, final Class<?> pageClass) {
        this.label = label;
        this.link = linkSource.createPageRenderLink(pageClass);
    }
}
