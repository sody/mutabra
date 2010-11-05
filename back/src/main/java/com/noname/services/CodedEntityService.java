package com.noname.services;

import com.noname.domain.CodedEntity;
import ga.domain.i18n.TranslatableEntityService;

/**
 * @author Ivan Khalopik
 */
public interface CodedEntityService<E extends CodedEntity>
		extends BaseEntityService<E>, TranslatableEntityService<E> {
}
