package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.LocalizationSetter;
import org.greatage.util.LocaleUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleMenu extends AbstractComponent {
	private static final Map<String, String> COUNTRY_CODES = new HashMap<String, String>();
	static {
		COUNTRY_CODES.put("ru", "RU");
		COUNTRY_CODES.put("en", "US");
	}

	@Inject
	private Locale currentLocale;

	@Inject
	private LocalizationSetter localizationSetter;

	@Inject
	@Symbol(SymbolConstants.SUPPORTED_LOCALES)
	private String[] locales;

	@Property
	private String locale;

	public String[] getLocales() {
		return locales;
	}

	public String getLinkClass() {
		return "flag " + COUNTRY_CODES.get(locale);
	}

	public String getItemClass() {
		return String.valueOf(currentLocale).equals(locale) ? "selected" : "";
	}

	void onChangeLocale(final String newLocale) {
		localizationSetter.setLocaleFromLocaleName(newLocale);
	}
}
