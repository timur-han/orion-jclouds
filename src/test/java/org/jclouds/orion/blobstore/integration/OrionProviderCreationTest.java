package org.jclouds.orion.blobstore.integration;

import java.util.Calendar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.testng.annotations.Test;
@Test(groups = "unit", testName = "OrionApiMetadataTest")
public class OrionProviderCreationTest {
	
  
  private BlobStore blobStore;

  @BeforeSuite
  protected void setUp() throws Exception {
      BlobStoreContext context = ContextBuilder.newBuilder("orionblob").credentials("timur", "123456").build(BlobStoreContext.class);
      // create a container in the default location
      blobStore = context.getBlobStore();
  }
  
  @Test
  protected void createContainer() throws Exception{
	  blobStore.createContainerInLocation(null, "Container+" + Calendar.getInstance().getTimeInMillis());
  }
}
