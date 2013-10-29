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
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.domain.OrionChildMetadata;

import com.google.common.base.Function;

/**
 * A recursive function to convert tree hierarchy of file system structure to a
 * list
 * 
 * @author timur
 * 
 */
public class ConvertToFlatList implements Function<JsonNode, List<OrionChildMetadata>> {

	private final ObjectMapper mapper;

	/**
	 * @param mapper
	 */
	public ConvertToFlatList(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public List<OrionChildMetadata> apply(JsonNode parentNode) {
		List<OrionChildMetadata> arrayList = new ArrayList<OrionChildMetadata>();

		if (parentNode.has(OrionConstantValues.LIST_CHILDREN)
		      && parentNode.get(OrionConstantValues.LIST_CHILDREN).isArray()) {
			for (JsonNode childNode : parentNode.get(OrionConstantValues.LIST_CHILDREN)) {
				try {
					OrionChildMetadata childData = mapper.readValue(childNode, OrionChildMetadata.class);
					// do not include metadata in the list
					if (childData.getName().equals(OrionConstantValues.ORION_METADATA_FILE_NAME)) {
						continue;
					}
					arrayList.add(childData);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arrayList.addAll(new ConvertToFlatList(mapper).apply(childNode));
			}
		}

		return arrayList;
	}

}
