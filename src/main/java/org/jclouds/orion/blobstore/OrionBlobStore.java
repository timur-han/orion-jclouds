package org.jclouds.orion.blobstore;

import java.util.Set;

import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.internal.BaseBlobStore;
import org.jclouds.blobstore.options.CreateContainerOptions;
import org.jclouds.blobstore.options.GetOptions;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.blobstore.options.PutOptions;
import org.jclouds.blobstore.util.BlobUtils;
import org.jclouds.collect.Memoized;
import org.jclouds.domain.Credentials;
import org.jclouds.domain.Location;
import org.jclouds.location.Provider;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.blobstore.functions.BlobPropertiesToBlobMetadata;
import org.jclouds.orion.blobstore.functions.BlobToOrionBlob;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Supplier;
import com.google.inject.Inject;

public class OrionBlobStore extends BaseBlobStore {

    private final OrionApi api;
    private final String workspaceName;
    private final BlobToOrionBlob blob2OrionBlob;
    private final BlobPropertiesToBlobMetadata blobProps2BlobMetadata;

    @Inject
    protected OrionBlobStore(BlobStoreContext context,
	    @Provider Supplier<Credentials> creds, BlobUtils blobUtils,
	    OrionApi api, Supplier<Location> defaultLocation,
	    @Memoized Supplier<Set<? extends Location>> locations,
	    BlobToOrionBlob blob2OrionBlob,
	    BlobPropertiesToBlobMetadata blobProps2BlobMetadata) {
	super(context, blobUtils, defaultLocation, locations);
	this.api = api;
	this.workspaceName = creds.get().identity;
	this.blob2OrionBlob = blob2OrionBlob;
	this.blobProps2BlobMetadata = blobProps2BlobMetadata;
    }

    @Override
    public boolean blobExists(String container, String blobName) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    @Override
    public BlobMetadata blobMetadata(String container, String blobName) {
	String parentPath = OrionUtils.fetchParentPath(blobName);
	// Remove existing parent path from the blob name
	// Convert the blob name to it's metadata file name and fetch it
	return blobProps2BlobMetadata.apply(api.getMetadata(getUserLocation(),
		container, parentPath,
		OrionUtils.getMetadataFileName(blobName.split(parentPath)[1])));
    }

    @Override
    public boolean containerExists(String container) {
	return api.containerExists(getUserLocation(), container);
    }

    @Override
    public void deleteContainer(String container) {
	api.deleteContainer(getUserLocation(), container);
    }

    @Override
    public boolean createContainerInLocation(Location location, String container) {
	return this.api.createContainerAsAProject(getUserLocation(), container);
    }

    @Override
    public boolean createContainerInLocation(Location arg0, String arg1,
	    CreateContainerOptions arg2) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    @Override
    protected boolean deleteAndVerifyContainerGone(String container) {
	return api.deleteContainer(getUserLocation(), container);
    }

    @Override
    public Blob getBlob(String arg0, String arg1, GetOptions arg2) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    private String getUserLocation() {
	return this.workspaceName;
    }

    @Override
    public PageSet<? extends StorageMetadata> list() {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    @Override
    public PageSet<? extends StorageMetadata> list(String arg0,
	    ListContainerOptions arg1) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    @Override
    public String putBlob(String container, Blob blob) {
	this.addFile(container, blob);
	return null;
    }

    @Override
    public String putBlob(String arg0, Blob arg1, PutOptions arg2) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    @Override
    public void removeBlob(String arg0, String arg1) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    private boolean addFile(String container, OrionBlob blob) {
	createPathRecursively(container, blob.getProperties().getParentPath()
		.split(OrionConstantValues.PATH_DELIMITER));
	return createBlobFile(container, blob)
		&& createMetadata(container, blob);

    }

    private boolean createMetadata(String container, OrionBlob blob) {

	return api.createMetadataFolder(getUserLocation(), container, blob
		.getProperties().getParentPath())
		&&
		// Create metadata file
		api.createMetadata(getUserLocation(), container, blob
			.getProperties().getParentPath(), OrionUtils
			.getMetadataFileName(blob.getProperties().getName())) &&
		// Add metadata file contents
		api.putMetadata(getUserLocation(), container, blob
			.getProperties().getParentPath(), blob);

    }

    private boolean createBlobFile(String container, OrionBlob blob) {
	return api.createBlob(getUserLocation(), container, blob
		.getProperties().getParentPath(), blob.getProperties()
		.getName())
		&&
		// Add file contents
		api.putBlob(getUserLocation(), container, blob.getProperties()
			.getParentPath(), blob);

    }

    private boolean addFile(String container, Blob blob) {
	return addFile(container, blob2OrionBlob.apply(blob));
    }

    /**
     * Create the non existing paths starting from index 0
     * 
     * @param container
     * @param parentPaths
     */
    private void createPathRecursively(String container, String[] parentPaths) {
	String currentPath = "";
	int startIndex = 0;
	for (String path : parentPaths) {
	    if (!api.folderExists(getUserLocation(), container, path)) {
		break;
	    }
	    startIndex++;
	    currentPath = currentPath + path;
	}
	for (; startIndex < parentPaths.length; startIndex++) {
	    api.createFolder(getUserLocation(), container, currentPath,
		    parentPaths[startIndex]);
	    currentPath = currentPath + parentPaths[startIndex]
		    + OrionConstantValues.PATH_DELIMITER;
	}
    }
}
