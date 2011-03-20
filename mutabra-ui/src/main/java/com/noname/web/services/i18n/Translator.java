/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.services.i18n;

import com.mutabra.domain.Translatable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface Translator {

	Map<String, String> translate(Translatable translatable);

	Map<String, String> translate(Translatable translatable, Locale locale);

	void updateCache(Translatable translatable, Locale locale, Map<String, String> messages);

}