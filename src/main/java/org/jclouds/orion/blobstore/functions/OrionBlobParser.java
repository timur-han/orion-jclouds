package org.jclouds.orion.blobstore.functions;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.blobstore.domain.Blob;

import com.google.common.base.Function;

public class OrionBlobParser implements Function<Object, String> {

    @Override
    public String apply(Object blobObj) {
	Blob blob = ((Blob) blobObj);
	String[] pathList = blob.getMetadata().getName().split("/");
	List<String> pathArrayList = new ArrayList<String>();
	for (int index = 0; index < (pathList.length - 1); index++) {
	    String path = pathList[index];
	    if (!path.equals("")) {
		pathArrayList.add(path);
	    }

	}
	createPathRecursively(container, pathArrayList);
	return blob.getMetadata().getName();
    }
}
