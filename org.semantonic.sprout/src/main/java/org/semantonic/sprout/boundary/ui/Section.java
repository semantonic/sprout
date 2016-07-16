package org.semantonic.sprout.boundary.ui;

import java.net.URI;

import org.semantonic.sprout.boundary.ApiUriBuilder;

public enum Section {

	Datasets("datasets");

	public final String title;

	private Section(String title) {
		this.title = title;
	}

	public URI getLocation(ApiUriBuilder uriBuilder) {
		switch (this) {
		case Datasets:
			return uriBuilder.buildGetDatasetsURI();
		default:
			throw new IllegalStateException();
		}
	}

}
