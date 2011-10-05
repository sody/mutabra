package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Dialog extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Parameter
	private boolean autoOpen;

	@Parameter
	private boolean autoDestroy;

	@Parameter
	private boolean modal;

	@Parameter
	private boolean resizable;

	@Parameter
	private Integer width;

	@Parameter
	private Integer height;

	@Inject
	private JavaScriptSupport support;

	public String getClientId() {
		return clientId;
	}

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		writer.element("div", "id", getClientId());
	}

	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.end();

		final JSONObject options = new JSONObject()
				.put("title", title)
				.put("autoOpen", autoOpen)
				.put("autoDestroy", autoDestroy)
				.put("modal", modal)
				.put("resizable", resizable);

		if (width != null) {
			options.put("width", width);
		}
		if (height != null) {
			options.put("height", height);
		}

		support.addInitializerCall("dialog", new JSONObject("id", getClientId()).put("options", options));
	}
}