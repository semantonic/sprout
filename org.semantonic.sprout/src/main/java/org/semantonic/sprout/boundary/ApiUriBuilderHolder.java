package org.semantonic.sprout.boundary;

import javax.ws.rs.core.UriInfo;

public class ApiUriBuilderHolder {

	private boolean initialized;

	private ApiUriBuilder builder;

	public ApiUriBuilderHolder initBuilder(UriInfo uriInfo) {
		if (initialized) {
			throw new IllegalStateException("already initialized");
		}

		this.builder = ApiUriBuilder.fromUriInfo(uriInfo);
		this.initialized = true;
		return this;
	}

	public ApiUriBuilder getBuilder() {
		assertInitialized();
		return builder;
	}

	protected void assertInitialized() {
		if (!initialized) {
			throw new IllegalStateException("builders not initialized");
		}
	}

}
