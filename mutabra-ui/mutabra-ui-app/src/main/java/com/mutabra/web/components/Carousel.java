package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Hidden;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Carousel<T> extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Property
	@Parameter
	private T value;

	@Property
	@Parameter(required = true, allowNull = false)
	private Iterable<T> source;

	@Property
	@Parameter
	private ValueEncoder<T> encoder;

	@Property
	@Parameter
	private T row;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String callback;

	@Inject
	private JavaScriptSupport support;

	@Inject
	private ComponentDefaultProvider defaultProvider;

	@Environmental(false)
	private FormSupport formSupport;

	@InjectComponent
	private Hidden hiddenValue;

	private String assignedClientId;

	public String getClientId() {
		return assignedClientId;
	}

	public boolean isInsideForm() {
		return formSupport != null;
	}

	ValueEncoder defaultEncoder() {
		return defaultProvider.defaultValueEncoder("value", getResources());
	}

	@SetupRender
	void setupClientId() {
		assignedClientId = support.allocateClientId(clientId);
	}

	@AfterRender
	void renderScript() {
		final JSONObject spec = new JSONObject("id", getClientId(), "selected", encoder.toClient(value));
		if (isInsideForm()) {
			spec.put("hiddenId", hiddenValue.getClientId());
			final JSONArray values = new JSONArray();
			for (T element : source) {
				values.put(encoder.toClient(element));
			}
			spec.put("values", values);
		}
		if (callback != null) {
			spec.put("callback", new JSONLiteral(callback));
		}
		support.addInitializerCall("carousel", spec);
	}
}
