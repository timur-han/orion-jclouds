package org.jclouds.orion.config;

import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.blobstore.config.BlobStoreMapModule;
import org.jclouds.orion.blobstore.OrionAsyncBlobStore;
import org.jclouds.orion.blobstore.OrionBlobStore;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class OrionBlobStoreModule extends AbstractModule {
	
	@Override
	protected void configure() {
		this.install(new BlobStoreMapModule());
		this.bind(ConsistencyModel.class).toInstance(ConsistencyModel.STRICT);
		this.bind(BlobStore.class).to(OrionBlobStore.class).in(Scopes.SINGLETON);
		this.bind(AsyncBlobStore.class).to(OrionAsyncBlobStore.class).in(Scopes.SINGLETON);
		
	}
	
}
