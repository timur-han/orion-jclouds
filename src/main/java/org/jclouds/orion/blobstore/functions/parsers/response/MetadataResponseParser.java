package org.jclouds.orion.blobstore.functions.parsers.response;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.domain.MutableBlobProperties;

import com.google.common.base.Function;
import com.google.inject.Inject;

public class MetadataResponseParser implements Function<HttpResponse, MutableBlobProperties> {

	private final ObjectMapper mapper;

	@Inject
	public MetadataResponseParser(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public MutableBlobProperties apply(HttpResponse response) {

		StringWriter writer = new StringWriter();
		MutableBlobProperties properties = null;
		try {
			IOUtils.copy(response.getPayload().getInput(), writer);
			String theString = writer.toString();
			properties = mapper.readValue(theString, MutableBlobProperties.class);
		} catch (IOException e) {
			System.out.println(response.getMessage());
			e.printStackTrace();
		}

		return properties;
	}
}
