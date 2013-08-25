package org.jclouds.orion.domain;

import org.jclouds.io.PayloadEnclosing;
import org.jclouds.javax.annotation.Nullable;

import com.google.common.collect.Multimap;

public interface OrionBlob extends PayloadEnclosing, Comparable<OrionBlob> {

	public interface Factory {
		OrionBlob create(@Nullable MutableBlobProperties properties);
	}

	/**
	 * @return System and User metadata relevant to this object.
	 */
	MutableBlobProperties getProperties();

	Multimap<String, String> getAllHeaders();

	void setAllHeaders(Multimap<String, String> allHeaders);

}
