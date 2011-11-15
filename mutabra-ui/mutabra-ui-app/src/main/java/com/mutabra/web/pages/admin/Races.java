package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Race;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.TranslationService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.RaceDialog;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.internal.BaseEntityDataSource;
import com.mutabra.web.services.Translator;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.annotations.Allow;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_ADMIN)
public class Races extends AbstractPage {

	@InjectService("raceService")
	private CodedEntityService<Race> raceService;

	@Inject
	private TranslationService translationService;

	@Inject
	private Translator translator;

	@InjectComponent
	private RaceDialog entityDialog;

	@Property
	private Race row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Race>(raceService.query(), Race.class);
	}

	Object onEdit(final Race race) {
		return entityDialog.show(race);
	}

	Object onSuccess() {
		raceService.saveOrUpdate(entityDialog.getValue());
		translationService.saveTranslations(entityDialog.getTranslations());
		//todo: should be automatic
		translator.invalidateCache(entityDialog.getValue());
		return this;
	}
}
