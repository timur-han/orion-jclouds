/**
 * Licensed to jclouds, Inc. (jclouds) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. jclouds licenses this file to you
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jclouds.orion;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.net.URI;
import java.util.Properties;

import org.jclouds.Constants;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.orion.blobstore.config.OrionBlobStoreModule;
import org.jclouds.orion.config.OrionHttpApiModule;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.reflect.Reflection2;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * Implementation of {@link ApiMetadata} for Orion 0.0.1 API
 * 
 * @author Timur Sungur
 */
public class OrionApiMetadata extends BaseHttpApiMetadata<OrionApi> {

	public static class Builder extends BaseHttpApiMetadata.Builder<OrionApi, Builder> {

		protected Builder() {
			// super(OrionApi.class);
			this.id(OrionConstantValues.ORION_ID)
			      .name("Orion API")
			      .defaultIdentity("Username")
			      .identityName("Username")
			      .defaultCredential("Password")
			      .credentialName("Password")
			      .documentation(URI.create("http://wiki.eclipse.org/Orion/Server_API"))
			      .version("1.0")
			      .defaultEndpoint(OrionConstantValues.END_POINT)
			      .defaultProperties(OrionApiMetadata.defaultProperties())
			      .view(Reflection2.typeToken(BlobStoreContext.class))
			      .defaultModules(
			            ImmutableSet.<Class<? extends Module>> of(OrionHttpApiModule.class, OrionBlobStoreModule.class));

		}

		@Override
		public OrionApiMetadata build() {
			return new OrionApiMetadata(this);
		}

		@Override
		public Builder fromApiMetadata(ApiMetadata in) {
			super.fromApiMetadata(in);
			return this;
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	public static Properties defaultProperties() {
		Properties properties = BaseHttpApiMetadata.defaultProperties();
		properties.setProperty(Constants.PROPERTY_TIMEOUTS_PREFIX + "default", SECONDS.toMillis(30) + "");
		return properties;
	}

	public OrionApiMetadata() {
		this(new Builder());
	}

	protected OrionApiMetadata(Builder builder) {
		super(builder);
	}

	@Override
	public Builder toBuilder() {
		return new Builder().fromApiMetadata(this);
	}

}
