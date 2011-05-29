package com.mutabra.web.components.common;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.greatage.tapestry.internal.SelectModelBuilder;
import org.greatage.util.LocaleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//todo: change this by introducing global select model

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleSelect {

	@Parameter(required = true)
	private Locale locale;

	@Inject
	@Symbol(SymbolConstants.SUPPORTED_LOCALES)
	private String supportedLocales;

	@Inject
	private SelectModelBuilder selectModelBuilder;

	private SelectModel localeModel;

	public SelectModel getLocaleModel() {
		if (localeModel == null) {
			final List<Locale> locales = getLocales();
			localeModel = selectModelBuilder.buildFormatted(Locale.class, locales, "%s", "displayName");
		}
		return localeModel;
	}

	private List<Locale> getLocales() {
		final List<Locale> locales = new ArrayList<Locale>();
		for (String localeName : supportedLocales.split(",")) {
			locales.add(LocaleUtils.parseLocale(localeName));
		}
		return locales;
	}
}
