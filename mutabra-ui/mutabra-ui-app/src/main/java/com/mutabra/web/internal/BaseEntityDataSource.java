package com.mutabra.web.internal;

import com.google.code.morphia.query.Query;
import com.mutabra.domain.BaseEntity;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityDataSource<E extends BaseEntity> implements GridDataSource {
    private final Query<E> query;
    private final Class<E> rowType;

    private List<E> selection;
    private int indexFrom;

    public BaseEntityDataSource(final Query<E> query, final Class<E> rowType) {
        this.query = query;
        this.rowType = rowType;
    }

    public int getAvailableRows() {
        return (int) query.countAll();
    }

    public E getRowValue(final int index) {
        return selection.get(index - indexFrom);
    }

    public Class<E> getRowType() {
        return rowType;
    }

    public void prepare(final int startIndex, final int endIndex, final List<SortConstraint> sortConstraints) {
        selection = query.offset(startIndex)
                .limit(endIndex - startIndex + 1)
                .asList();
        indexFrom = startIndex;
    }
}
