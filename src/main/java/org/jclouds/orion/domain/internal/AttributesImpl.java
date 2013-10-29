package org.jclouds.orion.domain.internal;

import org.codehaus.jackson.annotate.JsonProperty;
import org.jclouds.orion.domain.Attributes;

public class AttributesImpl implements Attributes {
	@JsonProperty("ReadOnly")
	Boolean readOnly = false;
	@JsonProperty("Exectuable")
	Boolean executable = false;
	@JsonProperty("Hidden")
	Boolean hidden = false;
	@JsonProperty("Archive")
	Boolean archive = false;
	@JsonProperty("SymbolicLink")
	Boolean symbolicLink = false;

	/**
	 * @return the readOnly
	 */
	@Override
	public Boolean getReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly
	 *           the readOnly to set
	 */
	@Override
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the executable
	 */
	@Override
	public Boolean getExecutable() {
		return executable;
	}

	/**
	 * @param executable
	 *           the executable to set
	 */
	@Override
	public void setExecutable(Boolean executable) {
		this.executable = executable;
	}

	/**
	 * @return the hidden
	 */
	@Override
	public Boolean getHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *           the hidden to set
	 */
	@Override
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the archive
	 */
	@Override
	public Boolean getArchive() {
		return archive;
	}

	/**
	 * @param archive
	 *           the archive to set
	 */
	@Override
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	/**
	 * @return the symbolicLink
	 */
	@Override
	public Boolean getSymbolicLink() {
		return symbolicLink;
	}

	/**
	 * @param symbolicLink
	 *           the symbolicLink to set
	 */
	@Override
	public void setSymbolicLink(Boolean symbolicLink) {
		this.symbolicLink = symbolicLink;
	}

}
