package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Effect;
import org.greatage.domain.EntityRepository;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SummonScript implements EffectScript {
	private final Class<? extends BattleCreature> realCreatureClass;

	public SummonScript(final EntityRepository repository) {
		this.realCreatureClass = repository.create(BattleCreature.class).getClass();
	}

	public void execute(final BattleUnit caster, final Effect effect, final Object target) {
		if (target != null && target instanceof Position && caster instanceof BattleHero) {
			final BattleCreature creature = ReflectionUtils.newInstance(realCreatureClass, caster, effect);
			creature.setHealth(effect.getHealth());
			creature.setPosition((Position) target);
			creature.setExhausted(true);
			((BattleHero) caster).getCreatures().add(creature);
		}
	}
}
