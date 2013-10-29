/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jclouds.orion.blobstore.fallbacks;

import org.jclouds.Fallback;
import org.jclouds.http.HttpCommandExecutorService;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.OrionResponseException;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionHttpFields;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;

/**
 * @author timur
 * 
 */
public class SameFileWithDiffTypeFallback implements Fallback<Boolean> {

	private final OrionApi api;
	private final HttpCommandExecutorService commandExecutor;

	@Inject
	public SameFileWithDiffTypeFallback(OrionApi api, HttpCommandExecutorService commandExecutor) {
		this.api = Preconditions.checkNotNull(api, "api is null");
		this.commandExecutor = Preconditions.checkNotNull(commandExecutor, "HttpCommandExecutorService");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.common.util.concurrent.FutureFallback#create(java.lang.Throwable
	 * )
	 */
	@Override
	public ListenableFuture<Boolean> create(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.Fallback#createOrPropagate(java.lang.Throwable)
	 */
	@Override
	public Boolean createOrPropagate(Throwable t) throws Exception {

		if (t instanceof OrionResponseException) {
			OrionResponseException exception = (OrionResponseException) t;
			if (exception.getError().getHttpCode().equals("500")) {
				// file exists as folder
				// folder exists as file replace them
				// api.removeBlob(userWorkspace, containerName, parentPath,
				// blobName)(userWorkspace, containerName)
				String path = exception.getCommand().getCurrentRequest().getEndpoint().getRawPath();
				while (path.startsWith(OrionConstantValues.PATH_DELIMITER)) {
					path = path.replaceFirst(OrionConstantValues.PATH_DELIMITER, "");
				}
				path = path
				      + exception.getCommand().getCurrentRequest().getHeaders().get(OrionHttpFields.HEADER_SLUG).toArray()[0];
				// in case it was a file creation attemp change it to the file
				// api
				path = path.replaceFirst(OrionConstantValues.ORION_IMPORT_PATH, OrionConstantValues.ORION_FILE_PATH);
				boolean result = this.api.deleteGivenPath(path);

				if (result) {
					exception.getCommand().setException(null);
					if (String.valueOf(this.commandExecutor.invoke(exception.getCommand()).getStatusCode()).startsWith("2")) {
						return true;
					}
				}
			}
		}
		throw Throwables.propagate(t);
	}
}
