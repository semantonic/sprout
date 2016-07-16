package org.semantonic.sprout.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

import org.semantonic.sprout.boundary.ApiUriBuilder;
import org.semantonic.sprout.boundary.DatasetDTO;
import org.semantonic.sprout.entity.DatasetId;

public class SproutClient extends AbstractClient {

	protected final ApiUriBuilder uriBuilder;

	public SproutClient(String scheme, String host, int port) {
		try {
			this.uriBuilder = ApiUriBuilder.fromScratch(scheme, host, port);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public List<DatasetDTO> getDatasets() {
		final URI uri = this.uriBuilder.buildGetDatasetsURI();
		return doGet(new GenericType<List<DatasetDTO>>() {
		}, uri);
	}

	public Optional<DatasetDTO> getDatasetById(DatasetId id) {
		final URI uri = this.uriBuilder.buildGetDatasetByIdURI(id);
		try {
			DatasetDTO result = doGet(DatasetDTO.class, uri);
			return Optional.of(result);
		} catch (NotFoundException ex) {
			return Optional.empty();
		}
	}

	public URI addNewDataset(DatasetDTO dataset) {
		final URI uri = this.uriBuilder.buildAddNewDatasetURI();
		return doPost(dataset, uri);
	}

	// / ========================================================
	public static void main(String[] args) {
		final SproutClient client = new SproutClient("http", "localhost", 9080);

		for (DatasetDTO dataset : client.getDatasets()) {
			System.out.println(dataset);
		}
	}

}
