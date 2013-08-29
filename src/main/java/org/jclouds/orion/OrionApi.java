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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.http.HttpResponse;
import org.jclouds.orion.blobstore.binders.BlobCreationBinder;
import org.jclouds.orion.blobstore.binders.OrionMetadataBinder;
import org.jclouds.orion.blobstore.functions.BlobMetadataName;
import org.jclouds.orion.blobstore.functions.BlobName;
import org.jclouds.orion.blobstore.functions.CreationResponseParser;
import org.jclouds.orion.blobstore.functions.FileExistsResponseParser;
import org.jclouds.orion.blobstore.functions.FolderMetadataResponseParser;
import org.jclouds.orion.blobstore.functions.MetadataResponseParser;
import org.jclouds.orion.blobstore.validators.ContainerNameValidator;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.orion.http.filters.EmptyDebugFilter;
import org.jclouds.orion.http.filters.FormAuthentication;
import org.jclouds.orion.http.filters.create.CreateFolderFilter;
import org.jclouds.orion.http.filters.create.CreateHiddenFileFilter;
import org.jclouds.orion.http.filters.create.CreateReadonlyFileFilter;
import org.jclouds.orion.http.filters.create.EmptyRequestFilter;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.ParamValidators;
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

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{container}")
	@ResponseParser(FileExistsResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
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
	@ResponseParser(CreationResponseParser.class)
	Boolean createContainerAsAProject(
			@PathParam("userWorkspace") String userWorkspace,
			@HeaderParam("Slug") @ParamValidators(ContainerNameValidator.class) String projectName);

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
	@ResponseParser(FileExistsResponseParser.class)
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
	@ResponseParser(FileExistsResponseParser.class)
	void removeBlob(@PathParam("pathToFile") String pathToFile,
			@HeaderParam(value = OrionHttpFields.HEADER_SLUG) String fileName);

	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}")
	@ResponseParser(CreationResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean createBlob(@PathParam("userWorkspace") String userWorkspace,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath,
			@BinderParam(BlobCreationBinder.class) OrionBlob blob);

	@PUT
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}{blobName}")
	@ResponseParser(FileExistsResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean putBlob(@PathParam("userWorkspace") String userWorkspace,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath,
			@PathParam("blobName") @ParamParser(BlobName.class) OrionBlob blob);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{pathToFile}")
	@ResponseParser(FolderMetadataResponseParser.class)
	@FormParams(keys = { "type" }, values = { "meta" })
	boolean folderExists(@PathParam("userWorkspace") String container,
			@PathParam("container") String userLocation,
			@PathParam("pathToFile") String firstPath);

	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}")
	@RequestFilters({ EmptyRequestFilter.class, CreateFolderFilter.class,
			CreateHiddenFileFilter.class, CreateReadonlyFileFilter.class })
	@Produces(MediaType.APPLICATION_JSON)
	@Headers(keys = { OrionHttpFields.HEADER_SLUG,
			OrionHttpFields.ORION_VERSION_FIELD }, values = {
			OrionConstantValues.ORION_METADATA_PATH,
			OrionConstantValues.ORION_VERSION })
	@ResponseParser(CreationResponseParser.class)
	boolean createMetadataFolder(@PathParam("userWorkspace") String userName,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath);

	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseParser(CreationResponseParser.class)
	@RequestFilters({ EmptyRequestFilter.class, CreateHiddenFileFilter.class,
			CreateReadonlyFileFilter.class })
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean createMetadata(@PathParam("userWorkspace") String userName,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath,
			@HeaderParam(OrionHttpFields.HEADER_SLUG) String fileName);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH)
	@ResponseParser(FolderMetadataResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean metadaFolderExits(@PathParam("userWorkspace") String userName,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath);

	@PUT
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH + "{fileName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseParser(CreationResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean putMetadata(
			@PathParam("userWorkspace") String userName,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath,
			@PathParam("fileName") @ParamParser(BlobMetadataName.class) @BinderParam(OrionMetadataBinder.class) OrionBlob blob);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH + "{fileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseParser(MetadataResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	MutableBlobProperties getMetadata(
			@PathParam("userWorkspace") String userName,
			@PathParam("container") String container,
			@PathParam("parentPath") String parentPath,
			@PathParam("fileName") String blob);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{container}/{path}")
	@ResponseParser(FileExistsResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	@FormParams(keys = { OrionHttpFields.FROM_PARAM_PARTS }, values = { OrionConstantValues.ORION_FILE_METADATA })
	@RequestFilters(EmptyDebugFilter.class)
	boolean blobExits(@PathParam("userWorkspace") String userName,
			@PathParam("container") String container,
			@PathParam("path") String path);

}
