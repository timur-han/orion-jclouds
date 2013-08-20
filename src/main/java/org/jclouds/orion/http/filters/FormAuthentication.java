package org.jclouds.orion.http.filters;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jclouds.domain.Credentials;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.location.Provider;
import org.jclouds.orion.config.constans.OrionHttpFields;

import com.google.common.base.Supplier;

/**
 * A costly hack to resolve form based authentication problem.
 * Needs to be replaced with a cleaner approach.
 * @author Timur
 *
 */
public class FormAuthentication implements HttpRequestFilter{

	private final Supplier<Credentials> creds;
	DefaultHttpClient httpclient = new DefaultHttpClient();
	
   	@Inject
   	public FormAuthentication(@Provider Supplier<Credentials> creds) {
	   this.creds = checkNotNull(creds, "creds");
    }
	
	@Override
	public HttpRequest filter(HttpRequest request) throws HttpException {
		Credentials currentCreds = checkNotNull(creds.get(), "credential supplier returned null");
		try{
			HttpHead httphead= new HttpHead(request.getEndpoint());
			for(String key : request.getHeaders().keySet()){
				httphead.addHeader(key, request.getHeaders().get(key).toString());
			}
			
	        HttpResponse response = httpclient.execute(httphead);
	        HttpEntity entity = response.getEntity();
	        System.out.println("Login form get: " + response.getStatusLine());
	        EntityUtils.consume(entity);
	        
	        List<Cookie> cookies = httpclient.getCookieStore().getCookies();

	
	        HttpPost httpost = new HttpPost("http://localhost:8080/mixloginstatic/LoginWindow.html?redirect=http%3A%2F%2Flocalhost%3A8080%2F&key=FORMOpenIdUser");
	
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair(creds.get().identity, "username"));
	        nvps.add(new BasicNameValuePair(creds.get().credential, "password"));
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
	
	        response = httpclient.execute(httpost);
	        entity = response.getEntity();
	
	        System.out.println("Login form get: " + response.getStatusLine());
	        EntityUtils.consume(entity);
	
	        System.out.println("Post logon cookies:");
	        cookies = httpclient.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		return request;
	}

}
