package org.jclouds.orion.blobstore.functions.parsers.param;

import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.domain.OrionBlob;

import com.google.common.base.Function;

public class BlobNameParamParser implements Function<Object, String> {

	/**
	 * Returns encoded blob name
	 */
	@Override
	public String apply(Object blob) {

		return OrionUtils.getRequestLocation(((OrionBlob) blob).getProperties().getName());
	}

}
