package com.mutabra.web.internal;

import com.mutabra.domain.Translatable;
import com.mutabra.web.services.Translator;
import org.apache.tapestry5.PropertyConduit;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class I18nConduit implements PropertyConduit {
	private final Translator translator;
	private final PropertyConduit conduit;
	private final String variant;
	private final String description;

	public I18nConduit(final PropertyConduit conduit, final Translator translator, final String variant,
					   final String description) {
		this.conduit = conduit;
		this.translator = translator;
		this.variant = variant;
		this.description = description;
	}

	public Object get(final Object target) {
		final Object instance = conduit.get(target);
		if (instance == null) {
			return "";
		}
		if (instance instanceof Translatable) {
			final Translatable entity = (Translatable) instance;
			final Map<String, String> properties = translator.translate(entity);
			return properties.get(variant);
		}
		throw new IllegalStateException("Only instances of Translatable can be translated");
	}

	public void set(final Object instance, final Object value) {
		throw new IllegalStateException("I18n conduit is read only");
	}

	public Class getPropertyType() {
		return String.class;
	}

	public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
		return conduit.getAnnotation(clazz);
	}

	@Override
	public String toString() {
		return description;
	}
}