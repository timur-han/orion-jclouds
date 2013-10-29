package org.jclouds.orion.http.filters.create;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.blobstore.functions.converters.JSONToOrionSpecificObject;
import org.jclouds.orion.blobstore.functions.converters.OrionSpecificObjectToJSON;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Make Directory attribute of file attributes true in the request
 * 
 * @author Timur
 * 
 */
public class CreateFolderFilter implements HttpRequestFilter {

	private final JSONToOrionSpecificObject json2OrionSpecificObj;
	private final OrionSpecificObjectToJSON orionSpecificObject2JSON;

	@Inject
	public CreateFolderFilter(JSONToOrionSpecificObject json2OrionSpecificObj,
	      OrionSpecificObjectToJSON orionSpecificObject2JSON) {

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
		metadata = json2OrionSpecificObj.apply((String) request.getPayload().getRawContent());
		metadata.setDirectory(true);
		request = request.toBuilder().payload(orionSpecificObject2JSON.apply(metadata)).build();
		return request;
	}

}