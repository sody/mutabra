package com.mutabra.domain;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CodedEntity extends BaseEntity, Translatable {

	String CODE_PROPERTY = "code";

	String getCode();

	void setCode(String code);
}
