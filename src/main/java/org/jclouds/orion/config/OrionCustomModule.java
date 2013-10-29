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
package org.jclouds.orion.config;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.jclouds.io.MutableContentMetadata;
import org.jclouds.io.payloads.BaseMutableContentMetadata;
import org.jclouds.orion.domain.Attributes;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionChildMetadata;
import org.jclouds.orion.domain.OrionError;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;
import org.jclouds.orion.domain.OrionStorageMetadata;
import org.jclouds.orion.domain.internal.AttributesImpl;
import org.jclouds.orion.domain.internal.MutableBlobPropertiesImpl;
import org.jclouds.orion.domain.internal.OrionChildMetadataImpl;
import org.jclouds.orion.domain.internal.OrionErrorImpl;
import org.jclouds.orion.domain.internal.OrionSpecificFileMetadataImpl;
import org.jclouds.orion.domain.internal.OrionStorageMetadataImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Configure orion specific settings in this module
 * 
 * @author timur
 * 
 */
public class OrionCustomModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OrionChildMetadata.class).to(OrionChildMetadataImpl.class);
		bind(OrionStorageMetadata.class).to(OrionStorageMetadataImpl.class);
	}

	@Provides
	public ObjectMapper getObjectMapper() {
		// configure objectmapper and provide it
		SimpleModule module = new SimpleModule("SimpleAbstractTypeResolver", Version.unknownVersion());
		module.addAbstractTypeMapping(MutableBlobProperties.class, MutableBlobPropertiesImpl.class);
		module.addAbstractTypeMapping(MutableContentMetadata.class, BaseMutableContentMetadata.class);
		module.addAbstractTypeMapping(OrionSpecificFileMetadata.class, OrionSpecificFileMetadataImpl.class);
		module.addAbstractTypeMapping(Attributes.class, AttributesImpl.class);
		module.addAbstractTypeMapping(OrionError.class, OrionErrorImpl.class);
		module.addAbstractTypeMapping(OrionChildMetadata.class, OrionChildMetadataImpl.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		return mapper;
	}

	@Provides
	OrionSpecificFileMetadata getOrionSpecificFileMetadata() {
		return new OrionSpecificFileMetadataImpl();
	}
}
