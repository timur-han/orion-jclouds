package org.jclouds.orion;

import org.jclouds.providers.internal.BaseProviderMetadataTest;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "OrionBlobProviderTest")
public class OrionProviderMetadaTest extends BaseProviderMetadataTest {
	
	public OrionProviderMetadaTest() {
		super(new OrionProviderMetadata(), new OrionApiMetadata());
	}
	
}
