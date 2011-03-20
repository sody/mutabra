package com.mutabra.web.pages.common;

import com.mutabra.domain.common.Race;
import com.mutabra.services.common.RaceService;
import com.mutabra.web.base.pages.CodedEntityPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.ROLE_ADMIN)
public class Races extends CodedEntityPage<Race> {

	@Inject
	private RaceService raceService;

	@Override
	protected RaceService getEntityService() {
		return raceService;
	}
}