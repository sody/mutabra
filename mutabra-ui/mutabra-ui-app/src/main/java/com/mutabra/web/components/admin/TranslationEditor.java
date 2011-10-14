package com.mutabra.web.components.admin;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import com.mutabra.services.TranslationService;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
		return getMessages().get(variant + ".label");
	}

	public Translation getTranslation() {
		if (translations == null) {
			translations = translationService.getTranslations(value, getLocale());
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
