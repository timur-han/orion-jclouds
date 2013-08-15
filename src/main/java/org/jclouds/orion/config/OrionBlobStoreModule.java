package org.jclouds.orion.config;


import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.blobstore.config.BlobStoreMapModule;
import org.jclouds.blobstore.config.BlobStoreObjectModule;
import org.jclouds.blobstore.strategy.ClearContainerStrategy;
import org.jclouds.blobstore.strategy.ClearListStrategy;
import org.jclouds.blobstore.strategy.DeleteDirectoryStrategy;
import org.jclouds.blobstore.strategy.GetBlobsInListStrategy;
import org.jclouds.blobstore.strategy.PutBlobsStrategy;
import org.jclouds.orion.blobstore.OrionBlobStore;
import org.jclouds.orion.blobstore.OrionBlobStoreContext;
import org.jclouds.orion.blobstore.strategy.DeleteAllKeysInList;
import org.jclouds.orion.blobstore.strategy.GetAllBlobsInListAndRetryOnFailure;
import org.jclouds.orion.blobstore.strategy.PutBlobsStrategyImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class OrionBlobStoreModule extends AbstractModule {
	
	@Override
	protected void configure() {
		//this.install(new BlobStoreMapModule());
		//this.install(new BlobStoreObjectModule());
		this.bind(BlobStore.class).to(OrionBlobStore.class).in(Scopes.SINGLETON);
		
		this.bind(BlobStoreContext.class).to(OrionBlobStoreContext.class).in(Scopes.SINGLETON);
		this.bind(GetBlobsInListStrategy.class).to(GetAllBlobsInListAndRetryOnFailure.class).in(Scopes.SINGLETON);
		this.bind(DeleteDirectoryStrategy.class).to(org.jclouds.orion.blobstore.strategy.MarkersDeleteDirectoryStrategy.class).in(Scopes.SINGLETON);
		this.bind(PutBlobsStrategy.class).to(PutBlobsStrategyImpl.class).in(Scopes.SINGLETON);
		this.bind(ClearContainerStrategy.class).to(DeleteAllKeysInList.class).in(Scopes.SINGLETON);
		this.bind(ClearListStrategy.class).to(DeleteAllKeysInList.class).in(Scopes.SINGLETON);
		this.bind(ConsistencyModel.class).toInstance(ConsistencyModel.STRICT);
		
		//this.bind(AsyncBlobStore.class).to(BlobStore.class).in(Scopes.SINGLETON);
		
	}
	
}
