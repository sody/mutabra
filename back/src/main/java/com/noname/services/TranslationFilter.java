package com.noname.services;

import com.noname.domain.Translation;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public interface TranslationFilter extends BaseEntityFilter<Translation> {

	String getType();

	Locale getLocale();

	List<String> getCodes();

	List<String> getVariants();

}