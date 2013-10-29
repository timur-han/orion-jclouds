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
package org.jclouds.orion.domain;

import java.net.URI;
import java.util.Date;
import java.util.Map;

import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.domain.Location;
import org.jclouds.domain.ResourceMetadata;

/**
 * Orion version of {@code StorageMetadata}
 * 
 * @author timur
 * 
 */
public interface OrionStorageMetadata extends StorageMetadata {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getType()
	 */
	@Override
	public abstract StorageType getType();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getProviderId()
	 */
	@Override
	public abstract String getProviderId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getName()
	 */
	@Override
	public abstract String getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getUri()
	 */
	@Override
	public abstract URI getUri();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getUserMetadata()
	 */
	@Override
	public abstract Map<String, String> getUserMetadata();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getETag()
	 */
	@Override
	public abstract String getETag();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getCreationDate()
	 */
	@Override
	public abstract Date getCreationDate();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getLastModified()
	 */
	@Override
	public abstract Date getLastModified();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.domain.ResourceMetadata#getLocation()
	 */
	@Override
	public abstract Location getLocation();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public abstract int compareTo(ResourceMetadata<StorageType> o);

	/**
	 * @param type
	 *           the type to set
	 */
	public abstract void setType(StorageType type);

	/**
	 * @return the eTag
	 */
	public abstract String geteTag();

	/**
	 * @param eTag
	 *           the eTag to set
	 */
	public abstract void setETag(String eTag);

	/**
	 * @param name
	 *           the name to set
	 */
	public abstract void setName(String name);

	/**
	 * @param uri
	 *           the uri to set
	 */
	public abstract void setUri(URI uri);

	/**
	 * @param userMetadata
	 *           the userMetadata to set
	 */
	public abstract void setUserMetadata(Map<String, String> userMetadata);

	/**
	 * @param creationDate
	 *           the creationDate to set
	 */
	public abstract void setCreationDate(Date creationDate);

	/**
	 * @param lastModified
	 *           the lastModified to set
	 */
	public abstract void setLastModified(Date lastModified);

	/**
	 * @param location
	 *           the location to set
	 */
	public abstract void setLocation(Location location);

}