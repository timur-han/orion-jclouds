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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jclouds.orion.domain.OrionChildMetadata;

/**
 * @author timur
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrionChildMetadataImpl implements OrionChildMetadata {
	@JsonProperty("ChildrenLocation")
	private String childrenLocation;
	@JsonProperty("Directory")
	boolean directory;
	@JsonProperty("Id")
	private String id;
	@JsonProperty("ImportLocation")
	private String importLocation;
	@JsonProperty("Location")
	private String location;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("LocalTimeStamp")
	private Long localTimeStamp;
	@JsonProperty("Length")
	private Long length;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#getChildrenLocation()
	 */
	@Override
	public String getChildrenLocation() {
		return childrenLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#setChildrenLocation(java.lang
	 * .String)
	 */
	@Override
	public void setChildrenLocation(String childrenLocation) {
		this.childrenLocation = childrenLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#getDirectory()
	 */
	@Override
	public boolean isDirectory() {
		return directory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jclouds.orion.domain.internal.Child#setDirectory(java.lang.String)
	 */
	@Override
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#getImportLocation()
	 */
	@Override
	public String getImportLocation() {
		return importLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jclouds.orion.domain.internal.Child#setImportLocation(java.lang.String
	 * )
	 */
	@Override
	public void setImportLocation(String importLocation) {
		this.importLocation = importLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#getLocation()
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#setLocation(java.lang.String)
	 */
	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.internal.Child#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.OrionChildMetadata#getLength()
	 */
	@Override
	public Long getLength() {
		return this.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.OrionChildMetadata#setLength(int)
	 */
	@Override
	public void setLength(Long length) {
		this.length = length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.OrionChildMetadata#getLocalTimeStamp()
	 */
	@Override
	public Long getLocalTimeStamp() {
		return this.localTimeStamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.orion.domain.OrionChildMetadata#setLocalTimeStamp(int)
	 */
	@Override
	public void setLocalTimeStamp(Long localTimeStamp) {
		this.localTimeStamp = localTimeStamp;

	}

}
