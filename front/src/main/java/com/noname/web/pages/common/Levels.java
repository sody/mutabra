package com.noname.web.pages.common;

import com.noname.domain.common.Level;
import com.noname.services.common.LevelService;
import com.noname.web.base.pages.CodedEntityPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
public class Levels extends CodedEntityPage<Level> {

	@Inject
	private LevelService levelService;

	@Override
	protected LevelService getEntityService() {
		return levelService;
	}
}