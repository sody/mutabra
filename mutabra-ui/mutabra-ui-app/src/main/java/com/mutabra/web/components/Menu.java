package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Menu extends AbstractComponent implements ClientElement {
	private static final String MENU_CLASS = new StringBuilder()
			.append(CSSConstants.MENU).append(" ")
			.append(CSSConstants.WIDGET).append(" ")
			.append(CSSConstants.WIDGET_CONTENT).append(" ")
			.append(CSSConstants.CORNER_ALL).append(" ")
			.append(CSSConstants.HELPER_CLEAR_FIX).toString();

	private static final String MENU_ITEM_CLASS = new StringBuilder()
			.append(CSSConstants.BUTTON).append(" ")
			.append(CSSConstants.BUTTON_TEXT_ONLY).append(" ")
			.append(CSSConstants.WIDGET).append(" ")
			.append(CSSConstants.STATE_DEFAULT).append(" ")
			.append(CSSConstants.CORNER_ALL).toString();

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
	private JavaScriptSupport support;

	@Inject
	private Block defaultItemBody;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public boolean isSelected() {
		return item.equals(selected);
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
		clientId = support.allocateClientId(getResources());
		final Element menu = writer.element("ul", "id", clientId);
		if (className != null) {
			menu.addClassName(className);
		}
		menu.addClassName(
				CSSConstants.MENU,
				CSSConstants.WIDGET,
				CSSConstants.WIDGET_CONTENT,
				CSSConstants.CORNER_ALL,
				CSSConstants.HELPER_CLEAR_FIX);
	}

	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.end();
		support.addInitializerCall("menu", new JSONObject("id", getClientId()));
	}
}
