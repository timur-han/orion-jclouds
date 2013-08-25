package org.jclouds.orion.blobstore.functions;

import java.io.StringWriter;

import org.jclouds.http.HttpResponse;
import org.jclouds.orion.domain.MutableBlobProperties;

import com.google.common.base.Function;
import com.google.gson.Gson;

public class MetadataResponseParser implements
		Function<HttpResponse, MutableBlobProperties> {

	@Override
	public MutableBlobProperties apply(HttpResponse response) {
		Gson reader = new Gson();
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, encoding);
		String theString = writer.toString();
		StreamRresponse.getPayload().getInput()
		reader.fromJson(json, classOfT)
		return null;
	}
}
