package com.mutabra.domain;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Translation extends BaseEntity {

	String getValue();

	void setValue(String value);

	String getType();

	void setType(String type);

	String getCode();

	void setCode(String code);

	String getVariant();

	void setVariant(String variant);

	Locale getLocale();

	void setLocale(Locale locale);
}
