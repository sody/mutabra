package com.mutabra.domain.common;

import com.google.code.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "levels", noClassnameStored = true)
public class Level extends CodedEntity {

    private LevelType type;
    private long rating;

    public LevelType getType() {
        return type;
    }

    public void setType(final LevelType type) {
        this.type = type;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }
}
