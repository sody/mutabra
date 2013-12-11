/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class NumberDataSource implements GridDataSource {
    private final int count;

    public NumberDataSource(final int count) {
        this.count = count;
    }

    @Override
    public int getAvailableRows() {
        return count;
    }

    @Override
    public Class<Integer> getRowType() {
        return Integer.class;
    }

    @Override
    public Integer getRowValue(final int index) {
        return index;
    }

    @Override
    public void prepare(final int startIndex, final int endIndex, final List<SortConstraint> sortConstraints) {
        // do nothing
    }
}
