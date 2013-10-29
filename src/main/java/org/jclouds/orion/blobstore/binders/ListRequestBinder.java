/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.orion.blobstore.binders;

import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.http.HttpRequest;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.rest.Binder;

/**
 * @author timur
 * 
 */
public class ListRequestBinder implements Binder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.rest.Binder#bindToRequest(org.jclouds.http.HttpRequest,
	 * java.lang.Object)
	 */
	@Override
	public <R extends HttpRequest> R bindToRequest(R request, Object input) {
		ListContainerOptions options = ListContainerOptions.class.cast(input);
		if (options.isRecursive()) {
			request = (R) request.toBuilder()
			      .replaceQueryParam(OrionHttpFields.QUERY_DEPTH, OrionConstantValues.MAXIMUM_DEPTH).build();
		}
		if (options.isDetailed()) {
			// TODO a mechanism for this
		}
		if (options.getDir() != null) {
			String requestPath = request.getEndpoint().toASCIIString();
			if (!requestPath.endsWith(OrionConstantValues.PATH_DELIMITER)) {
				requestPath = requestPath + OrionConstantValues.PATH_DELIMITER;
			}
			requestPath = OrionConstantValues.PATH_DELIMITER + OrionUtils.convert2RelativePath(options.getDir());
			request = (R) request.toBuilder().endpoint(requestPath).build();

		}

		return request;
	}
}
