package org.jclouds.orion.blobstore.config;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.blobstore.config.BlobStoreMapModule;
import org.jclouds.blobstore.strategy.ClearContainerStrategy;
import org.jclouds.blobstore.strategy.ClearListStrategy;
import org.jclouds.blobstore.strategy.DeleteDirectoryStrategy;
import org.jclouds.blobstore.strategy.GetBlobsInListStrategy;
import org.jclouds.blobstore.strategy.PutBlobsStrategy;
import org.jclouds.orion.blobstore.OrionBlobStore;
import org.jclouds.orion.blobstore.OrionBlobStoreContext;
import org.jclouds.orion.blobstore.strategy.internal.ClearFilesInContainer;
import org.jclouds.orion.blobstore.strategy.internal.DeleteAllKeysInList;
import org.jclouds.orion.blobstore.strategy.internal.GetAllBlobsInListAndRetryOnFailure;
import org.jclouds.orion.blobstore.strategy.internal.PutBlobsStrategyImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class OrionBlobStoreModule extends AbstractModule {

	@Override
	protected void configure() {

		install(new BlobStoreMapModule());
		bind(ConsistencyModel.class).toInstance(ConsistencyModel.STRICT);
		bind(BlobStoreContext.class).to(OrionBlobStoreContext.class).in(Scopes.SINGLETON);
		bind(GetBlobsInListStrategy.class).to(GetAllBlobsInListAndRetryOnFailure.class).in(Scopes.SINGLETON);
		bind(DeleteDirectoryStrategy.class).to(
		      org.jclouds.orion.blobstore.strategy.internal.MarkersDeleteDirectoryStrategy.class).in(Scopes.SINGLETON);
		bind(PutBlobsStrategy.class).to(PutBlobsStrategyImpl.class).in(Scopes.SINGLETON);
		bind(ClearContainerStrategy.class).to(ClearFilesInContainer.class).in(Scopes.SINGLETON);
		bind(ClearListStrategy.class).to(DeleteAllKeysInList.class).in(Scopes.SINGLETON);
		bind(BlobStore.class).to(OrionBlobStore.class).in(Scopes.SINGLETON);

	}
}
