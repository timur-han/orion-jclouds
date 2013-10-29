package org.jclouds.orion.blobstore.strategy.internal;

import java.util.List;

import org.jclouds.blobstore.strategy.ClearContainerStrategy;
import org.jclouds.domain.Credentials;
import org.jclouds.location.Provider;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.domain.OrionChildMetadata;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

public class ClearFilesInContainer implements ClearContainerStrategy {

	private final String workspaceName;
	private final OrionApi api;

	@Inject
	public ClearFilesInContainer(@Provider Supplier<Credentials> creds, OrionApi api) {
		Preconditions.checkNotNull(creds, "creds");
		this.workspaceName = creds.get().identity;
		this.api = api;
	}

	@Override
	public void execute(String containerName) {
		List<OrionChildMetadata> children = this.api.listContainerContents(getUserWorkspace(), containerName);
		for (OrionChildMetadata childFile : children) {
			String childPath = childFile.getLocation();
			while (childPath.startsWith(OrionConstantValues.PATH_DELIMITER)) {
				childPath = childPath.replaceFirst(OrionConstantValues.PATH_DELIMITER, "");
			}
			api.deleteGivenPath(childPath);
		}
	}

	private String getUserWorkspace() {
		return this.workspaceName;
	}

}
