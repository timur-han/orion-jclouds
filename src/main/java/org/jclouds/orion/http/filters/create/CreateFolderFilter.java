package org.jclouds.orion.http.filters.create;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;
import org.jclouds.orion.http.functions.JSON2OrionSpecificObject;
import org.jclouds.orion.http.functions.OrionSpecificObject2JSON;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

public class CreateFolderFilter implements HttpRequestFilter {

	private final JSON2OrionSpecificObject json2OrionSpecificObj;
	private final OrionSpecificObject2JSON orionSpecificObject2JSON;

	@Inject
	public CreateFolderFilter(JSON2OrionSpecificObject json2OrionSpecificObj,
			OrionSpecificObject2JSON orionSpecificObject2JSON) {

		this.json2OrionSpecificObj = Preconditions.checkNotNull(
				json2OrionSpecificObj, "json2OrionSpecificObjis null");
		this.orionSpecificObject2JSON = Preconditions.checkNotNull(
				orionSpecificObject2JSON, "orionSpecificObject2JSON is null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jclouds.http.HttpRequestFilter#filter(org.jclouds.http.HttpRequest )
	 */
	@Override
	public HttpRequest filter(HttpRequest request) throws HttpException {
		OrionSpecificFileMetadata metadata;
		metadata = json2OrionSpecificObj.apply((String) request.getPayload()
				.getRawContent());
		metadata.setDirectory(true);
		request = request.toBuilder()
				.payload(orionSpecificObject2JSON.apply(metadata)).build();
		return request;
	}

}