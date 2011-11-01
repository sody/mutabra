package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
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
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String selected;

	@Property
	@Parameter
	private String item;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Component(inheritInformalParameters = true)
	@MixinClasses(RenderInformals.class)
	private Any list;

	@Inject
	private Block defaultItemBody;

	public String getMenuClass() {
		final StringBuilder builder = new StringBuilder();
		if (className != null) {
			builder.append(className).append(' ');
		}
		builder.append(CSSConstants.MENU).append(' ')
				.append(CSSConstants.WIDGET).append(' ')
				.append(CSSConstants.WIDGET_CONTENT).append(' ')
				.append(CSSConstants.CORNER_ALL).append(' ')
				.append(CSSConstants.HELPER_CLEAR_FIX);
		return builder.toString();
	}

	public String getMenuItemClass() {
		return item + (item.equals(selected) ? " " + CSSConstants.STATE_HIGHLIGHT : "");
	}

	public String getMenuItemTitle() {
		return getMessages().get("button." + item + ".title");
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
