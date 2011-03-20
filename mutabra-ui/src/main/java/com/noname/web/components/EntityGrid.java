package com.noname.web.components;

import com.mutabra.domain.BaseEntity;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;
import org.greatage.tapestry.commonlib.components.Grid;
import org.greatage.tapestry.commonlib.components.GridTable;
import org.greatage.tapestry.commonlib.components.Menu;
import org.greatage.tapestry.commonlib.components.Panel;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class EntityGrid extends AbstractComponent {

	@Parameter
	private BaseEntity row;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Object rowActions;

	@Parameter
	private Block filter;

	@Parameter
	private boolean showIdColumn;

	@Persist
	private String searchString;

	@Component(
			parameters = "overrides=overrides",
			publishParameters = "actions")
	private Panel panel;

	@Component(
			parameters = {
					"overrides=overrides",
					"async=true"},
			publishParameters = "source")
	private Grid grid;

	@Component(
			parameters = {
					"overrides=overrides",
					"row=prop:row"})
	private GridTable table;

	@Component(
			parameters = {
					"overrides=overrides",
					"skin=literal:grid-menu",
					"actions=inherit:rowActions",
					"context=prop:row"})
	private Menu actionsMenu;

	@Inject
	private Block defaultFilter;

	public Block getFilter() {
		return filter != null ? filter : defaultFilter;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(final String searchString) {
		this.searchString = searchString;
	}

	public BaseEntity getRow() {
		return row;
	}

	public void setRow(final BaseEntity row) {
		this.row = row;
	}

	public boolean isShowIdColumn() {
		return showIdColumn;
	}
}
