package org.jclouds.orion.blobstore.integration;

import org.testng.annotations.BeforeMethod;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;

import org.testng.annotations.Test;
@Test(groups = "unit", testName = "OrionApiMetadataTest")
public class ProviderCreationTest {
	
  
  @Test
  protected void setUp() throws Exception {
      BlobStoreContext context = ContextBuilder.newBuilder("orion").build(BlobStoreContext.class);
      // create a container in the default location
      BlobStore blobStore = context.getBlobStore();
      
  }
}
