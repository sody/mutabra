package com.mutabra.web.services;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Hero;
import org.apache.tapestry5.Asset;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ImageSource {

	Asset getNotFoundImage();

	Asset getRaceImage(Race race);

	Asset getFaceImage(Face face);

	Asset getCardImage(Card card);

	Asset getHeroImage(Hero hero);
}
