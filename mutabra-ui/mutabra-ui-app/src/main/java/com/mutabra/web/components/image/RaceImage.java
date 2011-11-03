package com.mutabra.web.components.image;

import com.mutabra.domain.common.Race;
import com.mutabra.web.base.components.AbstractImage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceImage extends AbstractImage {

	@Property
	@Parameter(required = true, allowNull = false)
	private Race race;

	@Override
	protected String getTitle() {
		return race.getCode();
	}

	@Override
	protected String getAlt() {
		return race.getCode();
	}

	@Override
	protected String getPath() {
		return "img/races/";
	}

	@Override
	protected String getName() {
		return race.getCode().toLowerCase();
	}
}
