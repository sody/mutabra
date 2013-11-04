package com.mutabra.domain.common;

import org.mongodb.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "faces", noClassnameStored = true)
public class Face extends CodedEntity {
    public static final String BASENAME = "face";

    @Override
    public String getBasename() {
        return BASENAME;
    }
}
