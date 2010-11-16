package com.noname.web.pages.common;

import com.noname.domain.common.Level;
import com.noname.services.common.LevelService;
import com.noname.web.base.pages.CodedEntityPage;
import com.noname.web.services.AuthorityConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_ADMIN)
public class Levels extends CodedEntityPage<Level> {

	@Inject
	private LevelService levelService;

	@Override
	protected LevelService getEntityService() {
		return levelService;
	}
}