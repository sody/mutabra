package com.mutabra.domain.common;

import org.mongodb.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "levels", noClassnameStored = true)
public class Level extends CodedEntity {
    public static final String BASENAME = "level";

    private LevelType type;
    private long rating;

    @Override
    public String getBasename() {
        return BASENAME;
    }

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
