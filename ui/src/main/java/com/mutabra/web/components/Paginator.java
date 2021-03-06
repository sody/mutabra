/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractClientElement;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
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
@SupportsInformalParameters
public class Paginator extends AbstractClientElement {

    @Parameter(required = true, allowNull = false)
    private GridDataSource source;

    @Property
    @Parameter
    private Object value;

    @Property(write = false)
    @Parameter
    private Block placeholder;

    @Property(write = false)
    @Parameter
    private boolean offline;

    @Parameter(name = "class")
    private String cssClass;

    private int pageSize = 6;
    private int currentPage = 1;

    @Property
    private int page;

    @InjectComponent
    private Zone pageZone;

    public List<Object> getSource() {
        final List<Object> items = new ArrayList<>(pageSize);

        final int renderedPage = offline ? page : currentPage;
        final int startIndex = (renderedPage - 1) * pageSize;
        final int endIndex = Math.min(startIndex + pageSize, getCount());

        source.prepare(startIndex, endIndex - 1, Collections.<SortConstraint>emptyList());
        for (int i = startIndex; i < endIndex; i++) {
            items.add(source.getRowValue(i));
        }

        return items;
    }

    public List<Integer> getPlaceholders() {
        final List<Integer> items = new ArrayList<>(pageSize);

        final int renderedPage = offline ? page : currentPage;
        final int startIndex = (renderedPage - 1) * pageSize;
        final int endIndex = Math.min(startIndex + pageSize, getCount());
        for (int i = endIndex; i < startIndex + pageSize; i++) {
            items.add(i);
        }

        return items;
    }

    @Cached(watch = "clientId")
    public List<Integer> getPages() {
        final List<Integer> pages = new ArrayList<>();

        final int pageCount = (getCount() - 1) / pageSize + 1;
        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }

        return pages;
    }

    @Cached(watch = "clientId")
    public int getCount() {
        return source.getAvailableRows();
    }

    public String getPageContentId() {
        return getClientId() + "-" + page;
    }

    public String getContainerCssClass() {
        return cssClass != null ? "tab-content " + cssClass : "tab-content";
    }

    public String getPageContentCssClass() {
        return page == currentPage ? "tab-pane active" : "tab-pane";
    }

    public String getPageMenuCssClass() {
        return getPages().size() <= 1 ? "pagination invisible" : "pagination";
    }

    public String getPageCssClass() {
        return currentPage == page ? "active" : null;
    }

    @OnEvent(component = "changePage")
    Object changePage(final int page) {
        currentPage = page;

        return pageZone.getBody();
    }
}
