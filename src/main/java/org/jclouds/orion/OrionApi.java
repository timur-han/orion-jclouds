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

import java.io.Closeable;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.EmptySetOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.Fallbacks.VoidOnNotFoundOr404;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.ParamValidators;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;

import com.google.common.net.HttpHeaders;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Orion via their REST API.
 * <p/>
 * 
 * @see OrionClient
 * @see <a href="TODO: insert URL of provider documentation" />
 * @author Timur Sungur
 */
@RequestFilters(BasicAuthentication.class)
public interface OrionApi extends Closeable {
	
	public static final String API_VERSION = "0.0.1";
	
	
	
	/**
	 * @see OrionClient#list()
	 */

    @Named("CreateContainerFolder")
    @POST
    @Path("file/")
    @Fallback(VoidOnNotFoundOr404.class)
    Boolean createContainerAsAFolder(@HeaderParam("Slug")  String containerName);
	
}
