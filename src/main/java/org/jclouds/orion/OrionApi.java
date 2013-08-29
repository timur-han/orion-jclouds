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

import org.jclouds.blobstore.domain.Blob;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.blobstore.binders.BlobCreationBinder;
import org.jclouds.orion.blobstore.binders.OrionMetadataBinder;
import org.jclouds.orion.blobstore.fallbacks.DuplicateCreationFallback;
import org.jclouds.orion.blobstore.fallbacks.ExistenceCheckFallback;
import org.jclouds.orion.blobstore.fallbacks.SameFileWithDiffTypeFallback;
import org.jclouds.orion.blobstore.functions.BlobMetadataName;
import org.jclouds.orion.blobstore.functions.BlobName;
import org.jclouds.orion.blobstore.functions.BlobNameToMetadataNameParser;
import org.jclouds.orion.blobstore.functions.BlobRequestParser;
import org.jclouds.orion.blobstore.functions.CreationResponseParser;
import org.jclouds.orion.blobstore.functions.FileExistsResponseParser;
import org.jclouds.orion.blobstore.functions.FixBlobName;
import org.jclouds.orion.blobstore.functions.FixBlobParentPath;
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
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.FormParams;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.ParamValidators;
import org.jclouds.rest.annotations.QueryParams;
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
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}")
	@Fallback(ExistenceCheckFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	@QueryParams(keys = { OrionHttpFields.FROM_PARAM_PARTS }, values = { OrionConstantValues.ORION_FILE_METADATA })
	boolean containerExists(@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName);

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
	@Fallback(DuplicateCreationFallback.class)
	Boolean createContainerAsAProject(
			@PathParam("userWorkspace") String userWorkspace,
			@HeaderParam("Slug") @ParamValidators(ContainerNameValidator.class) String containerName);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName/}")
	@FormParams(keys = { "depth" }, values = { "1" })
	JsonObject listContainerContents(
			@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName);

	@DELETE
	@Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/"
			+ OrionConstantValues.ORION_FILE_PATH + "{containerName}")
	@Fallback(ExistenceCheckFallback.class)
	Boolean deleteContainerMetadata(
			@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName);

	@DELETE
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/"
			+ "{containerName}")
	@ResponseParser(FileExistsResponseParser.class)
	Boolean deleteContainer(@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName);

	@POST
	@Path(OrionConstantValues.ORION_AUTH_PATH)
	@Headers(keys = { OrionHttpFields.IGNORE_AUTHENTICATION,
			OrionHttpFields.ORION_VERSION_FIELD }, values = { "",
			OrionConstantValues.ORION_VERSION })
	HttpResponse formLogin(
			@FormParam(value = OrionHttpFields.USERNAME) String userName,
			@FormParam(value = OrionHttpFields.PASSWORD) String pass);

	// Normally only metadata needs to be fetched however
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}{blobName}")
	@Fallback(ExistenceCheckFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	@QueryParams(keys = { OrionHttpFields.FROM_PARAM_PARTS }, values = { OrionConstantValues.ORION_FILE_METADATA })
	@RequestFilters(EmptyDebugFilter.class)
	boolean blobExits(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("blobName") @ParamParser(FixBlobName.class) String blobName);

	@DELETE
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}")
	@ResponseParser(FileExistsResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean removeBlob(
			@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("blobName") @ParamParser(FixBlobName.class) String blobName);

	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}")
	@Fallback(SameFileWithDiffTypeFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean createBlob(
			@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@BinderParam(BlobCreationBinder.class) OrionBlob blob);

	@PUT
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}{blobName}")
	@ResponseParser(FileExistsResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean putBlob(
			@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("blobName") @ParamParser(BlobName.class) OrionBlob blob);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH + "{blobName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseParser(BlobRequestParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	Blob getBlob(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("blobName") @ParamParser(BlobNameToMetadataNameParser.class) String blobName);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}{blobName}")
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	HttpResponse getBlobContents(
			@PathParam("userWorkspace") String userWorkspace,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("blobName") @ParamParser(FixBlobName.class) String blob);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}{blobName}")
	@Fallback(ExistenceCheckFallback.class)
	@FormParams(keys = { "type" }, values = { "meta" })
	boolean folderExists(
			@PathParam("userWorkspace") String containerName,
			@PathParam("containerName") String userLocation,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String firstPath,
			@PathParam("blobName") @ParamParser(FixBlobName.class) String blobName);

	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}")
	@RequestFilters({ EmptyRequestFilter.class, CreateFolderFilter.class,
			CreateHiddenFileFilter.class, CreateReadonlyFileFilter.class })
	@Produces(MediaType.APPLICATION_JSON)
	@Headers(keys = { OrionHttpFields.HEADER_SLUG,
			OrionHttpFields.ORION_VERSION_FIELD }, values = {
			OrionConstantValues.ORION_METADATA_FILE_NAME,
			OrionConstantValues.ORION_VERSION })
	@ResponseParser(CreationResponseParser.class)
	boolean createMetadataFolder(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath);

	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_FILE_NAME)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseParser(CreationResponseParser.class)
	@RequestFilters({ EmptyRequestFilter.class, CreateHiddenFileFilter.class,
			CreateReadonlyFileFilter.class })
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean createMetadata(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@HeaderParam(OrionHttpFields.HEADER_SLUG) String fileName);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_FILE_NAME)
	@Fallback(ExistenceCheckFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean metadaFolderExits(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath);

	@PUT
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH + "{fileName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseParser(CreationResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean putMetadata(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("fileName") @ParamParser(BlobMetadataName.class) @BinderParam(OrionMetadataBinder.class) OrionBlob blob);

	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH
			+ "{userWorkspace}/{containerName}/{parentPath}"
			+ OrionConstantValues.ORION_METADATA_PATH + "{fileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseParser(MetadataResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	MutableBlobProperties getMetadata(
			@PathParam("userWorkspace") String userName,
			@PathParam("containerName") String containerName,
			@PathParam("parentPath") @ParamParser(FixBlobParentPath.class) String parentPath,
			@PathParam("fileName") @ParamParser(FixBlobName.class) String blobName);

	/**
	 * @param endpoint
	 */
	@DELETE
	@Path("{path}")
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean deleteGivenPath(@PathParam("path") String endpoint);

}
