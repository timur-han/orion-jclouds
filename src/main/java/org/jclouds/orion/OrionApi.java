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

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.blobstore.domain.Blob;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.blobstore.functions.FileDoesNotExistResponseParser;
import org.jclouds.orion.blobstore.functions.FolderMetadataResponseParser;
import org.jclouds.orion.blobstore.functions.OrionBlobParser;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.http.filters.FolderCreationFilter;
import org.jclouds.orion.http.filters.FormAuthentication;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;

import com.google.gson.JsonObject;

/**
 * Provides asynchronous access to Orion via their REST API.
 * <p/>
 * 
 * @see OrionClient
 * @see <a href="TODO: insert URL of provider documentation" />
 * @author Timur Sungur
 */
@RequestFilters({ FormAuthentication.class })
public interface OrionApi extends Closeable {

    public static final String API_VERSION = "0.0.1";

    @HEAD
    @Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/")
    @Headers(keys = { OrionHttpFields.IGNORE_AUTHENTICATION,
	    OrionHttpFields.ORION_VERSION_FIELD }, values = { "",
	    OrionConstantValues.ORION_VERSION })
    HttpResponse checkKeyValidity(
	    @PathParam(value = "userWorkspace") String userName);

    @GET
    @Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/"
	    + OrionConstantValues.ORION_FILE_PATH + "{container}")
    @ResponseParser(FileDoesNotExistResponseParser.class)
    Boolean containerExists(@PathParam("userWorkspace") String userWorkspace,
	    @PathParam("container") String container);

    /**
     * Creates the container as a project in orion
     * 
     * @param userWorkspace
     * @param projectName
     * @return
     */
    @POST
    @Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/")
    Boolean createContainerAsAProject(
	    @PathParam("userWorkspace") String userWorkspace,
	    @HeaderParam("Slug") String projectName);

    @GET
    @Path(OrionConstantValues.ORION_FILE_PATH
	    + "{userWorkspace}/{containerName/}")
    @FormParams(keys = { "depth" }, values = { "1" })
    JsonObject listContainerContents(
	    @PathParam("userWorkspace") String userWorkspace,
	    @PathParam("containerName") String containerName);

    @DELETE
    @Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/"
	    + OrionConstantValues.ORION_FILE_PATH + "{container}")
    @ResponseParser(FileDoesNotExistResponseParser.class)
    Boolean deleteContainer(@PathParam("userWorkspace") String userWorkspace,
	    @PathParam("container") String container);

    @POST
    @Path(OrionConstantValues.ORION_AUTH_PATH)
    @Headers(keys = { OrionHttpFields.IGNORE_AUTHENTICATION,
	    OrionHttpFields.ORION_VERSION_FIELD }, values = { "",
	    OrionConstantValues.ORION_VERSION })
    HttpResponse formLogin(
	    @FormParam(value = OrionHttpFields.USERNAME) String userName,
	    @FormParam(value = OrionHttpFields.PASSWORD) String pass);

    @DELETE
    @Path(OrionConstantValues.ORION_FILE_PATH
	    + "{userWorkspace}/{container}/{pathToFile}")
    @ResponseParser(FileDoesNotExistResponseParser.class)
    void removeFile(@PathParam("pathToFile") String pathToFile,
	    @HeaderParam(value = "Slug") String fileName);

    @POST
    @Path(OrionConstantValues.ORION_FILE_PATH
	    + "{userWorkspace}/{container}/{pathToFile}")
    @ResponseParser(FileDoesNotExistResponseParser.class)
    @R
    void putFile(
	    @PathParam("pathToFile") String userWorkspace,
	    String container,
	    @HeaderParam(value = "Slug") @ParamParser(OrionBlobParser.class) Blob blob);

    @GET
    @Path(OrionConstantValues.ORION_FILE_PATH
	    + "{userWorkspace}/{container}/{pathToFile}")
    @ResponseParser(FolderMetadataResponseParser.class)
    @FormParams(keys = { "type" }, values = { "meta" })
    boolean doesFolderExist(@PathParam("userWorkspace") String container,
	    @PathParam("container") String userLocation,
	    @PathParam("pathToFile") String firstPath);

    @POST
    @Path(OrionConstantValues.ORION_FILE_PATH
	    + "{userName}/{container}/{pathToFile}")
    @RequestFilters(FolderCreationFilter.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
    void createFolder(@PathParam("userName") String userName,
	    @PathParam("container") String container, String parentPath,
	    @HeaderParam("Slag") String name);

}
