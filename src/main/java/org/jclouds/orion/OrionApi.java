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
import java.util.List;

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
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.blobstore.binders.BlobCreationBinder;
import org.jclouds.orion.blobstore.binders.ListRequestBinder;
import org.jclouds.orion.blobstore.binders.OrionMetadataBinder;
import org.jclouds.orion.blobstore.fallbacks.DuplicateCreationFallback;
import org.jclouds.orion.blobstore.fallbacks.FileNotFoundFallback;
import org.jclouds.orion.blobstore.fallbacks.ReturnNullOnNotFound;
import org.jclouds.orion.blobstore.fallbacks.SameFileWithDiffTypeFallback;
import org.jclouds.orion.blobstore.functions.parsers.param.BlobMetadataNameParamParser;
import org.jclouds.orion.blobstore.functions.parsers.param.BlobNameToMetadataNameParamParser;
import org.jclouds.orion.blobstore.functions.parsers.param.EncodeBlobNameParamParser;
import org.jclouds.orion.blobstore.functions.parsers.param.EncodeBlobParentPathParamParser;
import org.jclouds.orion.blobstore.functions.parsers.response.BlobResponseParser;
import org.jclouds.orion.blobstore.functions.parsers.response.CreationResponseParser;
import org.jclouds.orion.blobstore.functions.parsers.response.FileExistsResponseParser;
import org.jclouds.orion.blobstore.functions.parsers.response.FolderListResposeParser;
import org.jclouds.orion.blobstore.functions.parsers.response.ListContainersResponseParser;
import org.jclouds.orion.blobstore.functions.parsers.response.MetadataResponseParser;
import org.jclouds.orion.blobstore.validators.BlobNameValidator;
import org.jclouds.orion.blobstore.validators.StringNameValidator;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.orion.domain.OrionChildMetadata;
import org.jclouds.orion.http.filters.FormAuthentication;
import org.jclouds.orion.http.filters.create.CreateFileNameFilter;
import org.jclouds.orion.http.filters.create.CreateFolderFilter;
import org.jclouds.orion.http.filters.create.CreateHiddenFileFilter;
import org.jclouds.orion.http.filters.create.CreateReadonlyFileFilter;
import org.jclouds.orion.http.filters.create.EmptyRequestFilter;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.ParamValidators;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;

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

	/**
	 * Check if container exists
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}")
	@Fallback(FileNotFoundFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	@QueryParams(keys = { OrionHttpFields.QUERY_PARTS }, values = { OrionConstantValues.ORION_FILE_METADATA })
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
	@Headers(keys = { OrionHttpFields.ORION_ECLIPSE_WEB_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	Boolean createContainerAsAProject(@PathParam("userWorkspace") String userWorkspace,
	      @HeaderParam("Slug") @ParamValidators(StringNameValidator.class) String containerName);

	/**
	 * List existing containers for the user
	 * 
	 * @param userWorkspace
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/")
	@ResponseParser(ListContainersResponseParser.class)
	@QueryParams(keys = { "depth" }, values = { "1" })
	@Headers(keys = { OrionHttpFields.ORION_ECLIPSE_WEB_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	PageSet<? extends StorageMetadata> listContainers(@PathParam("userWorkspace") String userWorkspace);

	/**
	 * List contents of a specifc container
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/")
	@ResponseParser(FolderListResposeParser.class)
	@QueryParams(keys = { "depth" }, values = { "1" })
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	List<OrionChildMetadata> listContainerContents(@PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String containerName);

	/**
	 * Delete project from workspace API
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @return
	 */
	@DELETE
	@Path(OrionConstantValues.ORION_WORKSPACE_PATH + "{userWorkspace}/" + OrionConstantValues.ORION_FILE_PATH
	      + "{containerName}")
	@Fallback(FileNotFoundFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_ECLIPSE_WEB_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	Boolean deleteContainerMetadata(@PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String containerName);

	/**
	 * Delete project from file API
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @return
	 */
	@DELETE
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/" + "{containerName}")
	@ResponseParser(FileExistsResponseParser.class)
	Boolean deleteContainer(@PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String containerName);

	/**
	 * Form authentication request
	 * 
	 * @param userName
	 * @param pass
	 * @return
	 */
	@POST
	@Path(OrionConstantValues.ORION_AUTH_PATH)
	@Headers(keys = { OrionHttpFields.IGNORE_AUTHENTICATION, OrionHttpFields.ORION_VERSION_FIELD }, values = { "",
	      OrionConstantValues.ORION_VERSION })
	HttpResponse formLogin(@FormParam(value = OrionHttpFields.USERNAME) String userName,
	      @FormParam(value = OrionHttpFields.PASSWORD) String pass);

	// Normally only metadata needs to be fetched however
	/**
	 * Check if a blob exists
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @param blobName
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}{blobName}")
	@Fallback(FileNotFoundFallback.class)
	@QueryParams(keys = { OrionHttpFields.QUERY_PARTS }, values = { OrionConstantValues.ORION_FILE_METADATA })
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean blobExists(@PathParam("userWorkspace") String userName, @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @PathParam("blobName") @ParamParser(EncodeBlobNameParamParser.class) String blobName);

	/**
	 * Remove a blob. No "/" in the name and .metadata as name are possible.
	 * 
	 * @see EncodeBlobNameParamParser is used.
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @param parentPath
	 * @param blobName
	 * @return
	 */
	@DELETE
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}")
	@Fallback(FileNotFoundFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean removeBlob(
	      @PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @PathParam("blobName") @ParamParser(EncodeBlobNameParamParser.class) @ParamValidators(StringNameValidator.class) String blobName);

	/**
	 * This method creates an empty file/folder depending on the request
	 * parameters.
	 * 
	 * If a file/folder with the same name exists, it is removed and replaced
	 * with the requested type.
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @param parentPath
	 * @param blob
	 * @return
	 */
	// TODO creation should be removed from SLUG header to json
	@POST
	@Path(OrionConstantValues.ORION_IMPORT_PATH + "{userWorkspace}/{containerName}/{parentPath}")
	@Fallback(SameFileWithDiffTypeFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD, OrionHttpFields.ORION_XFER_OPTIONS }, values = {
	      OrionConstantValues.ORION_VERSION, OrionConstantValues.XFER_RAW })
	boolean createBlob(@PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @BinderParam(BlobCreationBinder.class) @ParamValidators(value = { BlobNameValidator.class }) OrionBlob blob);

	/**
	 * Create a metadata folder to the corresponding path
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @return
	 */
	// TODO creation should be removed from SLUG header to json
	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}")
	@RequestFilters({ EmptyRequestFilter.class, CreateFolderFilter.class, CreateHiddenFileFilter.class,
	      CreateReadonlyFileFilter.class, CreateFileNameFilter.class })
	@Produces(MediaType.APPLICATION_JSON)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	@ResponseParser(CreationResponseParser.class)
	@Fallback(SameFileWithDiffTypeFallback.class)
	boolean createFolder(@PathParam("userWorkspace") String userName, @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @HeaderParam(OrionHttpFields.HEADER_SLUG) String blobName);

	/**
	 * Get the blob on the path. This starts with a metadata request and then
	 * file content is requested if the metadata could be fetched successfully.
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @param blobName
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}"
	      + OrionConstantValues.ORION_METADATA_PATH + "{blobName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseParser(BlobResponseParser.class)
	@Fallback(ReturnNullOnNotFound.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	Blob getBlob(
	      @PathParam("userWorkspace") String userName,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @PathParam("blobName") @ParamParser(BlobNameToMetadataNameParamParser.class) @ParamValidators(StringNameValidator.class) String blobName);

	/**
	 * This method is used to get the blob contents
	 * 
	 * @param userWorkspace
	 * @param containerName
	 * @param parentPath
	 * @param blob
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}{blobName}")
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	HttpResponse getBlobContents(@PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @PathParam("blobName") @ParamParser(EncodeBlobNameParamParser.class) String blob);

	/**
	 * Create a metadata folder to the corresponding path
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @return
	 */
	// TODO creation should be removed from SLUG header to json
	@POST
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}")
	@RequestFilters({ EmptyRequestFilter.class, CreateFolderFilter.class, CreateHiddenFileFilter.class,
	      CreateReadonlyFileFilter.class })
	@Produces(MediaType.APPLICATION_JSON)
	@Headers(keys = { OrionHttpFields.HEADER_SLUG, OrionHttpFields.ORION_VERSION_FIELD }, values = {
	      OrionConstantValues.ORION_METADATA_FILE_NAME, OrionConstantValues.ORION_VERSION })
	@ResponseParser(CreationResponseParser.class)
	boolean createMetadataFolder(@PathParam("userWorkspace") String userName,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath);

	/**
	 * Create an empty metadata file.
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @param fileName
	 * @return
	 */
	// TODO creation should be removed from SLUG header to json
	@POST
	@Path(OrionConstantValues.ORION_IMPORT_PATH + "{userWorkspace}/{containerName}/{parentPath}"
	      + OrionConstantValues.ORION_METADATA_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseParser(CreationResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD, OrionHttpFields.ORION_XFER_OPTIONS }, values = {
	      OrionConstantValues.ORION_VERSION, OrionConstantValues.XFER_RAW })
	boolean createMetadata(@PathParam("userWorkspace") String userName,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @BinderParam(OrionMetadataBinder.class) OrionBlob fileName);

	/**
	 * Check if the metadata folder exists
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}"
	      + OrionConstantValues.ORION_METADATA_FILE_NAME)
	@Fallback(FileNotFoundFallback.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean metadaFolderExits(@PathParam("userWorkspace") String userName,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath);

	/**
	 * Put metadata file contents.
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @param blob
	 * @return
	 */
	@PUT
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}"
	      + OrionConstantValues.ORION_METADATA_PATH + "{fileName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseParser(CreationResponseParser.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean putMetadata(
	      @PathParam("userWorkspace") String userName,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @PathParam("fileName") @ParamParser(BlobMetadataNameParamParser.class) @BinderParam(OrionMetadataBinder.class) OrionBlob blob);

	/**
	 * Return metadata object.
	 * 
	 * @param userName
	 * @param containerName
	 * @param parentPath
	 * @param blobName
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}/{parentPath}"
	      + OrionConstantValues.ORION_METADATA_PATH + "{fileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ResponseParser(MetadataResponseParser.class)
	@Fallback(ReturnNullOnNotFound.class)
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	MutableBlobProperties getMetadata(@PathParam("userWorkspace") String userName,
	      @PathParam("containerName") String containerName,
	      @PathParam("parentPath") @ParamParser(EncodeBlobParentPathParamParser.class) String parentPath,
	      @PathParam("fileName") @ParamParser(EncodeBlobNameParamParser.class) String blobName);

	/**
	 * This method sends a DELETE message to the given path
	 * 
	 * @param endpoint
	 */
	@DELETE
	@Path("{path}")
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	boolean deleteGivenPath(@PathParam("path") String endpoint);

	/**
	 * 
	 * List blobs with respect to corresponding options. Detail is not supported.
	 * Recursive means till 10th level in the file hierarchy and if it's false
	 * only the first level is returned.
	 * 
	 * @param userWorkspace
	 * @param container
	 * @param options
	 * @return
	 */
	@GET
	@Path(OrionConstantValues.ORION_FILE_PATH + "{userWorkspace}/{containerName}")
	@ResponseParser(ListContainersResponseParser.class)
	@QueryParams(keys = { "depth" }, values = { "1" })
	@Headers(keys = { OrionHttpFields.ORION_VERSION_FIELD }, values = { OrionConstantValues.ORION_VERSION })
	PageSet<? extends StorageMetadata> list(@PathParam("userWorkspace") String userWorkspace,
	      @PathParam("containerName") String container,
	      @BinderParam(ListRequestBinder.class) ListContainerOptions options);

}
