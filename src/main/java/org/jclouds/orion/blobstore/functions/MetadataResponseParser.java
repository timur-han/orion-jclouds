package org.jclouds.orion.blobstore.functions;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleAbstractTypeResolver;
import org.codehaus.jackson.map.module.SimpleModule;
import org.jclouds.http.HttpResponse;
import org.jclouds.io.MutableContentMetadata;
import org.jclouds.io.payloads.BaseMutableContentMetadata;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.internal.MutableBlobPropertiesImpl;

import com.google.common.base.Function;

public class MetadataResponseParser implements
		Function<HttpResponse, MutableBlobProperties> {

	@Override
	public MutableBlobProperties apply(HttpResponse response) {
		SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
		SimpleModule module = new SimpleModule("SimpleAbstractTypeResolver",
				Version.unknownVersion());
		module.addAbstractTypeMapping(MutableBlobProperties.class,
				MutableBlobPropertiesImpl.class);
		module.addAbstractTypeMapping(MutableContentMetadata.class,
				BaseMutableContentMetadata.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		StringWriter writer = new StringWriter();
		MutableBlobProperties properties = null;
		try {
			IOUtils.copy(response.getPayload().getInput(), writer);
			String theString = writer.toString();
			properties = mapper.readValue(theString,
					MutableBlobProperties.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties;
	}
}
