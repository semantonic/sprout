package org.semantonic.sprout.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DatasetRepositoryMock implements DatasetRepository {
	public final InternalIdSource datasetIdSource = new InternalIdSource();

	private final Map<DatasetId, DatasetEntity> datasets = new HashMap<DatasetId, DatasetEntity>();

	public DatasetEntity addedDataset;


	@Override
	public List<DatasetEntity> getAllDatasets() {
		return new ArrayList<DatasetEntity>(datasets.values());
	}

	@Override
	public Optional<DatasetEntity> getDatasetById(DatasetId id) {
		return Optional.ofNullable(datasets.get(id));
	}

	@Override
	public Optional<DatasetEntity> getDatasetByInternalId(InternalId internalId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addDataset(DatasetEntity dataset) {
		initializeInternalIdIfNull(dataset);
		if (datasets.containsKey(dataset.getId())) {
			throw new IllegalStateException(String.format("Repo already contains %s with %s[%d]",
					DatasetEntity.class.getName(), DatasetEntity.ID_FIELD_NAME, dataset.getId()));
		}

		this.datasets.put(dataset.getId(), dataset);
		this.addedDataset = dataset;
	}

	private InternalId initializeInternalIdIfNull(DatasetEntity dataset) {
		final Optional<InternalId> internalId = dataset.getInternalIdAsOptional(); 
		if ( ! internalId.isPresent()) {
			final InternalId newInternalId = this.datasetIdSource.nextId();
			newInternalId.applyTo(dataset);
		}
		
		return dataset.getInternalId();
	}

}
