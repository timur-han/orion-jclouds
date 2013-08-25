package org.jclouds.orion.blobstore.functions;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;

public class HiddenFileFilter implements HttpRequestFilter {

	@Override
	public HttpRequest filter(HttpRequest request) throws HttpException {

		return request;
	}

}
