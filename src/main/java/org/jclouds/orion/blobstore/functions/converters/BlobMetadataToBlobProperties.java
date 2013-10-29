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

import java.util.Map.Entry;

import javax.inject.Singleton;

import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.http.HttpUtils;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.domain.BlobType;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.internal.MutableBlobPropertiesImpl;

import com.google.common.base.Function;

/**
 * @author Adrian Cole, Timur Sungur
 */
@Singleton
public class BlobMetadataToBlobProperties implements Function<BlobMetadata, MutableBlobProperties> {

	@Override
	public MutableBlobProperties apply(BlobMetadata from) {
		if (from == null) {
			return null;
		}
		MutableBlobProperties to = new MutableBlobPropertiesImpl();
		HttpUtils.copy(from.getContentMetadata(), to.getContentMetadata());
		to.setParentPath(OrionUtils.getParentPath(from.getName()));
		to.setETag(from.getETag());
		to.setName(OrionUtils.getName(from.getName()));
		to.setUrl(from.getUri());
		to.setLastModified(from.getLastModified());

		if (from.getUserMetadata() != null) {
			for (Entry<String, String> entry : from.getUserMetadata().entrySet()) {
				to.getMetadata().put(entry.getKey().toLowerCase(), entry.getValue());
			}
		}
		if (from.getType() == StorageType.FOLDER) {
			to.setType(BlobType.FOLDER_BLOB);
		} else if (from.getType() == StorageType.BLOB) {
			to.setType(BlobType.FILE_BLOB);
		} else if (from.getType() == StorageType.CONTAINER) {
			to.setType(BlobType.PROJECT_BLOB);
		} else {
			to.setType(BlobType.UNRECOGNIZED);
		}
		return to;
	}
}
