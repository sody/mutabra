package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstantsEx;
import com.mutabra.web.internal.MessageUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.MixinClasses;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.mixins.RenderInformals;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Tabs extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Property
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String[] tabs;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String active;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Property
	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Property
	private String tab;

	@Component(inheritInformalParameters = true)
	@MixinClasses(RenderInformals.class)
	private Any container;

	public String getClientId() {
		return clientId;
	}

	public boolean isTabsBelow() {
		return className != null && className.contains(CSSConstantsEx.TABS_BELOW);
	}

	public String getContainerClass() {
		return new StringBuilder(className != null ? className : "")
				.append(' ').append(CSSConstantsEx.TABBABLE)
				.toString();
	}

	public String getMenuClass() {
		return new StringBuilder(CSSConstantsEx.NAV)
				.append(' ').append(CSSConstantsEx.NAV_TABS)
				.toString();
	}

	public String getMenuItemClass() {
		return tab.equals(active) ? CSSConstantsEx.ACTIVE : "";
	}

	public String getTabClass() {
		return new StringBuilder(tab.equals(active) ? CSSConstantsEx.ACTIVE : "")
				.append(' ').append(CSSConstantsEx.TAB_PANE)
				.toString();
	}

	public String getTabTitle() {
		return getMessages().get(MessageUtils.label(tab));
	}

	public Block getTabBody() {
		final Block block = overrides.getOverrideBlock(tab + "Tab");
		if (block != null) {
			return block;
		}
		if (getResources().hasBody()) {
			return getResources().getBody();
		}
		return null;
	}
}
