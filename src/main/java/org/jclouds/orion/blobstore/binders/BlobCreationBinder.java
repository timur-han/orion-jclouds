package org.jclouds.orion.blobstore.binders;

import org.jclouds.http.HttpRequest;
import org.jclouds.orion.config.constans.OrionHttpFields;
import org.jclouds.orion.domain.BlobType;
import org.jclouds.orion.domain.OrionBlob;
import org.jclouds.orion.domain.OrionSpecificFileMetadata;
import org.jclouds.orion.http.functions.OrionSpecificObject2JSON;
import org.jclouds.rest.Binder;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

public class BlobCreationBinder implements Binder {

	private final OrionSpecificFileMetadata metadata;
	private final OrionSpecificObject2JSON orionSpecificObject2JSON;

	@Inject
	public BlobCreationBinder(OrionSpecificFileMetadata metadata,
			OrionSpecificObject2JSON orionSpecificObject2JSON) {
		this.metadata = Preconditions
				.checkNotNull(metadata, "metadata is null");
		this.orionSpecificObject2JSON = Preconditions.checkNotNull(
				orionSpecificObject2JSON, "orionSpecificObject2JSON is null");
	}

	@Override
	public <R extends HttpRequest> R bindToRequest(R request, Object input) {
		OrionBlob blob = OrionBlob.class.cast(input);
		HttpRequest req = request
				.toBuilder()
				.replaceHeader(OrionHttpFields.HEADER_SLUG,
						blob.getProperties().getName()).build();
		if (blob.getProperties().getType() == BlobType.FILE_BLOB) {
			this.metadata.setDirectory(false);
		} else if (blob.getProperties().getType() == BlobType.FOLDER_BLOB) {
			this.metadata.setDirectory(true);
		} else {
			// fail since something is going wrong
			Preconditions.checkArgument(false, "metadata type is unknown");
			req = null;
		}
		req.setPayload(this.orionSpecificObject2JSON.apply(metadata));
		return (R) req;
	}
}
