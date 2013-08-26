package org.jclouds.orion.blobstore.functions;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.domain.MutableBlobProperties;
import org.jclouds.orion.domain.internal.MutableBlobPropertiesSerializer;

import com.google.common.base.Function;
import com.google.gson.GsonBuilder;

public class MetadataResponseParser implements
	Function<HttpResponse, MutableBlobProperties> {

    @Override
    public MutableBlobProperties apply(HttpResponse response) {
	GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MutableBlobProperties.class,
		new MutableBlobPropertiesSerializer());
	StringWriter writer = new StringWriter();
	MutableBlobProperties properties = null;
	try {
	    IOUtils.copy(response.getPayload().getInput(), writer);
	    String theString = writer.toString();
	    properties = gsonBuilder.create().fromJson(theString,
		    MutableBlobProperties.class);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return properties;
    }
}
