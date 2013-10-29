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

import javax.inject.Singleton;

import org.jclouds.blobstore.domain.MutableBlobMetadata;
import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.blobstore.domain.internal.MutableBlobMetadataImpl;
import org.jclouds.http.HttpUtils;
import org.jclouds.orion.domain.BlobProperties;

import com.google.common.base.Function;

/**
 * @author Adrian Cole
 */

@Singleton
public class BlobPropertiesToBlobMetadata implements Function<BlobProperties, MutableBlobMetadata> {

	@Override
	public MutableBlobMetadata apply(BlobProperties from) {
		if (from == null) {
			return null;
		}
		MutableBlobMetadata to = new MutableBlobMetadataImpl();
		HttpUtils.copy(from.getContentMetadata(), to.getContentMetadata());
		to.setUserMetadata(from.getMetadata());
		to.setETag(from.getETag());
		to.setLastModified(from.getLastModified());
		to.setName(from.getName());
		to.setContainer(from.getContainer());
		to.setUri(from.getUrl());

		if (from.getType() == org.jclouds.orion.domain.BlobType.FOLDER_BLOB) {
			to.setType(StorageType.FOLDER);
		} else if (from.getType() == org.jclouds.orion.domain.BlobType.FILE_BLOB) {
			to.setType(StorageType.BLOB);
		} else if (from.getType() == org.jclouds.orion.domain.BlobType.PROJECT_BLOB) {
			to.setType(StorageType.CONTAINER);
		} else {
			to.setType(null);
		}
		return to;
	}
}
