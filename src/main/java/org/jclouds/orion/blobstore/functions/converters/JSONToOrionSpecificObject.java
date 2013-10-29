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
 * A function to de-serialize {@link OrionSpecificFileMetadata} from a JSON
 * String
 * 
 * @author Timur
 * 
 */
public class JSONToOrionSpecificObject implements Function<String, OrionSpecificFileMetadata> {

	private final ObjectMapper mapper;

	@Inject
	public JSONToOrionSpecificObject(ObjectMapper mapper) {
		this.mapper = Preconditions.checkNotNull(mapper, "mapper is null");
	}

	@Override
	public OrionSpecificFileMetadata apply(String jsonObj) {
		try {
			return mapper.readValue(jsonObj, OrionSpecificFileMetadata.class);
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
