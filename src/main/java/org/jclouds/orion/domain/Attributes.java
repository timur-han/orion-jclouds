package org.jclouds.orion.domain;

public interface Attributes {

	/**
	 * @return the readOnly
	 */
	public abstract Boolean getReadOnly();

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public abstract void setReadOnly(Boolean readOnly);

	/**
	 * @return the executable
	 */
	public abstract Boolean getExecutable();

	/**
	 * @param executable
	 *            the executable to set
	 */
	public abstract void setExecutable(Boolean executable);

	/**
	 * @return the hidden
	 */
	public abstract Boolean getHidden();

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public abstract void setHidden(Boolean hidden);

	/**
	 * @return the archive
	 */
	public abstract Boolean getArchive();

	/**
	 * @param archive
	 *            the archive to set
	 */
	public abstract void setArchive(Boolean archive);

	/**
	 * @return the symbolicLink
	 */
	public abstract Boolean getSymbolicLink();

	/**
	 * @param symbolicLink
	 *            the symbolicLink to set
	 */
	public abstract void setSymbolicLink(Boolean symbolicLink);

}
