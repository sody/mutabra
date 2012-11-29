/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.services;

import com.mutabra.domain.Translatable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Translator {

    Map<String, String> translate(Translatable translatable);

    Map<String, String> translate(Translatable translatable, Locale locale);

    void invalidateCache(Translatable translatable);

}