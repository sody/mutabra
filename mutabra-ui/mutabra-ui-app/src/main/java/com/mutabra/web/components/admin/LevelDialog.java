package com.mutabra.web.components.admin;

import com.mutabra.domain.Translation;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.security.Account;
import com.mutabra.web.base.components.EntityDialog;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;

import java.util.List;

import static org.apache.tapestry5.EventConstants.PREPARE_FOR_SUBMIT;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelDialog extends EntityDialog<Level> {

	@InjectComponent
	private TranslationEditor translationEditor;

	public List<Translation> getTranslations() {
		return translationEditor.getTranslations();
	}

	@Override
	public String getTitle() {
		return getMessages().get("edit.title");
	}

	@OnEvent(PREPARE_FOR_SUBMIT)
	void prepare(final Level entity) {
		init(entity);
	}
}