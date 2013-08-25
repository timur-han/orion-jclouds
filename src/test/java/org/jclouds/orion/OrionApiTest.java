package org.jclouds.orion;

import java.util.Calendar;

import org.jclouds.apis.BaseApiLiveTest;
import org.jclouds.blobstore.options.ListOptions;
import org.jclouds.reflect.Reflection2;

import com.google.common.reflect.Invokable;

public class OrionApiTest extends BaseApiLiveTest<OrionApi> {

	void createContainerTest() {
		//Invokable<?, ?> method = Reflection2.method(OrionApi.class,
			//	"listContainers", ListOptions[].class);
		api.createContainerAsAProject("Container" + Calendar.getInstance().getTime(), "")
	}

	@Override
	public OrionProviderMetadata createProviderMetadata() {
		return new OrionProviderMetadata();
	}
}
