/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mutabra.web.internal;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.greatage.domain.Entity;
import org.greatage.domain.EntityRepository;

import java.io.Serializable;

/**
 * Needs ga:ga-core dependency.
 *
 * @author Ivan Khalopik
 */
public class EntityEncoderFactory<PK extends Serializable> implements ValueEncoderFactory<Entity<PK>> {
	private static final String NEW_ENTITY_VALUE = "new";

	private final TypeCoercer typeCoercer;
	private final EntityRepository repository;
	private final Class<PK> pkClass;

	public EntityEncoderFactory(final TypeCoercer typeCoercer, final EntityRepository repository, final Class<PK> pkClass) {
		assert typeCoercer != null;
		assert repository != null;
		assert pkClass != null;

		this.repository = repository;
		this.typeCoercer = typeCoercer;
		this.pkClass = pkClass;
	}

	public ValueEncoder<Entity<PK>> create(final Class<Entity<PK>> type) {
		return new ValueEncoder<Entity<PK>>() {
			public String toClient(final Entity<PK> value) {
				return value == null ? null : value.isNew() ? NEW_ENTITY_VALUE : typeCoercer.coerce(value.getId(), String.class);
			}

			public Entity<PK> toValue(final String clientValue) {
				if (NEW_ENTITY_VALUE.equals(clientValue)) {
					return repository.create(type);
				}
				final PK pk = typeCoercer.coerce(clientValue, pkClass);
				final Entity<PK> entity = pk != null ? repository.get(type, pk) : null;
				if (entity == null) {
					throw new NotFoundException();
				}
				return entity;
			}

			@Override
			public String toString() {
				final StringBuilder sb = new StringBuilder("EntityEncoder(");
				sb.append("class=").append(type);
				sb.append(")");
				return sb.toString();
			}
		};
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EntityEncoderFactory(");
		sb.append("pkClass=").append(pkClass);
		sb.append(")");
		return sb.toString();
	}
}
