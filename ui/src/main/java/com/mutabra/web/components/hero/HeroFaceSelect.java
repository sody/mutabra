/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractClientElement;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroFaceSelect extends AbstractClientElement {

    @Parameter(required = true, allowNull = false)
    private GridDataSource source;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String target;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private HeroAppearancePart part;

    private int pageSize = 6;
    private int currentPage = 1;

    @Property
    private Object item;

    @Property
    private int page;

    @InjectComponent
    private Zone menu;

    @Cached
    public int getCount() {
        return source.getAvailableRows();
    }

    @Cached
    public List<Object> getItems() {
        final List<Object> items = new ArrayList<>(pageSize);

        final int startIndex = (currentPage - 1) * pageSize;
        final int endIndex = Math.min(startIndex + pageSize, getCount());

        source.prepare(startIndex, endIndex - 1, Collections.<SortConstraint>emptyList());
        for (int i = startIndex; i < endIndex; i++) {
            items.add(source.getRowValue(i));
        }

        return items;
    }

    public String getItemValue() {
        return encode(source.getRowType(), item);
    }

    @Cached
    public List<Object> getPlaceholders() {
        final List<Object> items = new ArrayList<>(pageSize);

        final int startIndex = (currentPage - 1) * pageSize;
        final int endIndex = Math.min(startIndex + pageSize, getCount());
        for (int i = endIndex; i < startIndex + pageSize; i++) {
            items.add(i);
        }

        return items;
    }

    public List<Integer> getPages() {
        final List<Integer> pages = new ArrayList<>();

        final int pageCount = (part.getCount() - 1) / pageSize + 1;
        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }

        return pages;
    }

    public String getPageCssClass() {
        return currentPage == page ? "active" : null;
    }

    @OnEvent(component = "pageLink")
    Object changePage(final int page) {
        currentPage = page;

        return menu.getBody();
    }
}
