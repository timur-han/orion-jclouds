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
import org.jclouds.domain.Location;
import org.jclouds.orion.OrionApi;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

public class OrionBlobStore extends BaseBlobStore {
	
	private final OrionApi api;
	
	
	@Inject
	protected OrionBlobStore(BlobStoreContext context, BlobUtils blobUtils, OrionApi api, Supplier<Location> defaultLocation,@Memoized  Supplier<Set<? extends Location>> locations) {
		super(context, blobUtils, defaultLocation, locations);
		this.api = api;
	}
	
	@Override
	public boolean createContainerInLocation(Location location, String container) {
		return this.api.createContainer(container);
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
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public boolean createContainerInLocation(Location arg0, String arg1, CreateContainerOptions arg2) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public Blob getBlob(String arg0, String arg1, GetOptions arg2) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public PageSet<? extends StorageMetadata> list() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public PageSet<? extends StorageMetadata> list(String arg0, ListContainerOptions arg1) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public String putBlob(String arg0, Blob arg1) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
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
	
	@Override
	protected boolean deleteAndVerifyContainerGone(String arg0) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
}
