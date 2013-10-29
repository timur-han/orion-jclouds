package org.jclouds.orion.http.filters.create;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.blobstore.functions.converters.OrionSpecificObjectToJSON;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * This class should be present before providing remaining filters
 * 
 * @author timur
 * 
 */
public class EmptyRequestFilter implements HttpRequestFilter {

	private final OrionSpecificFileMetadata metadata;
	private final OrionSpecificObjectToJSON orionSpecificObject2JSON;

	@Inject
	public EmptyRequestFilter(OrionSpecificFileMetadata metadata, OrionSpecificObjectToJSON orionSpecificObject2JSON) {
		this.metadata = Preconditions.checkNotNull(metadata, "metadata is null");
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
	public HttpRequest filter(HttpRequest req) throws HttpException {
		req = req.toBuilder().payload(this.orionSpecificObject2JSON.apply(metadata)).build();
		return req;
	}

}