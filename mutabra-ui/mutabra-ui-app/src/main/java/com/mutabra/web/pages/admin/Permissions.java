package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.Permission;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.TranslationService;
import com.mutabra.services.security.PermissionQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.PermissionDialog;
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
public class Permissions extends AbstractPage {

	@InjectService("permissionService")
	private BaseEntityService<Permission, PermissionQuery> permissionService;

	@Inject
	private TranslationService translationService;

	@Inject
	private Translator translator;

	@InjectComponent
	private PermissionDialog entityDialog;

	@Property
	private Permission row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Permission>(permissionService.query(), Permission.class);
	}

	Object onEdit(final Permission permission) {
		return entityDialog.show(permission);
	}

	Object onSuccess() {
		permissionService.saveOrUpdate(entityDialog.getValue());
		translationService.saveTranslations(entityDialog.getTranslations());
		//todo: should be automatic
		translator.invalidateCache(entityDialog.getValue());
		return this;
	}
}
