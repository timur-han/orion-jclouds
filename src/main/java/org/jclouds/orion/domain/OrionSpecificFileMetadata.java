package org.jclouds.orion.domain;

/**
 * Orion file properties representation
 * 
 * @author Timur
 */
public interface OrionSpecificFileMetadata {

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param name
	 *           the name to set
	 */
	public abstract void setName(String name);

	/**
	 * @return the directory
	 */
	public abstract Boolean getDirectory();

	/**
	 * @param directory
	 *           the directory to set
	 */
	public abstract void setDirectory(Boolean directory);

	/**
	 * @return the eTag
	 */
	public abstract String geteTag();

	/**
	 * @param eTag
	 *           the eTag to set
	 */
	public abstract void seteTag(String eTag);

	/**
	 * @return the localTimeStamp
	 */
	public abstract Long getLocalTimeStamp();

	/**
	 * @param localTimeStamp
	 *           the localTimeStamp to set
	 */
	public abstract void setLocalTimeStamp(Long localTimeStamp);

	/**
	 * @return the location
	 */
	public abstract String getLocation();

	/**
	 * @param location
	 *           the location to set
	 */
	public abstract void setLocation(String location);

	/**
	 * @return the childrenLocation
	 */
	public abstract String getChildrenLocation();

	/**
	 * @param childrenLocation
	 *           the childrenLocation to set
	 */
	public abstract void setChildrenLocation(String childrenLocation);

	/**
	 * @return the attributes
	 */
	public abstract Attributes getAttributes();

	/**
	 * @param attributes
	 *           the attributes to set
	 */
	public abstract void setAttributes(Attributes attributes);

	/**
	 * @return the charSet
	 */
	public abstract String getCharSet();

	/**
	 * @param charSet
	 *           the charSet to set
	 */
	public abstract void setCharSet(String charSet);

	/**
	 * @return the contentType
	 */
	public abstract String getContentType();

	/**
	 * @param contentType
	 *           the contentType to set
	 */
	public abstract void setContentType(String contentType);

	/**
	 * @return the contentLegth
	 */
	public abstract Long getContentLegth();

	/**
	 * @param contentLegth
	 *           the contentLegth to set
	 */
	public abstract void setContentLegth(Long contentLegth);

}