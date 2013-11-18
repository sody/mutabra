package com.mutabra.web.internal.model;

import org.apache.tapestry5.Link;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public interface MenuModel {
    String ACTIVE_KEY = "menu.active";

    List<Item> getItems();

    interface Item {

        String getId();

        String getLabel();

        Link getLink();

        List<Item> getChildren();
    }
}
