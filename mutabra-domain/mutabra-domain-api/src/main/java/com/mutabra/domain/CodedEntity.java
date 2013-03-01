package com.mutabra.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Deprecated
public interface CodedEntity extends BaseEntity, Translatable {

    String getCode();
}
