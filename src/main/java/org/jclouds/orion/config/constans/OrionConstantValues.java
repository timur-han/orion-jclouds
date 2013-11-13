/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jclouds.orion.config.constans;

import java.io.IOException;
import java.util.Properties;

/**
 * Orion related constants
 * 
 * @author timur
 * 
 */
public class OrionConstantValues {

	private static final String PROPS_FILE_NAME = "orion.properties";
	public final static String END_POINT;
	public final static boolean DEBUG_MODE;

	static {
		Properties props = new Properties();
		try {
			props.load(ClassLoader.getSystemResourceAsStream(OrionConstantValues.PROPS_FILE_NAME));
		} catch (IOException e) {
			System.err.println("Property file could not be loaded");
			e.printStackTrace();
		}

		if (props != null) {
			END_POINT = props.getProperty("hostaddress");
			DEBUG_MODE = Boolean.parseBoolean(props.getProperty("debug"));
		} else {
			END_POINT = "https://orionhub.org";
			DEBUG_MODE = false;
		}

	}
	// Orion Blob Store Properties
	public final static String ORION_ID = "orionblob";
	public final static String ORION_VERSION = "1.0";
	// Orion Paths
	public final static String ORION_IMPORT_PATH = "xfer/import/";
	public final static String ORION_EXPORT_PATH = "xfer/export/";
	public final static String ORION_AUTH_PATH = "login/form/";
	public final static String ORION_WORKSPACE_PATH = "workspace/";
	public final static String ORION_FILE_PATH = "file/";

	// Orion metadata data request type
	public final static String ORION_FILE_METADATA = "meta";
	public final static String ORION_FILE_BODY = "body";

	// JSON Constants
	public final static String WORKSPACE_CHILDREN = "children";
	// general identifier field name
	public final static String RESOURCE_IDENTIFIER = "name";

	public final static String PATH_DELIMITER = "/";

	// METADATA FIELDS
	public static final String METADATA_CONTENTDATA = "contentMetadata";
	public static final String ORION_METADATA_PATH = ".metadata/";
	public static final String ORION_METADATA_FILE_NAME = ".metadata";

	// Standard encoding
	public static final String ENCODING = "UTF-8";
	public static final String LIST_CHILDREN = "Children";

	// The depth of requested children
	public static final String MAXIMUM_DEPTH = "10";
	public static final String XFER_RAW = "raw";

}
