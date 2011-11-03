package com.mutabra.web.components.image;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.player.Hero;
import com.mutabra.web.base.components.AbstractImage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 * @since 1.11
 */
public class HeroImage extends AbstractImage {

	@Property
	@Parameter(required = true)
	private Hero hero;

	@Override
	protected String getTitle() {
		return hero != null ? hero.getName() :"<anonymous>";
	}

	@Override
	protected String getAlt() {
		return hero != null ? hero.getName() :"<anonymous>";
	}

	@Override
	protected String getPath() {
		return "img/heroes/";
	}

	@Override
	protected String getName() {
		return hero != null ? hero.getRace().getCode().toLowerCase() + "_" + hero.getFace().getCode().toLowerCase() : "anonymous";
	}
}
