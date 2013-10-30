package org.jclouds.orion;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

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
import org.jclouds.blobstore.options.ListContainerOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "OrionApiMetadataTest")
public class OrionBlobStoreLiveTests {
	// properties file name
	String propsFileName = "endpoint.properties";
	// property constants
	final static String HOST_ADDRESS = "hostaddress";
	final static String USERNAME = "username";
	final static String PASSWORD = "password";

	String blobName = "servicetemplates/http%3A%2F%2Fwww.example.org%2Fwinery%2FTEST%2Fjclouds1/test1/";
	private BlobStore blobStore;
	private final String container = "Container";
	String payload = "Payload Test String";

	@BeforeSuite
	protected void setUp() throws Exception {
		Properties props = new Properties();

		props.load(this.getClass().getClassLoader().getResourceAsStream(this.propsFileName));
		BlobStoreContext context = ContextBuilder.newBuilder("orionblob").endpoint(props.getProperty(HOST_ADDRESS))
		      .credentials(props.getProperty(USERNAME), props.getProperty(PASSWORD)).build(BlobStoreContext.class);
		// create a container in the default location
		this.blobStore = context.getBlobStore();
	}

	@AfterTest
	protected void tearDown() throws Exception {
		this.blobStore.deleteContainer(this.container);
	}

	@Test
	protected void createContainer() throws Exception {
		this.blobStore.createContainerInLocation(null, this.container);
	}

	@Test
	protected void deleteContainer() throws Exception {

		this.blobStore.deleteContainer(this.container);
		this.blobStore.createContainerInLocation(null, this.container);
		Assert.assertTrue(this.blobStore.containerExists(this.container), "Container SHOULD exist");
		Assert.assertTrue(!this.blobStore.containerExists(String.valueOf(Calendar.getInstance().getTimeInMillis())),
		      "Container SHOULD NOT exist");
	}

	@Test
	protected void containerExists() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);
		Assert.assertTrue(this.blobStore.containerExists(this.container), "Container SHOULD exist");
		Assert.assertTrue(!this.blobStore.containerExists(String.valueOf(Calendar.getInstance().getTimeInMillis())),
		      "Container SHOULD NOT exist");
	}

	@Test
	protected void putBlob() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);
		Assert.assertTrue(this.blobStore.containerExists(this.container), "Container SHOULD exist");
		Assert.assertTrue(!this.blobStore.containerExists(String.valueOf(Calendar.getInstance().getTimeInMillis())),
		      "Container SHOULD NOT exist");

		Blob blob = this.blobStore.blobBuilder(this.blobName).build();
		blob.setPayload(this.payload);
		this.blobStore.putBlob(this.container, blob);
	}

	@Test
	protected void removeBlob() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);
		Assert.assertTrue(this.blobStore.containerExists(this.container), "Container SHOULD exist");
		Assert.assertTrue(!this.blobStore.containerExists(String.valueOf(Calendar.getInstance().getTimeInMillis())),
		      "Container SHOULD NOT exist");

		Blob blob = this.blobStore.blobBuilder(this.blobName).payload("").type(StorageType.FOLDER).build();
		this.blobStore.putBlob(this.container, blob);
		Assert.assertEquals(true, this.blobStore.blobExists(this.container, this.blobName));
		this.blobStore.removeBlob(this.container, this.blobName);
		Assert.assertEquals(false, this.blobStore.blobExists(this.container, this.blobName));
	}

	@Test
	protected void blobExists() throws Exception {
		this.blobStore.deleteContainer(this.container);
		this.blobStore.createContainerInLocation(null, this.container);

		Assert.assertEquals(this.blobStore.blobExists(this.container, this.blobName), false);
		Blob blob = this.blobStore.blobBuilder(this.blobName).build();
		blob.setPayload(this.payload);
		this.blobStore.putBlob(this.container, blob);
		Assert.assertEquals(this.blobStore.blobExists(this.container, this.blobName), true);
	}

	@Test
	protected void putBigBlob() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);
		Assert.assertTrue(this.blobStore.containerExists(this.container), "Container SHOULD exist");
		Assert.assertTrue(!this.blobStore.containerExists(String.valueOf(Calendar.getInstance().getTimeInMillis())),
		      "Container SHOULD NOT exist");

		Blob blob = this.blobStore.blobBuilder(this.blobName).build();
		String pathName = this.getClass().getClassLoader().getResource("SugarCRM.zip").getPath();
		File testFile = new File(pathName);
		InputStream iStream = FileUtils.openInputStream(testFile);
		blob.setPayload(iStream);
		this.blobStore.putBlob(this.container, blob);

		iStream = FileUtils.openInputStream(testFile);
		Blob returnedBlob = this.blobStore.getBlob(this.container, this.blobName);

		while (true) {
			int temp = iStream.read();
			// System.out.print(temp);
			Assert.assertEquals(returnedBlob.getPayload().getInput().read(), temp);
			if (temp == -1) {
				break;
			}
		}

	}

	@Test
	protected void getBlobMetadata() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);

		Blob blob = this.blobStore.blobBuilder(this.blobName).type(StorageType.FOLDER).build();
		blob.setPayload(this.payload);

		this.blobStore.putBlob(this.container, blob);
		blob = this.blobStore.blobBuilder(this.blobName).type(StorageType.FOLDER).build();
		blob.setPayload(this.payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		this.blobStore.putBlob(this.container, blob);

		BlobMetadata metadata = this.blobStore.blobMetadata(this.container, this.blobName);
		Assert.assertEquals(metadata.getUserMetadata().containsKey("test"), true, "user metadata is not there");

	}

	@Test
	protected void getBlob() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);

		Blob blob = this.blobStore.blobBuilder(this.blobName).build();
		blob.setPayload(this.payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		this.blobStore.putBlob(this.container, blob);
		Blob returnBlob = this.blobStore.getBlob(this.container, this.blobName);
		ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
		IOUtils.copy(returnBlob.getPayload().getInput(), tempStream);

		Assert.assertEquals(this.payload, new String(tempStream.toByteArray()));

	}

	@Test
	protected void listContainers() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);

		Blob blob = this.blobStore.blobBuilder(this.blobName).build();
		blob.setPayload(this.payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		this.blobStore.putBlob(this.container, blob);

		PageSet<? extends StorageMetadata> resultSet = this.blobStore.list();
		for (StorageMetadata data : resultSet) {
			System.out.println(data.getName());
		}

	}

	@Test
	protected void listBlobs() throws Exception {

		this.blobStore.createContainerInLocation(null, this.container);

		Blob blob = this.blobStore.blobBuilder(this.blobName).build();
		blob.setPayload(this.payload);
		blob.getMetadata().getUserMetadata().put("test", "test");
		this.blobStore.putBlob(this.container, blob);

		PageSet<? extends StorageMetadata> resultSet = this.blobStore.list(this.container,
		      ListContainerOptions.Builder.recursive());
		for (StorageMetadata data : resultSet) {
			System.out.println(data.getName());
		}

	}

}
