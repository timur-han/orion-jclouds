package org.jclouds.orion.http.filters;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;

public class OrionCustomFields implements HttpRequestFilter{

	@Override
	public HttpRequest filter(HttpRequest req) throws HttpException {
		return req.toBuilder().replaceHeader(OrionHttpFields.ORION_VERSION_FIELD, OrionConstantValues.ORION_VERSION).build();
	}

}
