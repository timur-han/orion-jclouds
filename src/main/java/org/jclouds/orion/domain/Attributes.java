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
 * File object attributes
 * 
 * @author Timur
 * 
 */
public interface Attributes {

	/**
	 * @return the readOnly
	 */
	public abstract Boolean getReadOnly();

	/**
	 * @param readOnly
	 *           the readOnly to set
	 */
	public abstract void setReadOnly(Boolean readOnly);

	/**
	 * @return the executable
	 */
	public abstract Boolean getExecutable();

	/**
	 * @param executable
	 *           the executable to set
	 */
	public abstract void setExecutable(Boolean executable);

	/**
	 * @return the hidden
	 */
	public abstract Boolean getHidden();

	/**
	 * @param hidden
	 *           the hidden to set
	 */
	public abstract void setHidden(Boolean hidden);

	/**
	 * @return the archive
	 */
	public abstract Boolean getArchive();

	/**
	 * @param archive
	 *           the archive to set
	 */
	public abstract void setArchive(Boolean archive);

	/**
	 * @return the symbolicLink
	 */
	public abstract Boolean getSymbolicLink();

	/**
	 * @param symbolicLink
	 *           the symbolicLink to set
	 */
	public abstract void setSymbolicLink(Boolean symbolicLink);

}
