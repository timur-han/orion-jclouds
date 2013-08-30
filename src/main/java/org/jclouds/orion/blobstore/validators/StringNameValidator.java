package org.jclouds.orion.blobstore.validators;

import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.orion.config.constans.OrionErrorExplanations;
import org.jclouds.predicates.Validator;

public class StringNameValidator extends Validator<String> {

	@Override
	public void validate(String containerName) throws IllegalArgumentException {
		if (containerName.contains(OrionConstantValues.PATH_DELIMITER)) {
			throw new IllegalArgumentException(
					OrionErrorExplanations.FORBIDDEN_SLASH);
		} else if (containerName
				.equals(OrionConstantValues.ORION_METADATA_PATH)) {
			throw new IllegalArgumentException(
					OrionErrorExplanations.FORBIDDEN_METADATA_NAME);
		}

	}
}
