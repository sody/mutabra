package com.noname.web.components.game;

import com.noname.domain.common.Race;
import com.noname.services.common.RaceService;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.greatage.tapestry.internal.SelectModelBuilder;

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

	@Inject
	private SelectModelBuilder selectModelBuilder;

	private List<Race> races;

	@Property
	private Object row;

	@Inject
	private AssetSource assetSource;

	public List<Race> getRaces() {
		if (races == null) {
			races = raceService.getEntities();
		}
		return races;
	}

	public Asset getRaceImage() {
		final String code = ((Race) row).getCode();
		return assetSource.getContextAsset("img/races/" + code.toLowerCase() + ".png", null);
	}
}
