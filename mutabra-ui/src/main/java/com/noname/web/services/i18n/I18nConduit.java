package com.noname.web.services.i18n;

import com.mutabra.domain.Translatable;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.internal.services.BasePropertyConduit;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.ioc.services.TypeCoercer;

import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class I18nConduit extends BasePropertyConduit {
	private final Translator translator;
	private final ThreadLocale locale;
	private final PropertyConduit conduit;
	private final String variant;

	public I18nConduit(Translator translator, TypeCoercer typeCoercer, ThreadLocale locale, PropertyConduit conduit, String variant, String description) {
		super(conduit.getPropertyType(), null, conduit, description, typeCoercer);
		this.translator = translator;
		this.locale = locale;
		this.conduit = conduit;
		this.variant = variant;
	}

	public Object get(Object target) {
		final Object instance = conduit.get(target);
		if (instance == null) {
			return "";
		}
		if (instance instanceof Translatable) {
			final Translatable entity = (Translatable) instance;
			final Map<String, String> properties = translator.translate(entity, locale.getLocale());
			return properties.get(variant);
		}
		throw new IllegalStateException("Only instances of Translatable can be translated");
	}

	public void set(Object instance, Object value) {
		throw new IllegalStateException("I18n conduit is not updatable");
	}

}