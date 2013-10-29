package org.jclouds.orion.blobstore;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.jclouds.Context;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobMap;
import org.jclouds.blobstore.BlobRequestSigner;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.InputStreamMap;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.internal.BaseView;
import org.jclouds.location.Provider;
import org.jclouds.rest.Utils;

import com.google.common.io.Closeables;
import com.google.common.reflect.TypeToken;

public class OrionBlobStoreContext extends BaseView implements BlobStoreContext {

	private final BlobStore blobStore;
	private final ConsistencyModel consistencyModel;
	private final Utils utils;
	private final BlobRequestSigner blobRequestSigner;

	@Inject
	public OrionBlobStoreContext(@Provider Context backend, @Provider TypeToken<? extends Context> backendType,
	      Utils utils, ConsistencyModel consistencyModel, BlobStore blobStore, BlobRequestSigner blobRequestSigner) {

		super(backend, backendType);
		this.consistencyModel = checkNotNull(consistencyModel, "consistencyModel");
		this.blobStore = checkNotNull(blobStore, "blobStore");
		this.utils = checkNotNull(utils, "utils");
		this.blobRequestSigner = checkNotNull(blobRequestSigner, "blobRequestSigner");
	}

	@Override
	public ConsistencyModel getConsistencyModel() {
		return consistencyModel;
	}

	@Override
	public BlobMap createBlobMap(String container, ListContainerOptions options) {
		return null;
	}

	@Override
	public BlobMap createBlobMap(String container) {
		return null;
	}

	@Override
	public InputStreamMap createInputStreamMap(String container, ListContainerOptions options) {
		return null;
	}

	@Override
	public InputStreamMap createInputStreamMap(String container) {
		return null;
	}

	@Override
	public BlobStore getBlobStore() {
		return blobStore;
	}

	@Override
	public AsyncBlobStore getAsyncBlobStore() {
		return null;
	}

	@Override
	public Utils utils() {
		return utils;
	}

	@Override
	public BlobRequestSigner getSigner() {
		return blobRequestSigner;
	}

	@Override
	public void close() {
		Closeables.closeQuietly(delegate());
	}

	public int hashCode() {
		return delegate().hashCode();
	}

	@Override
	public String toString() {
		return delegate().toString();
	}

	@Override
	public boolean equals(Object obj) {
		return delegate().equals(obj);
	}

}
