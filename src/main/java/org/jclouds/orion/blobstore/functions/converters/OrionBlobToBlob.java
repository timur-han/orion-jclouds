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
import org.jclouds.blobstore.domain.Blob.Factory;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * 
 * @author timur
 * 
 */
@Singleton
public class OrionBlobToBlob implements Function<OrionBlob, Blob> {
	private final Factory blobFactory;
	private final BlobPropertiesToBlobMetadata blobPr2BlobMd;

	@Inject
	OrionBlobToBlob(Factory blobFactory, BlobPropertiesToBlobMetadata blobPr2BlobMd) {
		this.blobFactory = Preconditions.checkNotNull(blobFactory, "blobFactory");
		this.blobPr2BlobMd = Preconditions.checkNotNull(blobPr2BlobMd, "blobPr2BlobMd");
	}

	@Override
	public Blob apply(OrionBlob from) {
		if (from == null) {
			return null;
		}
		Blob blob = blobFactory.create(blobPr2BlobMd.apply(from.getProperties()));
		blob.setPayload(Preconditions.checkNotNull(from.getPayload(), "payload: " + from));
		blob.setAllHeaders(from.getAllHeaders());
		return blob;
	}
}
