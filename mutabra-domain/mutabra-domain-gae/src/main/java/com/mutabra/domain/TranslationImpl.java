package com.mutabra.domain;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.TRANSLATION)
public class TranslationImpl extends BaseEntityImpl implements Translation {

	@Persistent
	private String type;

	@Persistent
	private String code;

	@Persistent
	private String variant;

	@Persistent
	private Locale locale = Locale.ROOT;

	@Persistent
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