package com.mutabra.services;

import com.mutabra.domain.Translation;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TranslationFilter extends BaseEntityFilter<Translation> {

	String getType();

	List<Locale> getLocales();

	List<String> getCodes();

	List<String> getVariants();

}