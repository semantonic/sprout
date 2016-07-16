package org.semantonic.sprout;


import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semantonic.sprout.boundary.DatasetDTO;
import org.semantonic.sprout.boundary.SystemTestSession;
import org.semantonic.sprout.client.TestSproutClient;
import org.semantonic.sprout.setup.GuiceModule;

/**
 * Invokes operations with persistent side-effect from the client-side and
 * affirms that side-effect is visible to the client.
 */
public class BlackboxIT {

	private static SystemTestSession session;
	
	private TestSproutClient client;

	@BeforeClass
	public static void startJetty() throws Exception {
		session = new SystemTestSession(SystemTestSession.createDefaultJpaModule(), new GuiceModule());
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
	}

	@Test
	public void shouldCreateAndGetDataset() {
		final List<DatasetDTO> prevDatasets = client.getDatasets();

		final DatasetDTO newDataset = new DatasetDTO(null, "my_id", "my_name", "my_link");
		
		final URI newDatasetLocation = client.addNewDataset(newDataset);

		// fetch Dataset by location
		final DatasetDTO createdDataset = client.doGet(DatasetDTO.class, newDatasetLocation);
		assertContentIsEqual(newDataset, createdDataset);

		// fetch all Datasets
		final List<DatasetDTO> createdDatasets = client.getDatasets();
		createdDatasets.removeAll(prevDatasets);
		Assert.assertEquals(1, createdDatasets.size());
		assertContentIsEqual(newDataset, createdDatasets.get(0));
	}

	// TODO: test proper exception handling
	// @Test(expected=SomeExceptionType)
	// public void connectShouldFail() {
	// }

	private static void assertContentIsEqual(DatasetDTO expected, DatasetDTO actual) {
		Assert.assertNotNull(expected);
		Assert.assertNotNull(actual);

		Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "href"));
	}

}
