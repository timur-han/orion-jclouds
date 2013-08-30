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
package org.jclouds.orion.config.constans;

/**
 * 
 * @author timur
 * 
 */
public class OrionHttpFields {

	final static public String ORION_VERSION_FIELD = "Orion-Version";

	final static public String HEADER_SLUG = "Slug";

	// Form Parameters

	final static public String USERNAME = "login";
	final static public String PASSWORD = "password";

	// Ignore form authentication header
	// api methods wtih this header key will not be subject to authentication
	// check
	public final static String IGNORE_AUTHENTICATION = "ignoreAuthentication";

	public final static String QUERY_PARTS = "parts";

	// Orion Specific Fields

	public final static String ORION_NAME = "Name";
	public final static String ORION_DIRECTORY = "Directory";
	public final static String ORION_ATTRIBUTES = "Attributes";
	public final static String ORION_CONTENT_TYPE = "ContentType";

	public final static String ORION_ATTRIBUTE_READONLY = "ContentType";
	public final static String ORION_ATTRIBUTE_EXECUTABLE = "Executable";
	public final static String ORION_ATTRIBUTE_HIDDEN = "Hidden";
	public final static String ORION_ATTRIBUTE_ARCHIVE = "Archive";
	public final static String ORION_ATTRIBUTE_SYMLINK = "SymbolicLink";

	public static final String QUERY_DEPTH = "depth";

	public static final String ORION_ECLIPSE_WEB_FIELD = "EclipseWeb-Version";

}
