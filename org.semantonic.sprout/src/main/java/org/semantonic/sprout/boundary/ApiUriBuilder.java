package org.semantonic.sprout.boundary;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.semantonic.sprout.boundary.BoundaryConstants.DATASET_ID;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Path;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.semantonic.sprout.boundary.DatasetsResource.AddNewDataset;
import org.semantonic.sprout.boundary.DatasetsResource.GetDatasetById;
import org.semantonic.sprout.boundary.DatasetsResource.GetDatasets;
import org.semantonic.sprout.entity.DatasetId;

public class ApiUriBuilder {

	private final UriBuilder baseUriBuilder;

	public static ApiUriBuilder fromUriInfo(UriInfo uriInfo) {
		final URI baseUri = uriInfo.getBaseUri();
		return new ApiUriBuilder(baseUri);
	}

	public static ApiUriBuilder fromScratch(String scheme, String host, int port) throws URISyntaxException {
		final URI baseUri = new URI(scheme, null, host, port, BoundaryConstants.API_BASE_PATH, null, null);
		return new ApiUriBuilder(baseUri);
	}

	public static ApiUriBuilder fromDummyBase() {
		try {
			return fromScratch("http", "localhost", 8080);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private ApiUriBuilder(URI baseUri) {
		checkNotNull(baseUri);
		this.baseUriBuilder = UriBuilder.fromUri(baseUri);
	}

	// --------------------------------------------

	public URI buildGetDatasetsURI() {
		final Method m = DatasetsResource.methodMarkers.resolveOrFail(GetDatasets.class);
		final UriBuilder builder = createUriBuilder(DatasetsResource.class, m);
		return builder.build();
	}

	public URI buildGetDatasetByIdURI(DatasetId id) {
		final Method m = DatasetsResource.methodMarkers.resolveOrFail(GetDatasetById.class);
		final UriBuilder builder = createUriBuilder(DatasetsResource.class, m);
		return builder.resolveTemplate(DATASET_ID, id.getValueAsString()).build();
	}

	public URI buildAddNewDatasetURI() {
		final Method m = DatasetsResource.methodMarkers.resolveOrFail(AddNewDataset.class);
		final UriBuilder builder = createUriBuilder(DatasetsResource.class, m);
		return builder.build();
	}

	// --------------------------------------------
	private UriBuilder createUriBuilder(Class<?> resource) {
		final UriBuilder builder = this.baseUriBuilder.clone();
		builder.path(resource);
		return builder;
	}

	private UriBuilder createUriBuilder(Class<?> resource, Method method) {
		final UriBuilder builder = createUriBuilder(resource);
		if (method.isAnnotationPresent(Path.class)) {
			builder.path(method);
		}
		return builder;
	}

}
