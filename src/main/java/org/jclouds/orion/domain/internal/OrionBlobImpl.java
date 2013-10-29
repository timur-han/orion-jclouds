/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.orion.domain.internal;

import javax.inject.Inject;

import org.jclouds.http.internal.PayloadEnclosingImpl;
import org.jclouds.io.Payload;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author Adrian Cole, Timur Sungur
 */
public class OrionBlobImpl extends PayloadEnclosingImpl implements OrionBlob, Comparable<OrionBlob> {

	private final MutableBlobProperties properties;
	private Multimap<String, String> allHeaders = LinkedHashMultimap.create();

	@Inject
	public OrionBlobImpl(MutableBlobProperties properties) {
		super();
		this.properties = properties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MutableBlobProperties getProperties() {
		return properties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Multimap<String, String> getAllHeaders() {
		return allHeaders;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAllHeaders(Multimap<String, String> allHeaders) {
		this.allHeaders = Preconditions.checkNotNull(allHeaders, "allHeaders");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(OrionBlob o) {
		if (getProperties().getName() == null) {
			return -1;
		}
		return (this == o) ? 0 : getProperties().getName().compareTo(o.getProperties().getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrionBlobImpl other = (OrionBlobImpl) obj;
		if (properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[properties=" + properties + "]";
	}

	@Override
	public void setPayload(Payload data) {
		super.setPayload(data);
		properties.setContentMetadata(data.getContentMetadata());
	}

}
