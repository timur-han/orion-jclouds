package org.jclouds.orion.config;

import javax.inject.Inject;
import javax.inject.Provider;

import org.jclouds.blobstore.config.BlobStoreObjectModule;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.orion.domain.internal.OrionBlobImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class OrionBlobModule extends AbstractModule {
    @Override
    protected void configure() {
	install(new BlobStoreObjectModule());
	bind(OrionBlob.Factory.class).to(OrionBlobFactory.class).in(
		Scopes.SINGLETON);

    }

    private static class OrionBlobFactory implements OrionBlob.Factory {
	@Inject
	Provider<MutableBlobProperties> metadataProvider;

	@Override
	public OrionBlob create(MutableBlobProperties metadata) {
	    return new OrionBlobImpl(metadata != null ? metadata
		    : metadataProvider.get());
	}
    }

}
