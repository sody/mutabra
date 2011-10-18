package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Face;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.TranslationService;
import com.mutabra.services.common.FaceQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.FaceDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import com.mutabra.web.services.AuthorityConstants;
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
@Allow(AuthorityConstants.ROLE_ADMIN)
public class Faces extends AbstractPage {

	@InjectService("faceService")
	private BaseEntityService<Face, FaceQuery> faceService;

	@Inject
	private TranslationService translationService;

	@Inject
	private Translator translator;

	@InjectComponent
	private FaceDialog entityDialog;

	@Property
	private Face row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Face>(faceService.query(), Face.class);
	}

	Object onEdit(final Face face) {
		return entityDialog.show(face);
	}

	Object onSuccess() {
		faceService.saveOrUpdate(entityDialog.getValue());
		translationService.saveTranslations(entityDialog.getTranslations());
		//todo: should be automatic
		translator.invalidateCache(entityDialog.getValue());
		return this;
	}
}
