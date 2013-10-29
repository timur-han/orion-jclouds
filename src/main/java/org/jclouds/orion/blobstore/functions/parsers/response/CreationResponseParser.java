package org.jclouds.orion.blobstore.functions.parsers.response;

import org.jclouds.http.HttpResponse;

import com.google.common.base.Function;

public class CreationResponseParser implements Function<HttpResponse, Boolean> {

	@Override
	public Boolean apply(HttpResponse response) {
		if ((response.getStatusCode() == 200) || (response.getStatusCode() == 201)) {
			return true;
		} else {
			return false;
		}
	}

}
