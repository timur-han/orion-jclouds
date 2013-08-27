package org.jclouds.orion;

import org.jclouds.orion.config.constans.OrionConstantValues;

import com.google.common.base.Preconditions;

public class OrionUtils {

    static public String fetchParentPath(String blobName) {
	Preconditions.checkNotNull(blobName, "blobname is null");
	String fetchedParent = "";
	String[] blobPaths = blobName.split(OrionConstantValues.PATH_DELIMITER);
	for (int index = 0; index < (blobPaths.length - 1); index++) {
	    if (!blobPaths[index].isEmpty()) {
		fetchedParent = fetchedParent + blobPaths[index]
			+ OrionConstantValues.PATH_DELIMITER;
	    }
	}
	return fetchedParent;
    }

    static public String getMetadataFileName(String blobName) {
	return String.valueOf(blobName.hashCode());
    }

}
