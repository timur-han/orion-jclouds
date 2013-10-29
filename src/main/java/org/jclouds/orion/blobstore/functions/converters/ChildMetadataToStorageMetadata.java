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

import java.sql.Date;

import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.domain.Credentials;
import org.jclouds.domain.Location;
import org.jclouds.location.Provider;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.domain.OrionChildMetadata;
import org.jclouds.orion.domain.OrionStorageMetadata;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

/**
 * @author timur
 * 
 */
public class ChildMetadataToStorageMetadata implements Function<OrionChildMetadata, OrionStorageMetadata> {

	private final OrionStorageMetadata storageMetadata;
	private final Location location;
	private final String userWorkspace;

	@Inject
	public ChildMetadataToStorageMetadata(@Provider Supplier<Credentials> creds, OrionStorageMetadata storageMetadata,
	      Supplier<Location> defaultLocation) {
		this.userWorkspace = Preconditions.checkNotNull(creds, "creds is null").get().identity;
		this.location = Preconditions.checkNotNull(defaultLocation, "defaultLocation is null").get();
		this.storageMetadata = Preconditions.checkNotNull(storageMetadata, "storageMetadata is null");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public OrionStorageMetadata apply(OrionChildMetadata childMetadata) {
		storageMetadata.setLocation(location);
		storageMetadata
		      .setName(OrionUtils.createOriginalNameFromLocation(getUserWorkspace(), childMetadata.getLocation()));

		if (OrionUtils.isContainerFromPath(childMetadata.getLocation())) {
			storageMetadata.setType(StorageType.CONTAINER);
		} else if (childMetadata.isDirectory()) {
			storageMetadata.setType(StorageType.FOLDER);
		} else if (!childMetadata.isDirectory()) {
			storageMetadata.setType(StorageType.BLOB);
		}

		storageMetadata.setLastModified(new Date(childMetadata.getLocalTimeStamp()));

		return storageMetadata;
	}

	/**
	 * @return
	 */
	private String getUserWorkspace() {
		return userWorkspace;
	}
}
