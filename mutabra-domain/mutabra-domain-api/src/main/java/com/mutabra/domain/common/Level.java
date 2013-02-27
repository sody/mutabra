package com.mutabra.domain.common;

import com.mutabra.domain.BaseEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Level extends BaseEntity {

    String getCode();

    LevelType getLevelType();

    void setLevelType(LevelType levelType);

    long getRating();

    void setRating(long rating);
}
