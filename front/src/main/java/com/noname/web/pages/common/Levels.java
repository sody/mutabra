package com.noname.web.pages.common;

import com.noname.domain.common.Level;
import com.noname.services.common.LevelService;
import ga.tapestry.commonlib.base.pages.TranslatableEntityPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
public class Levels extends TranslatableEntityPage<Level> {

	@Inject
	private LevelService levelService;

	@Override
	protected LevelService getEntityService() {
		return levelService;
	}
}