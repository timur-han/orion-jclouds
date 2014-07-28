package org.jclouds.orion.blobstore.strategy.internal;

import java.util.List;

import org.jclouds.blobstore.strategy.ClearContainerStrategy;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.OrionUtils;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.domain.OrionChildMetadata;

import com.google.inject.Inject;

public class ClearFilesInContainer implements ClearContainerStrategy {

	private final OrionApi api;
	private final OrionUtils orionUtils;

	@Inject
	public ClearFilesInContainer(OrionUtils orionUtils, OrionApi api) {
		this.orionUtils = orionUtils;
		this.api = api;
	}

	@Override
	public void execute(String containerName) {
		final List<OrionChildMetadata> children = this.api.listContainerContents(getUserWorkspace(), containerName);
		for (final OrionChildMetadata childFile : children) {
			String childPath = childFile.getLocation();
			while (childPath.startsWith(OrionConstantValues.PATH_DELIMITER)) {
				childPath = childPath.replaceFirst(OrionConstantValues.PATH_DELIMITER, "");
			}
			this.api.deleteGivenPath(childPath);
		}
	}

	private String getUserWorkspace() {
		return getOrionUtils().getUserWorkspace();
	}
	
	public OrionUtils getOrionUtils() {
		return this.orionUtils;
	}

}
