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
import javax.ws.rs.FormParam;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jclouds.http.HttpResponse;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.http.filters.FormAuthentication;
import org.jclouds.orion.http.filters.OrionAdditionalHttpFields;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.RequestFilters;

/**
 * Provides asynchronous access to Orion via their REST API.
 * <p/>
 * 
 * @see OrionClient
 * @see <a href="TODO: insert URL of provider documentation" />
 * @author Timur Sungur
 */
@RequestFilters({ OrionAdditionalHttpFields.class, FormAuthentication.class })
public interface OrionApi extends Closeable {

    public static final String API_VERSION = "0.0.1";

    @HEAD
    @Named("CheckKeyValidity")
    @Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userName}/")
    @Headers(keys = { OrionHttpFields.IGNORE_AUTHENTICATION,
	    OrionHttpFields.ORION_VERSION_FIELD }, values = { "",
	    OrionConstantValues.ORION_VERSION })
    HttpResponse checkKeyValidity(@PathParam(value = "userName") String userName);

    @POST
    @Named("CreateContainerFolder")
    @Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/")
    Boolean createContainerAsAProject(
	    @PathParam("userWorkspace") String userWorkspace,
	    @HeaderParam("Slug") String projectName);

    @POST
    @Named("FormAuthentication")
    @Path(OrionConstantValues.ORION_AUTH_PATH)
    @Headers(keys = { OrionHttpFields.IGNORE_AUTHENTICATION,
	    OrionHttpFields.ORION_VERSION_FIELD }, values = { "",
	    OrionConstantValues.ORION_VERSION })
    HttpResponse formLogin(
	    @FormParam(value = OrionHttpFields.USERNAME) String userName,
	    @FormParam(value = OrionHttpFields.PASSWORD) String pass);

}
