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

import com.google.code.morphia.Datastore;
import com.mutabra.domain.Entity;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderFactory;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 */
public class MorphiaEncoderFactory<PK extends Serializable> implements ValueEncoderFactory<Entity<PK>> {
    private static final String NEW_ENTITY_VALUE = "new";

    private final TypeCoercer typeCoercer;
    private final Datastore datastore;
    private final Class<PK> pkClass;

    public MorphiaEncoderFactory(final TypeCoercer typeCoercer, final Datastore datastore, final Class<PK> pkClass) {
        assert typeCoercer != null;
        assert datastore != null;
        assert pkClass != null;

        this.datastore = datastore;
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
                    try {
                        return type.newInstance();
                    } catch (InstantiationException e) {
                        throw new NotFoundException("Couldn't create new entity.", e);
                    } catch (IllegalAccessException e) {
                        throw new NotFoundException("Couldn't create new entity.", e);
                    }
                }

                final PK pk;
                try {
                    pk = typeCoercer.coerce(clientValue, pkClass);
                } catch (RuntimeException e) {
                    throw new NotFoundException("Couldn't parse entity primary key", e);
                }
                final Entity<PK> entity = pk != null ? datastore.get(type, pk) : null;
                if (entity == null) {
                    throw new NotFoundException("Entity is not found.");
                }
                return entity;
            }

            @Override
            public String toString() {
                return String.format("MorphiaEncoder[%s]", type);
            }
        };
    }

    @Override
    public String toString() {
        return String.format("MorphiaEncoderFactory[%s]", pkClass);
    }
}
