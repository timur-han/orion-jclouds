package org.jclouds.orion.http.filters;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class FolderCreationFilter implements HttpRequestFilter {

    @Override
    public HttpRequest filter(HttpRequest request) throws HttpException {
	JsonObject dirObj = new JsonObject();
	dirObj.add("Directory", new JsonPrimitive("true"));
	return request;
    }

}
