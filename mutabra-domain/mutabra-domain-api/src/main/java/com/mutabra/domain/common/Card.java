package com.mutabra.domain.common;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Card extends Castable {

    List<Ability> getAbilities();
}
