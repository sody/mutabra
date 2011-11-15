package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.Role;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.TranslationService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.RoleDialog;
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
public class Roles extends AbstractPage {

	@InjectService("roleService")
	private CodedEntityService<Role> roleService;

	@Inject
	private TranslationService translationService;

	@Inject
	private Translator translator;

	@InjectComponent
	private RoleDialog entityDialog;

	@Property
	private Role row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Role>(roleService.query(), Role.class);
	}

	Object onEdit(final Role role) {
		return entityDialog.show(role);
	}

	Object onSuccess() {
		roleService.saveOrUpdate(entityDialog.getValue());
		translationService.saveTranslations(entityDialog.getTranslations());
		//todo: should be automatic
		translator.invalidateCache(entityDialog.getValue());
		return this;
	}
}
