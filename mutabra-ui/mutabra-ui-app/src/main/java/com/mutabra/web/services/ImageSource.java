package com.mutabra.web.services;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.player.Hero;
import org.apache.tapestry5.Asset;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ImageSource {

	Asset getRaceImage(Race race, int size);

	Asset getFaceImage(Face face, int size);

	Asset getCardImage(Card card, int size);

	Asset getHeroImage(Hero hero, int size);
}
