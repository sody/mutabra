package com.noname.web.components;

import ga.tapestry.StateConstants;
import ga.tapestry.commonlib.base.components.AbstractComponent;
import ga.tapestry.commonlib.components.Details;
import ga.tapestry.commonlib.components.Panel;
import ga.tapestry.commonlib.components.ViewStack;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class EntityEditor extends AbstractComponent {

	@Parameter(required = true)
	private Object object;

	@Parameter(required = true)
	private String state;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String actions;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String viewActions;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String editActions;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String addActions;

	@Component(
			parameters = {
					"overrides=overrides",
					"state=state"})
	private ViewStack viewStack;

	@Component(
			parameters = {
					"overrides=overrides",
					"actions=prop:actions"},
			publishParameters = "skin")
	private Panel panel;

	@Component(
			parameters = {
					"overrides=overrides",
					"object=inherit:object",
					"skin=inherit:skin",
					"editable=editable"})
	private Details details;

	public boolean isEditable() {
		return StateConstants.ADD_STATE.equals(state) || StateConstants.EDIT_STATE.equals(state);
	}

	public String getState() {
		return state;
	}

	public String getActions() {
		String stateActions = null;
		if (StateConstants.ADD_STATE.equals(state)) {
			stateActions = addActions;
		} else if (StateConstants.VIEW_STATE.equals(state)) {
			stateActions = viewActions;
		} else if (StateConstants.EDIT_STATE.equals(state)) {
			stateActions = editActions;
		}
		return stateActions != null ? stateActions : actions;
	}

	protected boolean beginRender() {
		return state != null;
	}
}
