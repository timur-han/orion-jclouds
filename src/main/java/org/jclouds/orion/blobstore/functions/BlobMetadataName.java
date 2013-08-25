package org.jclouds.orion.blobstore.functions;

import org.jclouds.orion.blobstore.OrionUtils;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Function;

public class BlobMetadataName implements Function<Object, String> {

	@Override
	public String apply(Object blob) {
		return OrionUtils.getMetadataFileName(OrionBlob.class.cast(blob)
				.getProperties().getName());
	}

}
