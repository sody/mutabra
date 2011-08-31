package com.mutabra.services;

import com.mutabra.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationQuery
		extends BaseEntityQuery<Translation, TranslationQuery> {

	private String type;
	private List<Locale> locales;
	private List<String> codes;
	private List<String> variants;

	public TranslationQuery(final EntityRepository repository) {
		super(repository, Translation.class);
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

	public TranslationQuery setType(final String type) {
		this.type = type;
		return query();
	}

	public TranslationQuery addLocale(final Locale locale) {
		if (locales == null) {
			locales = CollectionUtils.newList();
		}
		locales.add(locale);
		return query();
	}

	public TranslationQuery addCode(final String code) {
		if (codes == null) {
			codes = CollectionUtils.newList();
		}
		codes.add(code);
		return query();
	}

	public TranslationQuery addVariant(final String variant) {
		if (variants == null) {
			variants = CollectionUtils.newList();
		}
		variants.add(variant);
		return query();
	}
}
