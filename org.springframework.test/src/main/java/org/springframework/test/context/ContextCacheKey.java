/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.test.context;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;

/**
 * A key that a custom context loader can use to cache own data.
 * @author <a href="mailto:kristian@zeniorD0Tno">Kristian Rosenvold</a>
 * */
public class ContextCacheKey {
	private final String key;

	public ContextCacheKey(Serializable key) {
		this.key = ObjectUtils.nullSafeToString(key); // + contextLoader.getClass().getName();
	}

	@SuppressWarnings({"RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ContextCacheKey key1 = (ContextCacheKey) o;

		if (key != null ? !key.equals(key1.key) : key1.key != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return key != null ? key.hashCode() : 0;
	}
}
