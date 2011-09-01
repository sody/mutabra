package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
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
	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Property
	private String item;

	@Inject
	private Block defaultItemBody;

	public Block getItemBody() {
		final Block block = overrides.getOverrideBlock(item + "MenuItem");
		if (block != null) {
			return block;
		}
		return defaultItemBody;
	}

	public String getItemClass() {
		return item;
	}

	public String getItemTitle() {
		return getMessages().get("button." + item + ".title");
	}
}
