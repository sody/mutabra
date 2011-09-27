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
public class DialogLink extends AbstractComponent implements ClientElement {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String dialog;

	@Parameter(allowNull = false, value = "open", defaultPrefix = BindingConstants.LITERAL)
	private String action;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		clientId = javaScriptSupport.allocateClientId(getResources());

		writer.element("a", "href", "#");
		writer.getElement().forceAttributes("id", getClientId());
	}

	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.end();

		final JSONObject options = new JSONObject();
		options.put("id", getClientId());
		options.put("dialogId", dialog);
		options.put("action", action);

		javaScriptSupport.addInitializerCall("dialoglink", options);
	}
}