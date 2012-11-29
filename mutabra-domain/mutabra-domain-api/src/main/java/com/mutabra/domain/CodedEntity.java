package com.mutabra.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CodedEntity extends BaseEntity, Translatable {

    String getCode();
}
