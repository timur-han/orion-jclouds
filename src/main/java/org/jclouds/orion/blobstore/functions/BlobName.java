package org.jclouds.orion.blobstore.functions;

import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Function;

public class BlobName implements Function<Object, String> {

	@Override
	public String apply(Object blob) {
		return ((OrionBlob) blob).getProperties().getName();
	}

}
