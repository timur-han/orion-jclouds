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

import javax.inject.Inject;
import javax.inject.Provider;

import org.jclouds.blobstore.config.BlobStoreObjectModule;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.orion.domain.internal.OrionBlobImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * 
 * @author timur
 * 
 */
public class OrionBlobModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new BlobStoreObjectModule());
		bind(OrionBlob.Factory.class).to(OrionBlobFactory.class).in(Scopes.SINGLETON);

	}

	private static class OrionBlobFactory implements OrionBlob.Factory {
		@Inject
		Provider<MutableBlobProperties> metadataProvider;

		@Override
		public OrionBlob create(MutableBlobProperties metadata) {
			return new OrionBlobImpl(metadata != null ? metadata : metadataProvider.get());
		}
	}

}
