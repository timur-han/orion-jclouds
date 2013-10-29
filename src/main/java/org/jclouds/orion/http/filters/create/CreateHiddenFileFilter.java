package org.jclouds.orion.http.filters.create;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.blobstore.functions.JSON2OrionSpecificObject;
import org.jclouds.orion.blobstore.functions.OrionSpecificObject2JSON;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Make Hidden attribute of file attributes true in the request
 * 
 * @author Timur
 * 
 */
public class CreateHiddenFileFilter implements HttpRequestFilter {

	private final JSON2OrionSpecificObject json2OrionSpecificObj;
	private final OrionSpecificObject2JSON orionSpecificObject2JSON;

	@Inject
	public CreateHiddenFileFilter(OrionSpecificFileMetadata metadata, JSON2OrionSpecificObject json2OrionSpecificObj,
	      OrionSpecificObject2JSON orionSpecificObject2JSON) {
		this.json2OrionSpecificObj = Preconditions.checkNotNull(json2OrionSpecificObj, "json2OrionSpecificObjis null");
		this.orionSpecificObject2JSON = Preconditions.checkNotNull(orionSpecificObject2JSON,
		      "orionSpecificObject2JSON is null");
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
		metadata = this.json2OrionSpecificObj.apply((String) request.getPayload().getRawContent());
		metadata.getAttributes().setHidden(true);
		request = request.toBuilder().payload(this.orionSpecificObject2JSON.apply(metadata)).build();
		return request;
	}

}