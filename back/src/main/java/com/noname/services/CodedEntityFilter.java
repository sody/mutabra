package com.noname.services;

import com.noname.domain.CodedEntity;
import ga.domain.i18n.TranslatableEntityFilter;

/**
 * @author Ivan Khalopik
 */
public interface CodedEntityFilter<E extends CodedEntity>
		extends BaseEntityFilter<E>, TranslatableEntityFilter<E> {
}
