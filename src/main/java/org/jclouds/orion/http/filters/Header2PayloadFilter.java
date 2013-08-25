package org.jclouds.orion.http.filters;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.config.constans.OrionHttpFields;

import com.google.gson.JsonObject;

/**
 * Converts the pre defined orion specific headers to json fields for the
 * payload for instance Directory true needs to be in the payload as a json
 * 
 * @author Timur
 * 
 */
public class Header2PayloadFilter implements HttpRequestFilter {

	@Override
	public HttpRequest filter(HttpRequest request) throws HttpException {
		JsonObject dirObj = new JsonObject();
		for (String headerKey : request.getHeaders().keySet()) {
			if (headerKey.startsWith(OrionHttpFields.HEADER_TO_PAYLOAD_PREFIX)) {

			}
		}
		return request;
	}

}
