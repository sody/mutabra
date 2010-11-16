package com.noname.web.pages.common;

import com.noname.domain.common.Race;
import com.noname.services.common.RaceService;
import com.noname.web.base.pages.CodedEntityPage;
import com.noname.web.services.AuthorityConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_ADMIN)
public class Races extends CodedEntityPage<Race> {

	@Inject
	private RaceService raceService;

	@Override
	protected RaceService getEntityService() {
		return raceService;
	}
}