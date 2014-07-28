package org.jclouds.orion;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jclouds.domain.Credentials;
import org.jclouds.location.Provider;
import org.jclouds.orion.config.constans.OrionConstantValues;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

public class OrionUtils {

	private final Supplier<Credentials> credsSupplier;

	@Inject
	public OrionUtils(@Provider Supplier<Credentials> creds) {
		this.credsSupplier = creds;
	}
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
		final String[] blobPaths = blobName.split(OrionConstantValues.PATH_DELIMITER);
		for (int index = 0; index < (blobPaths.length - 1); index++) {
			if (!blobPaths[index].isEmpty()) {
				fetchedParent = fetchedParent + blobPaths[index] + OrionConstantValues.PATH_DELIMITER;
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
	static public String getMetadataName(String blobName) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(blobName.getBytes(OrionConstantValues.ENCODING));
			final byte[] digest = messageDigest.digest();
			final BigInteger bigInteger = new BigInteger(1, digest);
			return bigInteger.toString(16);
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return String.valueOf(blobName.hashCode());
		} catch (final UnsupportedEncodingException e) {
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
		final String parentPath = OrionUtils.getParentPath(originalName);
		return encodeName(originalName.replaceFirst(parentPath, "").replaceAll(OrionConstantValues.PATH_DELIMITER, ""));
	}

	/**
	 * Used to provide one more time encoded path due to an existing bug in meant
	 * to be used while operating on already created objects. It is one more
	 * encoded because it provides the location. In the childeren list name is
	 * not double encoded.
	 *
	 * @param parentPath
	 * @return
	 */
	static public String getParentRequestLocation(String parentPath) {
		Preconditions.checkNotNull(parentPath, "blobname is null");
		String requestParent = "";

		for (final String path : parentPath.split(OrionConstantValues.PATH_DELIMITER)) {
			if (!path.isEmpty()) {
				requestParent = requestParent + OrionUtils.getRequestLocation(path) + OrionConstantValues.PATH_DELIMITER;
			}
		}
		return requestParent;
	}

	/**
	 * Locations are encoded one more time
	 *
	 * @param createdName
	 *           Gets orion based name ,i.e., all parent paths have been removed
	 * @return
	 */
	public static String getRequestLocation(String createdName) {
		return OrionUtils.encodeName(createdName);
	}

	/**
	 *
	 * Check if the given path is a container path. If it is a container path
	 * there should exist {file}/{userWorkspace}/{containerName} so the length of
	 * the path should be 3
	 *
	 * @param path
	 * @return
	 */
	public static boolean isContainerFromPath(String path) {
		final String[] paths = path.split(OrionConstantValues.PATH_DELIMITER);
		int nonEmptyLength = 0;
		for (final String pathPart : paths) {
			if (!pathPart.equals("")) {
				nonEmptyLength++;
			}
		}
		if (nonEmptyLength == 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Create a name for the user from the path by removing/file then
	 * userWorkspace and finally the container name This is achieved by removing
	 * first 3 strings.
	 *
	 * @param path
	 * @return
	 */
	public static String createOriginalNameFromLocation(String userWorkspace, String path) {
		// decode once because names are decoded one time to create the location
		// names
		path = OrionUtils.decodeName(path);
		// split using userWorkspace and take the second part which represents the path including the container
		String returnPath = path.split(userWorkspace)[1];
		// remove first and last slashed if they are available
		if(returnPath.lastIndexOf("/") == (returnPath.length() - 1)) {
			returnPath = returnPath.substring(0, returnPath.length() - 1);
		}
		if(returnPath.indexOf("/") == (0)) {
			returnPath = returnPath.replaceFirst("/", "");
		}
		return returnPath;
	}

	/**
	 *
	 * @param createdName
	 * @return
	 */
	private static String decodeName(String createdName) {

		try {
			return URLDecoder.decode(createdName, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createdName;
	}

	/**
	 *
	 * @param createdName
	 * @return
	 */
	private static String encodeName(String createdName) {

		try {
			return URLEncoder.encode(URLEncoder.encode(createdName, "UTF-8"), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createdName;
	}

	/**
	 * @param path
	 * @return
	 */
	public static String convert2RelativePath(String path) {
		while (!path.startsWith(OrionConstantValues.PATH_DELIMITER)) {
			path = path.replaceFirst(OrionConstantValues.PATH_DELIMITER, "");
		}
		return path;

	}
	public Credentials getCredsSupplier() {
		return this.credsSupplier.get();
	}

	/**
	 * @return userworkspace
	 */
	public String getUserWorkspace() {
		return this.credsSupplier.get().identity + OrionConstantValues.ORION_USER_CONTENT_ENDING;
	}
}
