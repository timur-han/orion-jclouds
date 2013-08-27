package org.jclouds.orion.http.filters.create;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

import com.google.inject.Inject;

public class CreateFolderFilter implements HttpRequestFilter {

    private OrionSpecificFileMetadata metadata;

    @Inject
    public CreateFolderFilter(OrionSpecificFileMetadata metadata) {
	this.metadata = metadata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jclouds.http.HttpRequestFilter#filter(org.jclouds.http.HttpRequest )
     */
    @Override
    public HttpRequest filter(HttpRequest request) throws HttpException {
	try {
	    metadata = metadata.parseObject((String) request.getPayload()
		    .getRawContent());
	    metadata.setDirectory(true);
	    request = request.toBuilder().payload(metadata.getJSONString())
		    .build();
	} catch (JsonGenerationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return request;
    }

}