package com.mutabra.web.internal;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.player.Hero;
import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.services.AssetSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ImageSourceImpl implements ImageSource {
	private static final String RACE_REPOSITORY = "img/races/";
	private static final String FACE_REPOSITORY = "img/faces/";
	private static final String CARD_REPOSITORY = "img/cards/";
	private static final String HERO_REPOSITORY = "img/heroes/";

	private final AssetSource assetSource;
	private final ThreadLocale locale;
	private final Asset notFound;

	public ImageSourceImpl(final AssetSource assetSource, final ThreadLocale locale) {
		this.assetSource = assetSource;
		this.locale = locale;

		//todo: implement all different not found assets
		notFound = assetSource.getContextAsset(HERO_REPOSITORY + "anonymous_64x64.png", locale.getLocale());
	}

	public Asset getNotFoundImage(final int size) {
		return notFound;
	}

	public Asset getRaceImage(final Race race, final int size) {
		return race != null ?
				getContextAsset(RACE_REPOSITORY, race.getCode(), size) :
				getNotFoundImage(size);
	}

	public Asset getFaceImage(final Face face, final int size) {
		return face != null ?
				getContextAsset(FACE_REPOSITORY, face.getCode(), size) :
				getNotFoundImage(size);
	}

	public Asset getCardImage(final Card card, final int size) {
		return card != null ?
				getContextAsset(CARD_REPOSITORY, card.getCode(), size) :
				getNotFoundImage(size);
	}

	public Asset getHeroImage(final Hero hero, final int size) {
		return hero != null ?
				getRaceImage(hero.getRace(), size) :
//				getContextAsset(HERO_REPOSITORY, hero.getRace().getCode() + "_" + hero.getFace().getCode(), size, notFound) :
				getNotFoundImage(size);
	}

	private Asset getContextAsset(final String repository, final String code, final int size) {
		final StringBuilder builder = new StringBuilder(repository);
		builder.append(code).append('_').append(size).append('x').append(size).append(".png");
		try {
			return assetSource.getContextAsset(builder.toString(), locale.getLocale());
		} catch (Exception e) {
			// return default if not found
			return getNotFoundImage(size);
		}
	}
}
