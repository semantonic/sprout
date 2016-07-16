package org.semantonic.sprout.entity;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semantonic.sprout.boundary.SystemTestSession;
import org.semantonic.sprout.setup.GuiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

/**
 * test DB queries
 */
public class DatasetRepositoryIT {

	private static Injector injector;
	private static DatasetRepositoryJPA repo;

	@BeforeClass
	public static void before() {
		injector = Guice.createInjector(SystemTestSession.createDefaultJpaModule(), new GuiceModule() {
			@Override
			protected void configure() {
				bind(DatasetRepositoryJPA.class);
			}
		});

		repo = injector.getInstance(DatasetRepositoryJPA.class);
		injector.getInstance(PersistService.class).start();
	}

	@AfterClass
	public static void after() {
		injector.getInstance(PersistService.class).stop();
	}

	// =============================================================

	@Test
	public void shouldQueryForAllDatasets() {
		final List<DatasetEntity> datasets = repo.getAllDatasets();
		Assert.assertNotNull(datasets);
	}

	@Test
	public void shouldQueryForDatasetByInternalId() {
		final InternalId internalId = InternalId.create(1L);
		repo.getDatasetByInternalId(internalId);
	}

	@Test
	public void shouldQueryForDatasetById() throws InvalidUriValueException {
		final DatasetId id = DatasetId.create("foo");
		repo.getDatasetById(id);
	}

	@Test
	public void shouldAddDataset() throws InvalidUriValueException {
		final DatasetId id = DatasetId.create(UriValues.random());
		final String name = "My Dataset";
		final UriValue link = UriValue.create(UriValues.random()) ;
		
		final DatasetEntity dataset = new DatasetEntity(id, name, link);
		repo.addDataset(dataset);
	}

}
