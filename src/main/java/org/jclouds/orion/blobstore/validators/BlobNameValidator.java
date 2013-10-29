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
package org.jclouds.orion.blobstore.validators;

import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionErrorExplanations;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.predicates.Validator;

/**
 * Check if the blob name is valid. This function accepts a blob. String version
 * {@code StringNameValidator}
 * 
 * @author timur
 * 
 */
public class BlobNameValidator extends Validator<OrionBlob> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.predicates.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(OrionBlob t) throws IllegalArgumentException {
		if (t.getProperties().getName().contains(OrionConstantValues.ORION_METADATA_PATH)) {
			throw new IllegalArgumentException(OrionErrorExplanations.FORBIDDEN_METADATA_NAME);
		}
	}

}
