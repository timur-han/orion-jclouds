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
 * Orion error Java representation
 * 
 * @author Timur
 * 
 */
public interface OrionError {

	/**
	 * @return the httpCode
	 */
	public abstract String getHttpCode();

	/**
	 * @param httpCode
	 *           the httpCode to set
	 */
	public abstract void setHttpCode(String httpCode);

	/**
	 * @return the code
	 */
	public abstract String getCode();

	/**
	 * @param code
	 *           the code to set
	 */
	public abstract void setCode(String code);

	/**
	 * @return the severity
	 */
	public abstract String getSeverity();

	/**
	 * @param severity
	 *           the severity to set
	 */
	public abstract void setSeverity(String severity);

	/**
	 * @return the message
	 */
	public abstract String getMessage();

	/**
	 * @param message
	 *           the message to set
	 */
	public abstract void setMessage(String message);

	/**
	 * @return the detailedMessage
	 */
	public abstract String getDetailedMessage();

	/**
	 * @param detailedMessage
	 *           the detailedMessage to set
	 */
	public abstract void setDetailedMessage(String detailedMessage);

	/**
	 * @return the cause
	 */
	public abstract String getCause();

	/**
	 * @param cause
	 *           the cause to set
	 */
	public abstract void setCause(String cause);

	/**
	 * @return the seeAlso
	 */
	public abstract String getSeeAlso();

	/**
	 * @param seeAlso
	 *           the seeAlso to set
	 */
	public abstract void setSeeAlso(String seeAlso);

}