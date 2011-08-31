/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mutabra.web.internal;

import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.greatage.domain.Entity;
import org.greatage.domain.EntityQuery;
import org.greatage.domain.EntityService;
import org.greatage.domain.PaginationBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityDataSource<PK extends Serializable, E extends Entity<PK>> implements GridDataSource {
	private final EntityQuery<PK, E, ?> query;
	private final Class<E> rowType;

	private List<E> selection;
	private int indexFrom;

	public EntityDataSource(final EntityQuery<PK, E, ?> query, final Class<E> rowType) {
		this.query = query;
		this.rowType = rowType;
	}

	public int getAvailableRows() {
		return query.count();
	}

	public E getRowValue(final int index) {
		return selection.get(index - indexFrom);
	}

	public Class<E> getRowType() {
		return rowType;
	}

	public void prepare(final int startIndex, final int endIndex, final List<SortConstraint> sortConstraints) {
		final PaginationBuilder builder = PaginationBuilder.create().start(startIndex).end(endIndex + 1);
		for (SortConstraint constraint : sortConstraints) {
			final String property = constraint.getPropertyModel().getPropertyName();
			final boolean ascending = constraint.getColumnSort() == ColumnSort.ASCENDING;
			builder.sort(property, ascending);
		}
		selection = query.list(builder);
		indexFrom = startIndex;
	}
}
