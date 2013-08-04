package org.jclouds.orion;

import java.util.Properties;

import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

public class OrionProviderMetada extends BaseProviderMetadata {
	
	public OrionProviderMetada(Builder builder) {
		super(builder);
		// TODO Auto-generated constructor stub
	}
	
	public OrionProviderMetada() {
		super(new Builder());
	}
	
	
	static class Builder extends BaseProviderMetadata.Builder {
		
		@Override
		public org.jclouds.providers.internal.BaseProviderMetadata.Builder fromProviderMetadata(ProviderMetadata in) {
			super.fromProviderMetadata(in);
			return this;
		}
		
		Builder() {
			this.id("orion").name("Orion").defaultProperties(new Properties()).linkedService("bloborion").apiMetadata(new OrionApiMetadata()).endpoint("https://orionhub.org/");
		}
		
		@Override
		public ProviderMetadata build() {
			return new OrionProviderMetada(this);
		}
	}
}
