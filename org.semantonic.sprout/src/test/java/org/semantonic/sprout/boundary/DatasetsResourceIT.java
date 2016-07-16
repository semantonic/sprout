package org.semantonic.sprout.boundary;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semantonic.sprout.boundary.DatasetDTO;
import org.semantonic.sprout.client.TestSproutClient;
import org.semantonic.sprout.entity.DatasetEntity;
import org.semantonic.sprout.entity.DatasetId;
import org.semantonic.sprout.entity.DatasetRepositoryMock;
import org.semantonic.sprout.entity.InvalidUriValueException;
import org.semantonic.sprout.entity.RepoProxies;
import org.semantonic.sprout.entity.TestDatasetEntity;

// each testmethod invokes at most one method on the resource
public class DatasetsResourceIT {

	// TODO:
	// http://www.petervannes.nl/files/084d1067451c4f9a56f9b865984f803d-52.php

	private static final RepoProxies repoProxies = new RepoProxies();
	private static ServiceIntegrationTestSession session;

	private TestSproutClient client;
	private DatasetRepositoryMock datasetRepoMock;

	private DatasetEntity myDataset;

	@BeforeClass
	public static void startJetty() throws Exception {
		session = new ServiceIntegrationTestSession(repoProxies.createCustomModule());
		session.server.start();
	}

	@AfterClass
	public static void stopJetty() throws Exception {
		session.server.stop();
	}

	// =============================================================

	@Before
	public void setup() {
		this.client = session.getOrCreateSproutClient();
		// TODO: once we have shared API <if>, we could dynamically proxy
		// SproutClient
		// .. and assert that each method got invoked exactly once after all
		// testcases have run

		this.datasetRepoMock = new DatasetRepositoryMock();
		repoProxies.setRelationRepository(datasetRepoMock);

		this.myDataset = createMyDataset();
		this.datasetRepoMock.addDataset(myDataset);
	}

	@After
	public void tearDown() {
		repoProxies.clearDelegates();
	}

	private DatasetEntity createMyDataset() {
		final TestDatasetEntity result = new TestDatasetEntity(
				"http://sprout.org/dataset/42",
				this.datasetRepoMock.datasetIdSource.nextId().getValue(),
				"myDataset", null);

		return result;
	}

	// -------------------------------------------------------------

	@Test
	public void shouldGetDatasets() {
		final List<DatasetDTO> datasets = client.getDatasets();

		Assert.assertEquals(datasetRepoMock.getAllDatasets().size(), datasets.size());

		final Iterator<DatasetEntity> expectedIt = datasetRepoMock.getAllDatasets().iterator();
		final Iterator<DatasetDTO> actualIt = datasets.iterator();
		while (expectedIt.hasNext()) {
			DatasetDtoOutCompareUtil.assertContentIsEqual(expectedIt.next(), actualIt.next(), client.getUriBuilder());
		}
	}

	@Test
	public void shouldGetDatasetById() {
		final DatasetId id = this.myDataset.getId();

		final DatasetDTO dataset = client.getDatasetById(id).get();

		Assert.assertNotNull(dataset);

		final DatasetEntity expected = datasetRepoMock.getDatasetById(id).get();
		DatasetDtoOutCompareUtil.assertContentIsEqual(expected, dataset, client.getUriBuilder());
	}

	@Test
	public void shouldAddNewDataset() {
		final DatasetDTO newDataset = new DatasetDTO(null, "foo", "bar" + System.currentTimeMillis(), null);

		final URI location = client.addNewDataset(newDataset);

		final DatasetEntity actual = datasetRepoMock.addedDataset;
		Assert.assertNotNull(actual);
		Assert.assertEquals(newDataset.id, actual.getId().getValueAsString());
		Assert.assertEquals(newDataset.name, actual.getName().orElse(null));

		final URI expectedLocation = client.getUriBuilder().buildGetDatasetByIdURI(actual.getId());
		Assert.assertEquals(expectedLocation, location);
	}
	
	@Test
	public void shouldHandleUnknownDatasetGracefully() throws InvalidUriValueException {
		final Optional<DatasetDTO> datasetOpt = client.getDatasetById(DatasetId.create("unknown_id"));
		Assert.assertNotNull(datasetOpt);
		Assert.assertFalse(datasetOpt.isPresent());
	}

}
