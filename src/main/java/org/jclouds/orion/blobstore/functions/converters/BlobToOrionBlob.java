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
package org.jclouds.orion.blobstore.functions.converters;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.blobstore.domain.Blob;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * @author Adrian Cole
 */
@Singleton
public class BlobToOrionBlob implements Function<Blob, OrionBlob> {
	private final BlobMetadataToBlobProperties blob2ObjectMd;
	private final OrionBlob.Factory objectProvider;

	@Inject
	BlobToOrionBlob(BlobMetadataToBlobProperties blob2ObjectMd, OrionBlob.Factory objectProvider) {
		this.blob2ObjectMd = blob2ObjectMd;
		this.objectProvider = objectProvider;
	}

	@Override
	public OrionBlob apply(Blob from) {
		if (from == null) {
			return null;
		}
		OrionBlob object = objectProvider.create(blob2ObjectMd.apply(from.getMetadata()));
		object.setPayload(Preconditions.checkNotNull(from.getPayload(), "payload: " + from));
		object.setAllHeaders(from.getAllHeaders());
		return object;
	}
}
