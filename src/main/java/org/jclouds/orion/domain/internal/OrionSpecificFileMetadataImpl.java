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

import org.codehaus.jackson.annotate.JsonProperty;
import org.jclouds.orion.domain.Attributes;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

/**
 * @author timur
 * 
 */
public class OrionSpecificFileMetadataImpl implements OrionSpecificFileMetadata {

	@JsonProperty("Name")
	private String name;
	@JsonProperty("Directory")
	private Boolean directory = false;
	@JsonProperty("ETag")
	private String eTag;
	@JsonProperty("LocalTimeStamp")
	private long localTimeStamp;
	@JsonProperty("Location")
	private String location;
	@JsonProperty("ChildrenLocation")
	private String childrenLocation;
	@JsonProperty("Attributes")
	private Attributes attributes = new AttributesImpl();
	@JsonProperty("CharSet")
	private String charSet;
	@JsonProperty("ContentType")
	private String contentType;
	@JsonProperty("ContentLength")
	private long contentLegth;

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
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
	 * @return the directory
	 */
	@Override
	public Boolean getDirectory() {
		return directory;
	}

	/**
	 * @param directory
	 *           the directory to set
	 */
	@Override
	public void setDirectory(Boolean directory) {
		this.directory = directory;
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
	public void seteTag(String eTag) {
		this.eTag = eTag;
	}

	/**
	 * @return the localTimeStamp
	 */
	@Override
	public Long getLocalTimeStamp() {
		return localTimeStamp;
	}

	/**
	 * @param localTimeStamp
	 *           the localTimeStamp to set
	 */
	@Override
	public void setLocalTimeStamp(Long localTimeStamp) {
		this.localTimeStamp = localTimeStamp;
	}

	/**
	 * @return the location
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *           the location to set
	 */
	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the childrenLocation
	 */
	@Override
	public String getChildrenLocation() {
		return childrenLocation;
	}

	/**
	 * @param childrenLocation
	 *           the childrenLocation to set
	 */
	@Override
	public void setChildrenLocation(String childrenLocation) {
		this.childrenLocation = childrenLocation;
	}

	/**
	 * @return the attributes
	 */
	@Override
	public Attributes getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *           the attributes to set
	 */
	@Override
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the charSet
	 */
	@Override
	public String getCharSet() {
		return charSet;
	}

	/**
	 * @param charSet
	 *           the charSet to set
	 */
	@Override
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	/**
	 * @return the contentType
	 */
	@Override
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *           the contentType to set
	 */
	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the contentLegth
	 */
	@Override
	public Long getContentLegth() {
		return contentLegth;
	}

	/**
	 * @param contentLegth
	 *           the contentLegth to set
	 */
	@Override
	public void setContentLegth(Long contentLegth) {
		this.contentLegth = contentLegth;
	}

}
