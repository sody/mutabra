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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.greatage.domain.Repository;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEEntityEncoderFactory implements ValueEncoderFactory<BaseEntity> {
	private static final String NEW_ENTITY_VALUE = "new";

	private final Repository repository;

	public GAEEntityEncoderFactory(final Repository repository) {
		assert repository != null;

		this.repository = repository;
	}

	public ValueEncoder<BaseEntity> create(final Class<BaseEntity> type) {
		return new ValueEncoder<BaseEntity>() {
			public String toClient(final BaseEntity value) {
				if (value == null) {
					return null;
				}
				if (value.isNew()) {
					return NEW_ENTITY_VALUE;
				}
				final Key key = ((BaseEntityImpl) value).getKey().getRaw();
				return KeyFactory.keyToString(key);
			}

			public BaseEntity toValue(final String clientValue) {
				if (NEW_ENTITY_VALUE.equals(clientValue)) {
					return repository.create(type);
				}
				if (StringUtils.isEmpty(clientValue)) {
					return null;
				}
				final Key key = KeyFactory.stringToKey(clientValue);
				if (key == null) {
					return null;
				}
				return Keys.getInstance(com.googlecode.objectify.Key.<BaseEntity>typed(key));
			}

			@Override
			public String toString() {
				final StringBuilder sb = new StringBuilder("GAEEntityEncoder(");
				sb.append("class=").append(type);
				sb.append(")");
				return sb.toString();
			}
		};
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("GAEEntityEncoderFactory(");
		sb.append(")");
		return sb.toString();
	}
}
