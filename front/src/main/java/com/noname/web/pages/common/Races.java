package com.noname.web.pages.common;

import com.noname.domain.common.Race;
import com.noname.services.common.RaceService;
import com.noname.web.base.pages.CodedEntityPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Secured;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Secured
public class Races extends CodedEntityPage<Race> {

	@Inject
	private RaceService raceService;

	@Override
	protected RaceService getEntityService() {
		return raceService;
	}
}