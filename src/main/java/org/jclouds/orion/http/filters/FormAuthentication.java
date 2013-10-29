package org.jclouds.orion.http.filters;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.jclouds.domain.Credentials;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.location.Provider;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.config.constans.OrionHttpFields;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.net.HttpHeaders;

/**
 * This class is used to make a form-based authentication. It contains a cache.
 * To bypass the authentication requests need to have
 * {@code OrionHttpFields.IGNORE_AUTHENTICATION} header which will be removed by
 * this filter.
 * 
 * @author Timur
 * 
 */
public class FormAuthentication implements HttpRequestFilter {

	class SessionKeyRequester implements Callable<Collection<String>> {

		@Override
		public Collection<String> call() throws Exception {
			return getApi().formLogin(getCreds().identity, getCreds().credential).getHeaders().get(HttpHeaders.SET_COOKIE);
		}

	}

	private static Cache<String, Collection<String>> getKeycache() {
		return FormAuthentication.keyCache;
	}

	private final Credentials creds;

	private final OrionApi api;

	// This class holds a cache for keys
	// username:sesionsId pairs are used
	final static private Cache<String, Collection<String>> keyCache = CacheBuilder.newBuilder().maximumSize(1000)
	      .build();

	public static boolean hasKey(String identity) {

		return FormAuthentication.getKeycache().asMap().containsKey(identity);
	}

	public static void removeKey(String identity) {
		FormAuthentication.getKeycache().invalidate(identity);
	}

	@Inject
	public FormAuthentication(@Provider Supplier<Credentials> creds, OrionApi api) {
		Preconditions.checkNotNull(creds, "creds");
		this.creds = creds.get();
		this.api = Preconditions.checkNotNull(api, "creds");
	}

	@Override
	public HttpRequest filter(HttpRequest request) throws HttpException {
		boolean ignoreAuthentication = false;
		// The requests with the header ignoreauthentication will not be
		// validated

		if (request.getHeaders().containsKey(OrionHttpFields.IGNORE_AUTHENTICATION)) {
			request = request.toBuilder().removeHeader(OrionHttpFields.IGNORE_AUTHENTICATION).build();
			ignoreAuthentication = true;

		}
		Collection<String> cachedKey = null;
		try {
			if (!ignoreAuthentication) {
				cachedKey = FormAuthentication.getKeycache().get(getCreds().identity, new SessionKeyRequester());
				request = request.toBuilder()
				      .replaceHeader(HttpHeaders.COOKIE, cachedKey.toArray(new String[cachedKey.size()])).build();
			}
		} catch (ExecutionException e) {
			// TODO
			e.printStackTrace();
			throw new HttpException(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return request;
	}

	private OrionApi getApi() {
		return api;
	}

	private Credentials getCreds() {
		return creds;
	}
}
