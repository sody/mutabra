package com.noname.web.components.game;

import com.noname.domain.common.Race;
import com.noname.services.common.RaceService;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.tapestry.internal.SelectModelBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceSelect {

	@Parameter
	private Race race;

	@Inject
	private RaceService raceService;

	@Inject
	private SelectModelBuilder selectModelBuilder;

	private SelectModel raceModel;

	public Race getRace() {
		return race;
	}

	public void setRace(final Race race) {
		this.race = race;
	}

	public SelectModel getRaceModel() {
		if (raceModel == null) {
			final List<Race> races = raceService.getEntities();
			raceModel = selectModelBuilder.buildFormatted(Race.class, races, "%s", "this:description");
		}
		return raceModel;
	}
}
