package com.mutabra.web.internal;

import com.mutabra.domain.BaseEntity;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.greatage.domain.EntityQuery;
import org.greatage.domain.PaginationBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityDataSource<E extends BaseEntity> implements GridDataSource {
	private final EntityQuery<Long, E> query;
	private final Class<E> rowType;

	private List<E> selection;
	private int indexFrom;

	public BaseEntityDataSource(final EntityQuery<Long, E> query, final Class<E> rowType) {
		this.query = query;
		this.rowType = rowType;
	}

	public int getAvailableRows() {
		return (int) query.count();
	}

	public E getRowValue(final int index) {
		return selection.get(index - indexFrom);
	}

	public Class<E> getRowType() {
		return rowType;
	}

	public void prepare(final int startIndex, final int endIndex, final List<SortConstraint> sortConstraints) {
		final PaginationBuilder builder = PaginationBuilder.create().start(startIndex).end(endIndex + 1);
//		for (SortConstraint constraint : sortConstraints) {
//			final String property = constraint.getPropertyModel().getPropertyName();
//			final boolean ascending = constraint.getColumnSort() == ColumnSort.ASCENDING;
//			builder.sort(property, ascending);
//		}
		selection = query.list(builder);
		indexFrom = startIndex;
	}
}
