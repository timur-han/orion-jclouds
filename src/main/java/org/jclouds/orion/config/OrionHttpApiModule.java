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
package org.jclouds.orion.config;

import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.json.config.GsonModule.DateAdapter;
import org.jclouds.json.config.GsonModule.Iso8601DateAdapter;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.handlers.OrionErrorHandler;
import org.jclouds.rest.ConfiguresHttpApi;
import org.jclouds.rest.config.HttpApiModule;

/**
 * Configures the Orion connection.
 * 
 * @author Timur Sungur
 */
@ConfiguresHttpApi
public class OrionHttpApiModule extends HttpApiModule<OrionApi> {

	// public static final Map<Class<?>, Class<?>> DELEGATE_MAP =
	// ImmutableMap.<Class<?>, Class<?>> builder().put(KeyApi.class,
	// KeyAsyncApi.class).build();

	public OrionHttpApiModule() {

	}

	@Override
	protected void bindErrorHandlers() {
		this.bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(OrionErrorHandler.class);
		this.bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(OrionErrorHandler.class);
		this.bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(OrionErrorHandler.class);
	}

	@Override
	protected void configure() {
		install(new OrionBlobModule());
		install(new OrionCustomModule());
		this.bind(DateAdapter.class).to(Iso8601DateAdapter.class);
		super.configure();

	}

}
