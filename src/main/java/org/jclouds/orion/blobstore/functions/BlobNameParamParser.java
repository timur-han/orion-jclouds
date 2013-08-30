package org.jclouds.orion.blobstore.functions;

import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Function;

public class BlobNameParamParser implements Function<Object, String> {

	@Override
	public String apply(Object blob) {
		// TODO when bug is fixed this line needs to changed
		return OrionUtils.getRequestName(((OrionBlob) blob).getProperties()
				.getName());
	}

}
