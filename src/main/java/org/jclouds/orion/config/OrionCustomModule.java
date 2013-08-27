package org.jclouds.orion.config;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.jclouds.io.MutableContentMetadata;
import org.jclouds.io.payloads.BaseMutableContentMetadata;
import org.jclouds.orion.domain.Attributes;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;
import org.jclouds.orion.domain.internal.AttributesImpl;
import org.jclouds.orion.domain.internal.MutableBlobPropertiesImpl;
import org.jclouds.orion.domain.internal.OrionSpecificFileMetadataImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class OrionCustomModule extends AbstractModule {

	@Override
	protected void configure() {

	}

	@Provides
	public ObjectMapper getObjectMapper() {
		// configure objectmapper and provide it
		SimpleModule module = new SimpleModule("SimpleAbstractTypeResolver",
				Version.unknownVersion());
		module.addAbstractTypeMapping(MutableBlobProperties.class,
				MutableBlobPropertiesImpl.class);
		module.addAbstractTypeMapping(MutableContentMetadata.class,
				BaseMutableContentMetadata.class);
		module.addAbstractTypeMapping(OrionSpecificFileMetadata.class,
				OrionSpecificFileMetadataImpl.class);
		module.addAbstractTypeMapping(Attributes.class, AttributesImpl.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		return mapper;
	}

	@Provides
	OrionSpecificFileMetadata getOrionSpecificFileMetadata() {
		return new OrionSpecificFileMetadataImpl();
	}
}
