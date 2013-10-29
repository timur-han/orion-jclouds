package org.jclouds.orion.blobstore.binders;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jclouds.http.HttpRequest;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.rest.Binder;

import com.google.inject.Inject;

public class OrionMetadataBinder implements Binder {

	private final ObjectMapper mapper;

	@Inject
	public OrionMetadataBinder(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public <R extends HttpRequest> R bindToRequest(R request, Object input) {
		OrionBlob blob = OrionBlob.class.cast(input);
		Date date = Calendar.getInstance().getTime();
		blob.getProperties().setLastModified(date);

		request = (R) request.toBuilder()
		      .replaceHeader(OrionHttpFields.HEADER_SLUG, OrionUtils.getMetadataName(blob.getProperties().getName()))
		      .build();

		try {
			request.setPayload(this.mapper.writeValueAsString(blob.getProperties()));
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
