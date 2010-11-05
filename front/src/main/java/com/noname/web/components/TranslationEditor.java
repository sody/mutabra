package com.noname.web.components;

import com.noname.domain.Translation;
import ga.tapestry.commonlib.base.components.AbstractComponent;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class TranslationEditor extends AbstractComponent {

	@Parameter(required = true)
	private Map<String, Translation> translations;

	@Parameter
	private boolean editable;

	@Parameter
	private List<String> variants;

	private Translation translation;

	private List<String> propertyNames;

	public List<String> getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyName(String propertyName) {
		translation = translations.get(propertyName);
	}

	public Translation getTranslation() {
		return translation;
	}

	public boolean isEditable() {
		return editable;
	}

	protected void setupRender() {
		storeAndExecute(this, new SetupTranslations());
	}

	protected void setupTranslations() {
		propertyNames = CollectionFactory.newList();
		if (variants != null) {
			propertyNames.addAll(variants);
		} else {
			propertyNames.addAll(translations.keySet());
		}
	}

	static class SetupTranslations implements ComponentAction<TranslationEditor> {
		public void execute(TranslationEditor component) {
			component.setupTranslations();
		}
	}

}