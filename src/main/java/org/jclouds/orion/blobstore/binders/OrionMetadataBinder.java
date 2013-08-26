package org.jclouds.orion.blobstore.binders;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jclouds.http.HttpRequest;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.rest.Binder;

public class OrionMetadataBinder implements Binder {

	@Override
	public <R extends HttpRequest> R bindToRequest(R request, Object input) {
		OrionBlob blob = OrionBlob.class.cast(input);
		request = (R) request
				.toBuilder()
				.replaceHeader(OrionHttpFields.HEADER_SLUG,
						blob.getProperties().getName()).build();
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setPayload(mapper.writeValueAsString(blob.getProperties()));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}
}
