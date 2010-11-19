package com.noname.web.components.game;

import com.noname.domain.common.Race;
import com.noname.services.common.RaceService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceSelect {

	@Property
	@Parameter
	private Race race;

	@Inject
	private RaceService raceService;

	private List<Race> races;

	@Property
	private Object row;

	public List<Race> getRaces() {
		if (races == null) {
			races = raceService.getEntities();
		}
		return races;
	}
}
