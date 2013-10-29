package org.jclouds.orion;

import java.util.Properties;

import org.jclouds.orion.config.constans.OrionConstantValues;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

public class OrionProviderMetadata extends BaseProviderMetadata {

	static class Builder extends BaseProviderMetadata.Builder {

		Builder() {
			id(OrionConstantValues.ORION_ID).name("Orion Service").defaultProperties(new Properties())
			      .linkedService("bloborion").apiMetadata(new OrionApiMetadata()).endpoint(OrionConstantValues.END_POINT)
			      .iso3166Code("");
		}

		@Override
		public ProviderMetadata build() {
			return new OrionProviderMetadata(this);
		}

		@Override
		public Builder fromProviderMetadata(ProviderMetadata in) {
			super.fromProviderMetadata(in);
			return this;
		}
	}

	public OrionProviderMetadata() {
		super(new Builder());
	}

	public OrionProviderMetadata(Builder builder) {
		super(builder);
		// TODO Auto-generated constructor stub
	}
}
