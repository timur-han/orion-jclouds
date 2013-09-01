package org.jclouds.orion.blobstore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.blobstore.internal.BaseBlobStore;
import org.jclouds.blobstore.options.CreateContainerOptions;
import org.jclouds.blobstore.options.GetOptions;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.blobstore.options.PutOptions;
import org.jclouds.blobstore.util.BlobUtils;
import org.jclouds.collect.Memoized;
import org.jclouds.domain.Credentials;
import org.jclouds.domain.Location;
import org.jclouds.io.MutableContentMetadata;
import org.jclouds.location.Provider;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.blobstore.functions.BlobPropertiesToBlobMetadata;
import org.jclouds.orion.blobstore.functions.BlobToOrionBlob;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.domain.BlobType;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

public class OrionBlobStore extends BaseBlobStore {

	private final OrionApi api;
	private final String userWorkspace;
	private final BlobToOrionBlob blob2OrionBlob;
	private final BlobPropertiesToBlobMetadata blobProps2BlobMetadata;
	private final BlobUtils blobUtils;

	@Inject
	protected OrionBlobStore(BlobStoreContext context,
			@Provider Supplier<Credentials> creds, BlobUtils blobUtils,
			OrionApi api, Supplier<Location> defaultLocation,
			@Memoized Supplier<Set<? extends Location>> locations,
			BlobToOrionBlob blob2OrionBlob,
			BlobPropertiesToBlobMetadata blobProps2BlobMetadata,
			OrionBlob.Factory orionBlobProvider) {
		super(context, blobUtils, defaultLocation, locations);
		this.blobUtils = blobUtils;
		this.api = Preconditions.checkNotNull(api, "api is null");
		this.userWorkspace = Preconditions.checkNotNull(creds.get(),
				"creds is null").identity;
		this.blob2OrionBlob = Preconditions.checkNotNull(blob2OrionBlob,
				"blob2OrionBlob is null");
		this.blobProps2BlobMetadata = Preconditions.checkNotNull(
				blobProps2BlobMetadata, "blobProps2BlobMetadata is null");

	}

	@Override
	public boolean blobExists(String container, String blobName) {
		return api.blobExists(getUserWorkspace(), container,
				OrionUtils.getParentPath(blobName),
				OrionUtils.getName(blobName));
	}

	@Override
	public BlobMetadata blobMetadata(String container, String blobName) {
		String parentPath = OrionUtils.getParentPath(blobName);
		// Blob names must NOT start with a "/" since they are relative paths
		// they will be automatically removed in case it starts with that
		// Get the blob name
		// Convert the blob name to it's metadata file name and fetch it
		return blobProps2BlobMetadata.apply(api.getMetadata(getUserWorkspace(),
				container, parentPath,
				OrionUtils.getMetadataName(OrionUtils.getName(blobName))));
	}

	@Override
	public boolean containerExists(String container) {
		return api.containerExists(getUserWorkspace(), container);
	}

	@Override
	public void deleteContainer(String container) {
		// api.deleteContainer(getUserLocation(), container);
		api.deleteContainerMetadata(getUserWorkspace(), container);
	}

	@Override
	public boolean createContainerInLocation(Location location, String container) {
		return this.api
				.createContainerAsAProject(getUserWorkspace(), container);
	}

	@Override
	public boolean createContainerInLocation(Location arg0, String arg1,
			CreateContainerOptions arg2) {
		return this.createContainerInLocation(arg0, arg1);
	}

	@Override
	protected boolean deleteAndVerifyContainerGone(String container) {
		return api.deleteContainerMetadata(getUserWorkspace(), container);
	}

	@Override
	public Blob getBlob(String container, String blob, GetOptions arg2) {
		return api.getBlob(getUserWorkspace(), container,
				OrionUtils.getParentPath(blob), OrionUtils.getName(blob));
	}

	private String getUserWorkspace() {
		return this.userWorkspace;
	}

	@Override
	public PageSet<? extends StorageMetadata> list() {
		return api.listContainers(getUserWorkspace());
	}

	@Override
	public PageSet<? extends StorageMetadata> list(String container,
			ListContainerOptions options) {
		return api.list(getUserWorkspace(), container, options);
	}

	@Override
	public String putBlob(String container, Blob blob) {
		OrionBlob orionBlob = this.blob2OrionBlob.apply(blob);
		MutableContentMetadata tempMD = orionBlob.getProperties()
				.getContentMetadata();
		// Copy temporarily the inputstream otherwise JVM closes the stream
		ByteArrayOutputStream tempOutputStream = new ByteArrayOutputStream();
		try {
			IOUtils.copy(blob.getPayload().getInput(), tempOutputStream);
			orionBlob.setPayload(tempOutputStream.toByteArray());
			orionBlob.getProperties().setContentMetadata(tempMD);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> pathList = new ArrayList<String>(
				Arrays.asList(orionBlob.getProperties().getParentPath()
						.split(OrionConstantValues.PATH_DELIMITER)));
		createParentPaths(container, pathList);
		insertBlob(container, orionBlob);
		return null;
	}

	@Override
	public String putBlob(String container, Blob blob, PutOptions arg2) {
		return putBlob(container, blob);
	}

	@Override
	public void removeBlob(String container, String blobName) {
		api.removeBlob(getUserWorkspace(), container,
				OrionUtils.getParentPath(blobName),
				OrionUtils.getName(blobName));
	}

	/**
	 * insert blob operations 1. create a blob file 2. put blob content 3.
	 * create metadata
	 * 
	 * @param container
	 * @param orionBlob
	 * @return
	 */
	private void insertBlob(String container, OrionBlob orionBlob) {
		orionBlob.getProperties().setContainer(container);
		boolean creationFlag = api.createBlob(getUserWorkspace(), container,
				orionBlob.getProperties().getParentPath(), orionBlob);
		if (creationFlag) {
			if (orionBlob.getProperties().getType() == BlobType.FILE_BLOB) {
				api.putBlob(getUserWorkspace(), container, orionBlob
						.getProperties().getParentPath(), orionBlob);
			}
			if (!createMetadata(container, orionBlob)) {
				System.err
						.println("metadata could not be created blob will be removed");
				api.removeBlob(getUserWorkspace(), container, orionBlob
						.getProperties().getParentPath(), orionBlob
						.getProperties().getName());

			}
		} else {
			System.err.println("blob could not be created");
		}

	}

	private boolean createMetadata(String container, OrionBlob blob) {

		return api.createMetadataFolder(getUserWorkspace(), container, blob
				.getProperties().getParentPath())
				&&
				// Create metadata file
				api.createMetadata(getUserWorkspace(), container, blob
						.getProperties().getParentPath(), OrionUtils
						.getMetadataName(blob.getProperties().getName())) &&
				// Add metadata file contents
				api.putMetadata(getUserWorkspace(), container, blob
						.getProperties().getParentPath(), blob);

	}

	/**
	 * Create the non existing paths starting from index 0
	 * 
	 * @param containerName
	 * @param pathArray
	 */
	private void createParentPaths(String containerName, List<String> pathArray) {
		String parentPath = "";
		for (String path : pathArray) {
			if (!api.blobExists(getUserWorkspace(), containerName, parentPath,
					path)) {
				insertBlob(
						containerName,
						blob2OrionBlob.apply(blobUtils.blobBuilder()
								.payload("").name(parentPath + path)
								.type(StorageType.FOLDER).build()));

			}
			parentPath = parentPath + path + OrionConstantValues.PATH_DELIMITER;
		}

	}

}
