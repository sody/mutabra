package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleHero;
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

	public void execute(final ScriptContext context) {
		final BattleHero caster = (BattleHero) context.getCaster();
		final Effect effect = context.getEffect();

		for (BattleField field : context.getTargets()) {
			if (!field.hasUnit()) {
				final BattleCreature creature = ReflectionUtils.newInstance(realCreatureClass, caster, effect);
				creature.setHealth(effect.getHealth());
				creature.setPosition(field.getPosition());
				creature.setExhausted(true);

				caster.getCreatures().add(creature);
			}
		}
	}
}
