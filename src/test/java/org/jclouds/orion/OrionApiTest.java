package org.jclouds.orion;

import java.util.Calendar;

import org.jclouds.apis.BaseApiLiveTest;

public class OrionApiTest extends BaseApiLiveTest<OrionApi> {

	void createContainerTest() {
		// Invokable<?, ?> method = Reflection2.method(OrionApi.class,
		// "listContainers", ListOptions[].class);
		api.createContainerAsAProject("Container"
				+ Calendar.getInstance().getTime(), "");
	}

	@Override
	public OrionProviderMetadata createProviderMetadata() {
		return new OrionProviderMetadata();
	}
}
