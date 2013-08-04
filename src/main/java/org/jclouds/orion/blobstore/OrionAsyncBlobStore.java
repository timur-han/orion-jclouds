package org.jclouds.orion.blobstore;

import java.util.Set;

import org.jclouds.Constants;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.internal.BaseAsyncBlobStore;
import org.jclouds.blobstore.options.CreateContainerOptions;
import org.jclouds.blobstore.options.GetOptions;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.blobstore.options.PutOptions;
import org.jclouds.blobstore.util.BlobUtils;
import org.jclouds.domain.Location;
import org.jclouds.orion.OrionApi;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class OrionAsyncBlobStore extends BaseAsyncBlobStore {
	
	private final OrionApi api;
	
	
	@Inject
	protected OrionAsyncBlobStore(BlobStoreContext context, BlobUtils blobUtils, Supplier<Location> defaultLocation, Supplier<Set<? extends Location>> locations, OrionApi api, @Named(Constants.PROPERTY_USER_THREADS) ListeningExecutorService userExecutor) {
		super(context, blobUtils, userExecutor, defaultLocation, locations);
		this.api = api;
	}
	
	@Override
	public ListenableFuture<PageSet<? extends StorageMetadata>> list() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<Boolean> containerExists(String container) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<Boolean> createContainerInLocation(Location location, String container) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<Boolean> createContainerInLocation(Location location, String container, CreateContainerOptions options) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<PageSet<? extends StorageMetadata>> list(String container, ListContainerOptions options) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<Boolean> blobExists(String container, String name) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<String> putBlob(String container, Blob blob) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<String> putBlob(String container, Blob blob, PutOptions options) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<BlobMetadata> blobMetadata(String container, String key) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<Blob> getBlob(String container, String key, GetOptions options) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	public ListenableFuture<Void> removeBlob(String container, String key) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
	@Override
	protected boolean deleteAndVerifyContainerGone(String container) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Not yet implemented.");
	}
	
}
