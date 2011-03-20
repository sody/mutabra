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

	Map<String, Translation> getTranslations();

	void setTranslations(Map<String, Translation> translations);
}
