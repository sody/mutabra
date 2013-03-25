package com.mutabra.domain.common;

import com.google.code.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "faces", noClassnameStored = true)
public class Face extends CodedEntity {
}
