package com.mutabra.domain.common;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Card extends Castable {

	Level getLevel();

	void setLevel(Level level);

	Set<Ability> getAbilities();
}
