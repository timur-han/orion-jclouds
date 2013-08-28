package org.jclouds.orion.http.filters;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;

public class EmptyDebugFilter implements HttpRequestFilter {

    @Override
    public HttpRequest filter(HttpRequest req) throws HttpException {

	return req;
    }

}
