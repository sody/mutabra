package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.ChangeSet;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.db.DatabaseService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.internal.BaseEntityDataSource;
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
public class ChangeSets extends AbstractPage {

	@InjectService("changeSetService")
	private BaseEntityService<ChangeSet> changeSetService;

	@Inject
	private DatabaseService databaseService;

	@Property
	private ChangeSet row;

	@Property
	private boolean dropFirst;

	@Property
	private boolean clearCheckSums;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<ChangeSet>(changeSetService.query(), ChangeSet.class);
	}

	void onSuccess() {
		databaseService.update(dropFirst, clearCheckSums);
	}
}
