package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.dom.Element;
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

	@Inject
	private Block defaultItemBody;

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

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		final Element menu = writer.element("ul");
		if (className != null) {
			menu.addClassName(className);
		}
		menu.addClassName(
				CSSConstants.MENU,
				CSSConstants.WIDGET,
				CSSConstants.WIDGET_CONTENT,
				CSSConstants.CORNER_ALL,
				CSSConstants.HELPER_CLEAR_FIX);

		getResources().renderInformalParameters(writer);
	}

	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.end();
	}
}
