package com.mutabra.web.components.menu;

import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.LocalizationSetter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleMenu {
    private static final Map<String, String> COUNTRY_CODES = new HashMap<String, String>();

    static {
        COUNTRY_CODES.put("ru", "ru");
        COUNTRY_CODES.put("en", "us");
    }

    @Inject
    private Locale currentLocale;

    @Inject
    private LocalizationSetter localizationSetter;

    @Property(write = false)
    @Inject
    @Symbol(SymbolConstants.SUPPORTED_LOCALES)
    private String[] locales;

    @Property
    private String locale;

    public String getFlagIconClass() {
        return "flag-" + COUNTRY_CODES.get(locale);
    }

    public String getMenuItemClass() {
        return currentLocale.toString().equals(locale) ?
                CSSConstants.ACTIVE :
                null;
    }

    @OnEvent("changeLocale")
    void changeLocale(final String newLocale) {
        localizationSetter.setLocaleFromLocaleName(newLocale);
    }
}
