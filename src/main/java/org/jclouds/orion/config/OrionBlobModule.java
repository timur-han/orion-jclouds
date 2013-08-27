package org.jclouds.orion.config;

import javax.inject.Inject;
import javax.inject.Provider;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.jclouds.blobstore.config.BlobStoreObjectModule;
import org.jclouds.io.MutableContentMetadata;
import org.jclouds.io.payloads.BaseMutableContentMetadata;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.orion.domain.internal.MutableBlobPropertiesImpl;
import org.jclouds.orion.domain.internal.OrionBlobImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
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

    @Provides
    public ObjectMapper objectMapperProvider() {
	// configure objectmapper and provide it
	SimpleModule module = new SimpleModule("SimpleAbstractTypeResolver",
		Version.unknownVersion());
	module.addAbstractTypeMapping(MutableBlobProperties.class,
		MutableBlobPropertiesImpl.class);
	module.addAbstractTypeMapping(MutableContentMetadata.class,
		BaseMutableContentMetadata.class);

	ObjectMapper mapper = new ObjectMapper();
	mapper.registerModule(module);
	mapper.setSerializationInclusion(Inclusion.NON_NULL);
	return mapper;
    }

}
