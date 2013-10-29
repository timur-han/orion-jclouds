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

import java.net.URI;
import java.util.Date;
import java.util.Map;

import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.domain.Location;
import org.jclouds.domain.ResourceMetadata;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.domain.OrionStorageMetadata;

/**
 * @author timur
 * 
 */
public class OrionStorageMetadataImpl implements OrionStorageMetadata {
	private StorageType type;

	private String name;
	private URI uri;
	private Map<String, String> userMetadata;
	private String eTag;
	private Date creationDate;
	private Date lastModified;
	private Location location;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getType()
	 */
	@Override
	public StorageType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getProviderId()
	 */
	@Override
	public String getProviderId() {
		return OrionConstantValues.ORION_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getUri()
	 */
	@Override
	public URI getUri() {
		return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getUserMetadata()
	 */
	@Override
	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getETag()
	 */
	@Override
	public String getETag() {
		return eTag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getCreationDate()
	 */
	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.StorageMetadata#getLastModified()
	 */
	@Override
	public Date getLastModified() {
		return lastModified;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.domain.ResourceMetadata#getLocation()
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ResourceMetadata<StorageType> resourceMetadata) {
		return resourceMetadata.getName().compareTo(getName());
	}

	/**
	 * @param type
	 *           the type to set
	 */
	@Override
	public void setType(StorageType type) {
		this.type = type;
	}

	/**
	 * @return the eTag
	 */
	@Override
	public String geteTag() {
		return eTag;
	}

	/**
	 * @param eTag
	 *           the eTag to set
	 */
	@Override
	public void setETag(String eTag) {
		this.eTag = eTag;
	}

	/**
	 * @param name
	 *           the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param uri
	 *           the uri to set
	 */
	@Override
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * @param userMetadata
	 *           the userMetadata to set
	 */
	@Override
	public void setUserMetadata(Map<String, String> userMetadata) {
		this.userMetadata = userMetadata;
	}

	/**
	 * @param creationDate
	 *           the creationDate to set
	 */
	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @param lastModified
	 *           the lastModified to set
	 */
	@Override
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @param location
	 *           the location to set
	 */
	@Override
	public void setLocation(Location location) {
		this.location = location;
	}

}
