package com.mutabra.services;

import com.mutabra.domain.Translation;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationQuery
		extends BaseEntityQuery<Translation, TranslationQuery>
		implements TranslationFilter {

	private String type;
	private List<Locale> locales;
	private List<String> codes;
	private List<String> variants;

	public TranslationQuery() {
		super(Translation.class);
	}

	public String getType() {
		return type;
	}

	public List<Locale> getLocales() {
		return locales;
	}

	public List<String> getCodes() {
		return codes;
	}

	public List<String> getVariants() {
		return variants;
	}

	TranslationQuery setType(final String type) {
		this.type = type;
		return query();
	}

	TranslationQuery addLocale(final Locale locale) {
		if (locales == null) {
			locales = CollectionUtils.newList();
		}
		locales.add(locale);
		return query();
	}

	TranslationQuery addCode(final String code) {
		if (codes == null) {
			codes = CollectionUtils.newList();
		}
		codes.add(code);
		return query();
	}

	TranslationQuery addVariant(final String variant) {
		if (variants == null) {
			variants = CollectionUtils.newList();
		}
		variants.add(variant);
		return query();
	}
}
