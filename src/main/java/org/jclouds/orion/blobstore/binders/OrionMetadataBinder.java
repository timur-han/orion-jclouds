package org.jclouds.orion.blobstore.binders;

import org.jclouds.http.HttpRequest;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.rest.Binder;

import com.google.gson.Gson;

public class OrionMetadataBinder implements Binder {

	@Override
	public <R extends HttpRequest> R bindToRequest(R request, Object input) {
		OrionBlob blob = OrionBlob.class.cast(input);
		request = (R) request
				.toBuilder()
				.replaceHeader(OrionHttpFields.HEADER_SLUG,
						blob.getProperties().getName()).build();
		String jsonRep = (new Gson()).toJson(blob.getProperties());
		request.setPayload(jsonRep);
		return request;
	}
}
