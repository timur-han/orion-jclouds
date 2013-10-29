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

/**
 * List operation on Orion returns an object with an array of children objects.
 * A child node in this array represented by this object.
 * 
 * @author timur
 * 
 */

public interface OrionChildMetadata {

	/**
	 * @return the childrenLocation
	 */
	public abstract String getChildrenLocation();

	/**
	 * @param childrenLocation
	 *           the childrenLocation to set
	 */
	public abstract void setChildrenLocation(String childrenLocation);

	/**
	 * @return the directory
	 */
	public abstract boolean isDirectory();

	/**
	 * @param directory
	 *           the directory to set
	 */
	public abstract void setDirectory(boolean directory);

	/**
	 * @return the id
	 */
	public abstract String getId();

	/**
	 * @param id
	 *           the id to set
	 */
	public abstract void setId(String id);

	/**
	 * @return the importLocation
	 */
	public abstract String getImportLocation();

	/**
	 * @param importLocation
	 *           the importLocation to set
	 */
	public abstract void setImportLocation(String importLocation);

	/**
	 * @return the location
	 */
	public abstract String getLocation();

	/**
	 * @param location
	 *           the location to set
	 */
	public abstract void setLocation(String location);

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param name
	 *           the name to set
	 */
	public abstract void setName(String name);

	/**
	 * @return the length
	 */
	public abstract Long getLength();

	/**
	 * @param length
	 *           the length to set
	 */
	public abstract void setLength(Long length);

	/**
	 * @return the localTimeStamp
	 */
	public abstract Long getLocalTimeStamp();

	/**
	 * @param localTimeStamp
	 *           the localTimeStamp to set
	 */
	public abstract void setLocalTimeStamp(Long localTimeStamp);

}