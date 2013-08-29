package org.jclouds.orion.blobstore.integration;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.domain.StorageType;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "OrionApiMetadataTest")
public class OrionBlobStoreTests {

	String blobName = "servicetemplates/http%3A%2F%2Fwww.example.org%2Fwinery%2FTEST%2Fjclouds1/test1/test2/";
	private BlobStore blobStore;
	private final String container = "Container";
	String payload = "Payload Test String";

	@BeforeSuite
	protected void setUp() throws Exception {
		BlobStoreContext context = ContextBuilder.newBuilder("orionblob")
				.endpoint("http://127.0.0.1:8080")
				.credentials("timur", "123456").build(BlobStoreContext.class);
		// create a container in the default location
		blobStore = context.getBlobStore();
	}

	@AfterTest
	protected void tearDown() throws Exception {
		blobStore.deleteContainer(container);
	}

	@Test
	protected void createContainer() throws Exception {
		blobStore.createContainerInLocation(null, container);
	}

	@Test
	protected void deleteContainer() throws Exception {

		blobStore.deleteContainer(container);
		blobStore.createContainerInLocation(null, container);
		Assert.assertTrue(blobStore.containerExists(container),
				"Container SHOULD exist");
		Assert.assertTrue(!blobStore.containerExists(String.valueOf(Calendar
				.getInstance().getTimeInMillis())),
				"Container SHOULD NOT exist");
	}

	@Test
	protected void containerExists() throws Exception {

		blobStore.createContainerInLocation(null, container);
		Assert.assertTrue(blobStore.containerExists(container),
				"Container SHOULD exist");
		Assert.assertTrue(!blobStore.containerExists(String.valueOf(Calendar
				.getInstance().getTimeInMillis())),
				"Container SHOULD NOT exist");
	}

	@Test
	protected void putBlob() throws Exception {

		blobStore.createContainerInLocation(null, container);
		Assert.assertTrue(blobStore.containerExists(container),
				"Container SHOULD exist");
		Assert.assertTrue(!blobStore.containerExists(String.valueOf(Calendar
				.getInstance().getTimeInMillis())),
				"Container SHOULD NOT exist");

		Blob blob = blobStore.blobBuilder(blobName).build();
		blob.setPayload(payload);
		blobStore.putBlob(container, blob);
	}

	@Test
	protected void removeBlob() throws Exception {

		blobStore.createContainerInLocation(null, container);
		Assert.assertTrue(blobStore.containerExists(container),
				"Container SHOULD exist");
		Assert.assertTrue(!blobStore.containerExists(String.valueOf(Calendar
				.getInstance().getTimeInMillis())),
				"Container SHOULD NOT exist");

		Blob blob = blobStore.blobBuilder(blobName).payload("")
				.type(StorageType.FOLDER).build();
		blobStore.putBlob(container, blob);
		Assert.assertEquals(true, blobStore.blobExists(container, blobName));
		blobStore.removeBlob(container, blobName);
		Assert.assertEquals(false, blobStore.blobExists(container, blobName));
	}

	@Test
	protected void blobExists() throws Exception {

		blobStore.createContainerInLocation(null, container);

		Assert.assertEquals(blobStore.blobExists(container, blobName), false);
		Blob blob = blobStore.blobBuilder(blobName).build();
		blob.setPayload(payload);
		blobStore.putBlob(container, blob);
		Assert.assertEquals(blobStore.blobExists(container, blobName), true);
	}

	@Test
	protected void putBigBlob() throws Exception {

		blobStore.createContainerInLocation(null, container);
		Assert.assertTrue(blobStore.containerExists(container),
				"Container SHOULD exist");
		Assert.assertTrue(!blobStore.containerExists(String.valueOf(Calendar
				.getInstance().getTimeInMillis())),
				"Container SHOULD NOT exist");

		Blob blob = blobStore.blobBuilder(blobName).build();
		String pathName = getClass().getClassLoader()
				.getResource("Moodle.csar").getPath();
		File testFile = new File(pathName);
		InputStream iStream = FileUtils.openInputStream(testFile);
		blob.setPayload(iStream);
		blobStore.putBlob(container, blob);
	}

	@Test
	protected void getBlobMetadata() throws Exception {

		blobStore.createContainerInLocation(null, container);

		Blob blob = blobStore.blobBuilder(blobName).build();
		blob.setPayload(payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		blobStore.putBlob(container, blob);
		BlobMetadata metadata = blobStore.blobMetadata(container, blobName);
		Assert.assertEquals(metadata.getUserMetadata().containsKey("test"),
				true, "user metadata is not there");

	}

	@Test
	protected void getBlob() throws Exception {

		blobStore.createContainerInLocation(null, container);

		Blob blob = blobStore.blobBuilder(blobName).build();
		blob.setPayload(payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		blobStore.putBlob(container, blob);
		Blob returnBlob = blobStore.getBlob(container, blobName);
		ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
		IOUtils.copy(returnBlob.getPayload().getInput(), tempStream);

		Assert.assertEquals(payload, new String(tempStream.toByteArray()));

	}

	@Test
	protected void listBlobs() throws Exception {

		blobStore.createContainerInLocation(null, container);

		Blob blob = blobStore.blobBuilder(blobName).build();
		blob.setPayload(payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		blobStore.putBlob(container, blob);

		PageSet<? extends StorageMetadata> resultSet = blobStore.list();

	}

}
