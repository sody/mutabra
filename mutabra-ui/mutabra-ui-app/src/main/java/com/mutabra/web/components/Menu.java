package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
import com.mutabra.web.internal.MessageUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.MixinClasses;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.mixins.RenderInformals;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Menu extends AbstractComponent {

	@Property
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String[] items;

	@Property
	@Parameter
	private String item;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String menuClass;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, cache = false)
	private String itemClass;

	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Component(inheritInformalParameters = true)
	@MixinClasses(RenderInformals.class)
	private Any list;

	@Inject
	private Block defaultItemBody;

	public String getMenuClass() {
		return menuClass;
	}

	public String getMenuItemClass() {
		return itemClass;
	}

	public String getMenuItemTitle() {
		return getMessages().get(MessageUtils.label(item));
	}

	public Block getMenuItemBody() {
		final Block block = overrides.getOverrideBlock(item + "MenuItem");
		if (block != null) {
			return block;
		}
		if (getResources().hasBody()) {
			return getResources().getBody();
		}
		return defaultItemBody;
	}
}
