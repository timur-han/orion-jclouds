package org.jclouds.orion.blobstore.functions.converters;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * A function to serialize to {@link OrionSpecificFileMetadata} to JSON format
 * 
 * @author Timur
 * 
 */
public class OrionSpecificObjectToJSON implements Function<OrionSpecificFileMetadata, String> {

	private final ObjectMapper mapper;

	@Inject
	public OrionSpecificObjectToJSON(ObjectMapper mapper) {
		this.mapper = Preconditions.checkNotNull(mapper, "mapper is null");
	}

	@Override
	public String apply(OrionSpecificFileMetadata jsonObj) {
		try {
			return mapper.writeValueAsString(jsonObj);
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
		return null;
	}
}
