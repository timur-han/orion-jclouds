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
package org.jclouds.orion.blobstore.fallbacks;

import org.jclouds.Fallback;
import org.jclouds.orion.OrionResponseException;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author timur
 * 
 */
public class DuplicateCreationFallback implements Fallback<Boolean> {

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
			if (exception.getError().getHttpCode().equals("400")
			      && exception.getError().getMessage().startsWith("Duplicate")) {
				return false;
			}
		}
		throw Throwables.propagate(t);
	}

}
