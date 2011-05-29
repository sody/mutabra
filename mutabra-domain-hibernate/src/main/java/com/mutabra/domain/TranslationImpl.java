package com.mutabra.domain;

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
public class TranslationImpl extends BaseEntityImpl implements Translation {

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

	public void setValue(final String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(final String variant) {
		this.variant = variant;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}
}