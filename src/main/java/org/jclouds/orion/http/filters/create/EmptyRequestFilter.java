package org.jclouds.orion.http.filters.create;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

import com.google.inject.Inject;

/**
 * This class should be present before providing remaining filters
 * 
 * @author timur
 * 
 */
public class EmptyRequestFilter implements HttpRequestFilter {

    private final OrionSpecificFileMetadata metadata;

    @Inject
    public EmptyRequestFilter(OrionSpecificFileMetadata metadata) {
	this.metadata = metadata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jclouds.http.HttpRequestFilter#filter(org.jclouds.http.HttpRequest )
     */
    @Override
    public HttpRequest filter(HttpRequest req) throws HttpException {
	try {
	    req = req.toBuilder().payload(metadata.getJSONString()).build();
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
	return req;
    }

}