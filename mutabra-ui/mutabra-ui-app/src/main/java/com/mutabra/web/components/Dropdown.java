package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstantsEx;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.MixinClasses;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.mixins.RenderInformals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Dropdown extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Property
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String label;

	@Property
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String[] items;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Property
	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Component(inheritInformalParameters = true)
	@MixinClasses(RenderInformals.class)
	private Any container;

	public String getClientId() {
		return clientId;
	}

	public String getContainerClass() {
		return className != null ?
				CSSConstantsEx.BUTTON_GROUP + " " + CSSConstantsEx.DROPDOWN + " " + className :
				CSSConstantsEx.BUTTON_GROUP + " " + CSSConstantsEx.DROPDOWN;
	}

	public String getToggleClass() {
		return CSSConstantsEx.BUTTON + " " + CSSConstantsEx.DROPDOWN_TOGGLE;
	}

	public String getCaretClass() {
		return CSSConstantsEx.CARET;
	}

	public String getMenuClass() {
		return CSSConstantsEx.DROPDOWN_MENU;
	}
}
