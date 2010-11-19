package com.noname.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "TRANSLATION")
public class Translation extends BaseEntity {
	public static final String TYPE_PROPERTY = "type";
	public static final String CODE_PROPERTY = "code";
	public static final String VARIANT_PROPERTY = "variant";
	public static final String LOCALE_PROPERTY = "locale";
	public static final String VALUE_PROPERTY = "value";

	@Column(name = "BASENAME", nullable = false)
	private String type;

	@Column(name = "CODE", nullable = false)
	private String code;

	@Column(name = "VARIANT", nullable = false)
	private String variant;

	@Column(name = "LOCALE", nullable = false)
	private Locale locale = Locale.ROOT;

	@Column(name = "VALUE", nullable = false)
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}