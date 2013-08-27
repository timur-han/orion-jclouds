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

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author timur
 * 
 */
public class OrionSpecificFileMetadata implements
	Provider<OrionSpecificFileMetadata> {

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
    private Attributes attributes = new Attributes();
    @JsonProperty("CharSet")
    private String charSet;
    @JsonProperty("ContentType")
    private String contentType;
    @JsonProperty("ContentLength")
    private long contentLegth;
    private final ObjectMapper mapper;

    @Inject
    public OrionSpecificFileMetadata(ObjectMapper mapper) {
	this.mapper = mapper;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the directory
     */
    public Boolean getDirectory() {
	return directory;
    }

    /**
     * @param directory
     *            the directory to set
     */
    public void setDirectory(Boolean directory) {
	this.directory = directory;
    }

    /**
     * @return the eTag
     */
    public String geteTag() {
	return eTag;
    }

    /**
     * @param eTag
     *            the eTag to set
     */
    public void seteTag(String eTag) {
	this.eTag = eTag;
    }

    /**
     * @return the localTimeStamp
     */
    public Long getLocalTimeStamp() {
	return localTimeStamp;
    }

    /**
     * @param localTimeStamp
     *            the localTimeStamp to set
     */
    public void setLocalTimeStamp(Long localTimeStamp) {
	this.localTimeStamp = localTimeStamp;
    }

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the childrenLocation
     */
    public String getChildrenLocation() {
	return childrenLocation;
    }

    /**
     * @param childrenLocation
     *            the childrenLocation to set
     */
    public void setChildrenLocation(String childrenLocation) {
	this.childrenLocation = childrenLocation;
    }

    /**
     * @return the attributes
     */
    public Attributes getAttributes() {
	return attributes;
    }

    /**
     * @param attributes
     *            the attributes to set
     */
    public void setAttributes(Attributes attributes) {
	this.attributes = attributes;
    }

    /**
     * @return the charSet
     */
    public String getCharSet() {
	return charSet;
    }

    /**
     * @param charSet
     *            the charSet to set
     */
    public void setCharSet(String charSet) {
	this.charSet = charSet;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
	return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    /**
     * @return the contentLegth
     */
    public Long getContentLegth() {
	return contentLegth;
    }

    /**
     * @param contentLegth
     *            the contentLegth to set
     */
    public void setContentLegth(Long contentLegth) {
	this.contentLegth = contentLegth;
    }

    public class Attributes {
	@JsonProperty("ReadOnly")
	Boolean readOnly = false;
	@JsonProperty("Exectuable")
	Boolean executable = false;
	@JsonProperty("Hidden")
	Boolean hidden = false;
	@JsonProperty("Archive")
	Boolean archive = false;
	@JsonProperty("SymbolicLink")
	Boolean symbolicLink = false;

	/**
	 * @return the readOnly
	 */
	public Boolean getReadOnly() {
	    return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(Boolean readOnly) {
	    this.readOnly = readOnly;
	}

	/**
	 * @return the executable
	 */
	public Boolean getExecutable() {
	    return executable;
	}

	/**
	 * @param executable
	 *            the executable to set
	 */
	public void setExecutable(Boolean executable) {
	    this.executable = executable;
	}

	/**
	 * @return the hidden
	 */
	public Boolean getHidden() {
	    return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(Boolean hidden) {
	    this.hidden = hidden;
	}

	/**
	 * @return the archive
	 */
	public Boolean getArchive() {
	    return archive;
	}

	/**
	 * @param archive
	 *            the archive to set
	 */
	public void setArchive(Boolean archive) {
	    this.archive = archive;
	}

	/**
	 * @return the symbolicLink
	 */
	public Boolean getSymbolicLink() {
	    return symbolicLink;
	}

	/**
	 * @param symbolicLink
	 *            the symbolicLink to set
	 */
	public void setSymbolicLink(Boolean symbolicLink) {
	    this.symbolicLink = symbolicLink;
	}

    }

    @JsonIgnore
    public String getJSONString() throws JsonGenerationException,
	    JsonMappingException, IOException {
	return mapper.writeValueAsString(this);
    }

    @JsonIgnore
    public OrionSpecificFileMetadata parseObject(String string)
	    throws JsonGenerationException, JsonMappingException, IOException {
	return mapper.readValue(string, OrionSpecificFileMetadata.class);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.inject.Provider#get()
     */
    @Override
    @JsonIgnore
    public OrionSpecificFileMetadata get() {

	return this;
    }

    @JsonCreator
    OrionSpecificFileMetadata getInstance() {
	return new OrionSpecificFileMetadata(mapper);
    }

}
