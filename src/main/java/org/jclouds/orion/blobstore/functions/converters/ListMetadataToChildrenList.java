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
package org.jclouds.orion.blobstore.functions.converters;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.domain.OrionChildMetadata;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Create the children node java objects from a response to a list contained
 * files request
 * 
 * @author timur
 * 
 */
public class ListMetadataToChildrenList implements Function<HttpResponse, List<OrionChildMetadata>> {

	private final ObjectMapper mapper;

	@Inject
	public ListMetadataToChildrenList(ObjectMapper mapper) {
		this.mapper = Preconditions.checkNotNull(mapper, "mapper is null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public List<OrionChildMetadata> apply(HttpResponse res) {
		try {
			JsonNode jsonNode = mapper.readTree(res.getPayload().getInput());
			return new ConvertToFlatList(mapper).apply(jsonNode);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
