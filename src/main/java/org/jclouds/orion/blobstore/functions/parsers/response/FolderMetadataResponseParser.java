package org.jclouds.orion.blobstore.functions.parsers.response;

import org.jclouds.http.HttpResponse;

import com.google.common.base.Function;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FolderMetadataResponseParser implements Function<HttpResponse, Boolean> {

	@Override
	public Boolean apply(HttpResponse res) {
		if (res.getStatusLine().startsWith("4")) {
			return false;
		} else if (res.getStatusLine().startsWith("2")) {
			JsonObject responseObject = (JsonObject) (new JsonParser()).parse(res.getMessage());
			if (responseObject.get("Directory").getAsBoolean()) {
				return true;
			}
		}
		return false;
	}

}
