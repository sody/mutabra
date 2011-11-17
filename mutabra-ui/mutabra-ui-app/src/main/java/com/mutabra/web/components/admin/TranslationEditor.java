package com.mutabra.web.components.admin;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import com.mutabra.services.TranslationService;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationEditor extends AbstractComponent {

	@Parameter(required = true, allowNull = false)
	private Translatable value;

	@Inject
	private TranslationService translationService;

	@Property
	private String variant;

	private Map<String, Translation> translations;

	public Collection<String> getVariants() {
		return value.getTranslationVariants();
	}

	public String getLabel() {
		return getMessages().get("label." + variant);
	}

	public Translation getTranslation() {
		if (translations == null) {
			final List<Translation> existingTranslations = translationService.getTranslations(value, getLocale());
			final Map<String, Translation> mapped = new HashMap<String, Translation>();
			for (Translation translation : existingTranslations) {
				mapped.put(translation.getVariant(), translation);
			}

			translations = new HashMap<String, Translation>();
			for (String translationVariant : value.getTranslationVariants()) {
				if (mapped.containsKey(translationVariant)) {
					translations.put(translationVariant, mapped.get(translationVariant));
				} else {
					final Translation translation = translationService.create();
					translation.setLocale(getLocale());
					translation.setVariant(translationVariant);
					translations.put(translationVariant, translation);
				}
			}
		}
		return translations.get(variant);
	}

	public List<Translation> getTranslations() {
		final ArrayList<Translation> result = new ArrayList<Translation>();
		for (Translation translation : translations.values()) {
			translation.setType(value.getTranslationType());
			translation.setCode(value.getTranslationCode());
			result.add(translation);
		}
		return result;
	}
}
