package com.mutabra.web.pages.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.common.LevelService;
import com.mutabra.web.base.pages.CodedEntityPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.ROLE_ADMIN)
public class Levels extends CodedEntityPage<Level> {

	@Inject
	private LevelService levelService;

	@Override
	protected LevelService getEntityService() {
		return levelService;
	}
}