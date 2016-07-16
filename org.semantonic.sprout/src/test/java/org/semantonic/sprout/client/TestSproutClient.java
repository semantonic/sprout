package org.semantonic.sprout.client;

import java.net.URI;

import org.semantonic.sprout.boundary.ApiUriBuilder;

public class TestSproutClient extends SproutClient {
	
	public TestSproutClient(String scheme, String host, int port) {
		super(scheme, host, port);
	}

	public ApiUriBuilder getUriBuilder() {
		return this.uriBuilder;
	}
	
	public <T> T doGet(Class<T> responseType, URI uri) {
		return super.doGet(responseType, uri);
	}

}
