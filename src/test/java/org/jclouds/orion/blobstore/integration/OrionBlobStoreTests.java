package org.jclouds.orion.blobstore.integration;

import java.util.Calendar;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "OrionApiMetadataTest")
public class OrionBlobStoreTests {

    private BlobStore blobStore;

    @Test
    protected void containerExists() throws Exception {
	String container = "Container+"
		+ Calendar.getInstance().getTimeInMillis();
	blobStore.createContainerInLocation(null, container);
	Assert.assertTrue(blobStore.containerExists(container),
		"Container SHOULD exist");
	Assert.assertTrue(blobStore.containerExists(String.valueOf(Calendar
		.getInstance().getTimeInMillis())),
		"Container SHOULD NOT exist");
    }

    @Test
    protected void createContainer() throws Exception {
	blobStore.createContainerInLocation(null, "Container+"
		+ Calendar.getInstance().getTimeInMillis());
    }

    @BeforeSuite
    protected void setUp() throws Exception {
	BlobStoreContext context = ContextBuilder.newBuilder("orionblob")
		.credentials("timur", "123456").build(BlobStoreContext.class);
	// create a container in the default location
	blobStore = context.getBlobStore();
    }
}
