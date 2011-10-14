package com.mutabra.domain;

import com.mutabra.db.Tables;
import org.greatage.util.LocaleUtils;

import javax.jdo.annotations.NotPersistent;
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
	private String locale = "";

	@NotPersistent
	private Locale localeValue;

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
		if (localeValue == null) {
			localeValue = LocaleUtils.parseLocale(locale);
		}
		return localeValue;
	}

	public void setLocale(final Locale locale) {
		localeValue = locale;
		this.locale = locale != null ? locale.toString() : null;
	}
}