package org.jclouds.orion;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jclouds.orion.config.constans.OrionConstantValues;

import com.google.common.base.Preconditions;

public class OrionUtils {

    /**
     * Removes the last element which is the name of the blob for instance
     * /path1/path2/blobname/ -> path1/path2/ The first slash is removed since
     * the paths are relative
     * 
     * @param blobName
     * @return
     */
    static public String getParentPath(String blobName) {
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

    /**
     * Convert blobName to an hashed unique ID SHA-256 hashing is used This
     * method is used to create
     * 
     * @param blobName
     * @return
     */
    static public String getHashID(String blobName) {
	MessageDigest messageDigest;
	try {
	    messageDigest = MessageDigest.getInstance("SHA-256");
	    messageDigest.update(blobName
		    .getBytes(OrionConstantValues.ENCODING));
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

    /**
     * Gets the name of passed name by extracting the parent paths
     * 
     * @param originalName
     * @return
     */
    public static String getName(String originalName) {
	String parentPath = OrionUtils.getParentPath(originalName);
	return originalName.replaceFirst(parentPath, "").replaceAll(
		OrionConstantValues.PATH_DELIMITER, "");
    }

    /**
     * Used to provide one more time encoded path due to an existing bug in
     * Orion https://bugs.eclipse.org/bugs/show_bug.cgi?id=416118 This method is
     * meant to be used while operating on already created objects
     * 
     * @param parentPath
     * @return
     */
    static public String getParentRequestPath(String parentPath) {
	Preconditions.checkNotNull(parentPath, "blobname is null");
	String requestParent = "";

	for (String path : parentPath.split(OrionConstantValues.PATH_DELIMITER)) {
	    if (!path.isEmpty()) {
		requestParent = requestParent + OrionUtils.getRequestName(path)
			+ OrionConstantValues.PATH_DELIMITER;
	    }
	}
	return requestParent;
    }

    /**
     * Used to provide one more time encoded name due to an existing bug in
     * Orion https://bugs.eclipse.org/bugs/show_bug.cgi?id=416118 This method is
     * meant to be used while operating on already created objects
     * 
     * @param createdName
     *            Gets orion based name ,i.e., all parent paths have been
     *            removed
     * @return
     */
    public static String getRequestName(String createdName) {
	return OrionUtils.encodeName(createdName);
    }

    /**
     * 
     * @param createdName
     * @return
     */
    private static String encodeName(String createdName) {

	try {
	    return URLEncoder.encode(createdName, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return createdName;
    }

}
