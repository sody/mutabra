package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(library = "dialog.js")
public class DialogLink extends AbstractComponent implements ClientElement {

	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String dialog;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	public String getClientId() {
		return clientId;
	}

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		writer.element("a", "href", "#");
		writer.getElement().forceAttributes("id", getClientId());
	}

	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.end();

		final JSONObject params = new JSONObject();
		params.put("id", getClientId());
		params.put("dialogId", dialog);

		javaScriptSupport.addInitializerCall("dialoglink", params);
	}
}