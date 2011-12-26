package com.mutabra.services.game;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Cards;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Levels;
import com.mutabra.domain.common.Races;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroCard;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroServiceImpl extends BaseEntityServiceImpl<Hero> implements HeroService {
	private final Class<? extends Hero> realEntityClass;
	private final Class<? extends HeroCard> realHeroCardClass;

	private final CodedEntityService<Level> levelService;
	private final CodedEntityService<Card> cardService;

	public HeroServiceImpl(final EntityRepository repository,
						   final CodedEntityService<Level> levelService,
						   final CodedEntityService<Card> cardService) {
		super(repository, Hero.class);
		this.levelService = levelService;
		this.cardService = cardService;

		realEntityClass = repository.create(Hero.class).getClass();
		realHeroCardClass = repository.create(HeroCard.class).getClass();
	}

	public Hero create(final Account account) {
		final Hero hero = ReflectionUtils.newInstance(realEntityClass, account);
		hero.setName(account.getName());
		hero.setLevel(levelService.get(Levels.NEWBIE));
		hero.setHealth(30);
		return hero;
	}

	@Transactional
	@Override
	public void save(final Hero entity) {
		super.save(entity);

		if (Races.PLUNGER.equals(entity.getRace().getCode())) {
			addCard(entity, Cards.ELECTRIC_RAY);
			addCard(entity, Cards.SEAHORSE);
			addCard(entity, Cards.MERMAID);
			addCard(entity, Cards.CALM);
			addCard(entity, Cards.WAVE);
			addCard(entity, Cards.WHIRLPOOL);
			addCard(entity, Cards.TRIDENT_BLOW);
			addCard(entity, Cards.SWIM_AWAY);
			addCard(entity, Cards.STORM);
			addCard(entity, Cards.DROP_OF_THE_OCEAN);
		}

		if (Races.FLYER.equals(entity.getRace().getCode())) {
			addCard(entity, Cards.CHAMOIS);
			addCard(entity, Cards.CARRION_VULTURE);
			addCard(entity, Cards.CHIVES);
			addCard(entity, Cards.SCRAMBLE);
			addCard(entity, Cards.SCRATCH);
			addCard(entity, Cards.SNOWBALL);
			addCard(entity, Cards.THROW);
			addCard(entity, Cards.FALLING_BOULDER);
			addCard(entity, Cards.ECHO_MOUNTAIN);
			addCard(entity, Cards.DECOMPRESSION);
		}
	}

	private void addCard(final Hero hero, final String cardCode) {
		final HeroCard card = ReflectionUtils.newInstance(realHeroCardClass, hero);
		card.setCard(cardService.get(cardCode));
		repository().saveOrUpdate(card);
	}
}
