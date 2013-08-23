package org.jclouds.orion.blobstore;

import java.util.List;
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

import com.google.common.base.Supplier;
import com.google.inject.Inject;

public class OrionBlobStore extends BaseBlobStore {

    private final OrionApi api;
    private final String workspaceName;

    @Inject
    protected OrionBlobStore(BlobStoreContext context,
	    @Provider Supplier<Credentials> creds, BlobUtils blobUtils,
	    OrionApi api, Supplier<Location> defaultLocation,
	    @Memoized Supplier<Set<? extends Location>> locations) {
	super(context, blobUtils, defaultLocation, locations);
	this.api = api;
	this.workspaceName = creds.get().identity;
    }

    @Override
    public boolean blobExists(String container, String blobName) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
    }

    @Override
    public BlobMetadata blobMetadata(String container, String blobName) {
	// TODO Auto-generated method stub
	throw new IllegalStateException("Not yet implemented.");
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

	api.putFile(getUserLocation(), container, blob);
	return null;
    }

    private String createPathRecursively(String container,
	    List<String> differentPaths) {

	String currentPath = "";
	int startIndex = 0;
	for (String path : differentPaths) {
	    if (!api.doesFolderExist(getUserLocation(), container, path)) {
		break;
	    }
	    startIndex++;
	    currentPath = currentPath + path;
	}
	for (; startIndex < (differentPaths.size()); startIndex++) {
	    api.createFolder(getUserLocation(), container, currentPath,
		    differentPaths.get(startIndex));
	    currentPath = currentPath + differentPaths.get(startIndex);
	}
	return currentPath;

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

}
