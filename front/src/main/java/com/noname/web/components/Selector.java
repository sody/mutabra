package com.noname.web.components;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(library = "selector.js")
public class Selector extends AbstractComponent {

	@Parameter
	private Iterable<?> source;

	@Parameter
	private Object value;

	@Property
	@Parameter
	private Object row;

	@Parameter(required = true)
	private ValueEncoder encoder;

	@Inject
	private ComponentDefaultProvider defaultProvider;

	private Map<Object, String> itemIds;
	private Map<Object, String> itemValues;

	private String clientId;
	private String previousId;
	private String nextId;

	ValueEncoder defaultEncoder() {
		return defaultProvider.defaultValueEncoder("value", getResources());
	}

	public String getClientId() {
		return clientId;
	}

	public String getPreviousId() {
		return previousId;
	}

	public String getNextId() {
		return nextId;
	}

	public String getRowId() {
		return itemIds.get(row);
	}

	public String getRowClass() {
		return "item " + (row.equals(value) ? "" : "invisible");
	}

	void setupRender() {
		itemIds = new HashMap<Object, String>();
		itemValues = new HashMap<Object, String>();
		for (Object o : source) {
			final String itemId = allocateClientId("item");
			itemIds.put(o, itemId);
			itemValues.put(o, encoder.toClient(o));
		}
		if (value == null) {
			value = source.iterator().next();
		}

		clientId = allocateClientId();
		previousId = allocateClientId("previous");
		nextId = allocateClientId("next");
	}

	void afterRender() {
		final JSONObject spec = new JSONObject("previousId", previousId, "nextId", nextId, "hiddenId", clientId);
		spec.put("selectedId", itemIds.get(value));
		spec.put("items", generateItems());
		getJavaScriptSupport().addInitializerCall("selector", spec);
	}

	private JSONObject generateItems() {
		final JSONObject result = new JSONObject();
		for (Object item : source) {
			result.put(itemIds.get(item), itemValues.get(item));
		}
		return result;
	}
}
