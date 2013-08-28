package org.jclouds.orion;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	MessageDigest messageDigest;
	try {
	    messageDigest = MessageDigest.getInstance("SHA-256");
	    messageDigest.update(blobName.getBytes("UTF-8"));
	    byte[] digest = messageDigest.digest();
	    BigInteger bigInteger = new BigInteger(1, digest);
	    return bigInteger.toString(16);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	    return String.valueOf(blobName.hashCode());
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return String.valueOf(blobName.hashCode());
	}

    }

    public static String fetchName(String originalName) {
	String parentPath = OrionUtils.fetchParentPath(originalName);
	// should be relative path
	if (originalName.startsWith("/")) {
	    originalName = originalName.replaceFirst("/", "");
	}
	return originalName.replaceFirst(parentPath, "");
    }

    public static String getFilePath(String originalName) {
	String returnVal = originalName;
	if (originalName.startsWith("/")) {
	    returnVal = originalName.replaceFirst("/", "");
	}
	if (!originalName.endsWith("/")) {
	    returnVal = returnVal + "/";
	}
	return returnVal;
    }

}
