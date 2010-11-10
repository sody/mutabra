package com.noname.web.services;

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.PropertyConduitSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Khalopik
 */
public class I18nPropertyConduitSource implements PropertyConduitSource {
	private static final String PATTERN = "^(.+):(.+)$";

	private final Translator translator;
	private final TypeCoercer typeCoercer;
	private final ThreadLocale locale;
	private final StringInterner interner;
	private final PropertyConduitSource conduitSource;

	public I18nPropertyConduitSource(Translator translator, TypeCoercer typeCoercer, ThreadLocale locale, StringInterner interner, PropertyConduitSource conduitSource) {
		this.translator = translator; //todo: defence
		this.typeCoercer = typeCoercer; //todo: defence
		this.locale = locale; //todo: defence
		this.interner = interner; //todo: defence
		this.conduitSource = conduitSource; //todo: defence
	}

	public PropertyConduit create(Class rootType, String expression) {
		final Matcher matcher = Pattern.compile(PATTERN).matcher(expression);
		if (matcher.matches()) {
			final String propertyExpression = matcher.group(1);
			final String variant = matcher.group(2);
			final PropertyConduit conduit = conduitSource.create(rootType, propertyExpression);
			final String description = interner.format("I18nConduit[%s %s %s]", rootType.getName(), propertyExpression, variant);
			return new I18nConduit(translator, typeCoercer, locale, conduit, variant, description);
		}
		return conduitSource.create(rootType, expression);
	}

}