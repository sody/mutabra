package com.mutabra.web.internal;

import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Hero;
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
    private static final String ABILITY_REPOSITORY = "img/abilities/";
    private static final String HERO_REPOSITORY = "img/heroes/";

    private final AssetSource assetSource;
    private final ThreadLocale locale;

    private final Asset notFound;
    private final Asset cardBack;

    public ImageSourceImpl(final AssetSource assetSource, final ThreadLocale locale) {
        this.assetSource = assetSource;
        this.locale = locale;

        //todo: implement all different not found assets
        notFound = assetSource.getContextAsset(HERO_REPOSITORY + "anonymous.svg", locale.getLocale());

        cardBack = getContextAsset(CARD_REPOSITORY, "back");
    }

    public Asset getNotFoundImage() {
        return notFound;
    }

    public Asset getRaceImage(final Race race) {
        return race != null ?
                getContextAsset(RACE_REPOSITORY, race.getCode()) :
                getNotFoundImage();
    }

    public Asset getFaceImage(final Face face) {
        return face != null ?
                getContextAsset(FACE_REPOSITORY, face.getCode()) :
                getNotFoundImage();
    }

    public Asset getCardImage(final Card card) {
        return card != null ?
                getContextAsset(CARD_REPOSITORY, card.getCode()) :
                getNotFoundImage();
    }

    public Asset getAbilityImage(final Ability ability) {
        return ability != null ?
                getContextAsset(ABILITY_REPOSITORY, ability.getCode()) :
                getNotFoundImage();
    }

    public Asset getCardBack() {
        return cardBack;
    }

    public Asset getHeroImage(final Hero hero) {
        return hero != null ?
                getFaceImage(hero.getFace()) :
//				getContextAsset(HERO_REPOSITORY, hero.getRace().getCode() + "_" + hero.getFace().getCode(), size, notFound) :
                getNotFoundImage();
    }

    private Asset getContextAsset(final String repository, final String code) {
        final StringBuilder builder = new StringBuilder(repository);
        builder.append(code).append(".svg");
        try {
            return assetSource.getContextAsset(builder.toString(), locale.getLocale());
        } catch (Exception e) {
            // return default if not found
            return getNotFoundImage();
        }
    }
}
