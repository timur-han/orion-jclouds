package org.jclouds.orion.blobstore.strategy.impl;

import org.jclouds.blobstore.strategy.ClearContainerStrategy;
import org.jclouds.domain.Credentials;
import org.jclouds.location.Provider;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.config.constans.OrionConstantValues;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

public class ClearAllFoldersInContainer implements ClearContainerStrategy {

    private final String workspaceName;
    private final OrionApi api;

    @Inject
    public ClearAllFoldersInContainer(@Provider Supplier<Credentials> creds,
	    OrionApi api) {
	Preconditions.checkNotNull(creds, "creds");
	this.workspaceName = creds.get().identity;
	this.api = api;
    }

    @Override
    public void execute(String containerName) {
	JsonObject workspaceObj = this.api.listContainerContents(
		getUserLocation(), containerName);
	JsonArray childerenNodes = workspaceObj.get(
		OrionConstantValues.WORKSPACE_CHILDREN).getAsJsonArray();
	for (JsonElement childElement : childerenNodes) {
	    api.deleteContainer(getUserLocation(), childElement
		    .getAsJsonObject().get("Name").getAsString());
	}
    }

    private String getUserLocation() {
	return this.workspaceName;
    }

}
