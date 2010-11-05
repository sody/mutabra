package com.noname.web.components;

import ga.domain.GenericEntity;
import ga.tapestry.commonlib.base.components.AbstractComponent;
import ga.tapestry.commonlib.components.Grid;
import ga.tapestry.commonlib.components.GridTable;
import ga.tapestry.commonlib.components.Menu;
import ga.tapestry.commonlib.components.Panel;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class EntityGrid extends AbstractComponent {

	@Parameter
	private GenericEntity row;

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

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public GenericEntity getRow() {
		return row;
	}

	public void setRow(GenericEntity row) {
		this.row = row;
	}

	public boolean isShowIdColumn() {
		return showIdColumn;
	}
}
