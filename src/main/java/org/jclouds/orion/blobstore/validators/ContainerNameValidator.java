package org.jclouds.orion.blobstore.validators;

import org.jclouds.predicates.Validator;

public class ContainerNameValidator extends Validator<String> {

	@Override
	public void validate(String containerName) throws IllegalArgumentException {
		if (containerName.contains("/")) {
			throw new IllegalArgumentException("Name should not contain '/'");
		}

	}

}
