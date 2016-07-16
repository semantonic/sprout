package org.semantonic.sprout.entity;

import java.util.List;
import java.util.Optional;

public class DatasetRepositoryProxy implements DatasetRepository {

	public DatasetRepository delegate;

	@Override
	public List<DatasetEntity> getAllDatasets() {
		return delegate.getAllDatasets();
	}

	@Override
	public Optional<DatasetEntity> getDatasetById(DatasetId id) {
		return delegate.getDatasetById(id);
	}

	@Override
	public Optional<DatasetEntity> getDatasetByInternalId(InternalId internalId) {
		return delegate.getDatasetByInternalId(internalId);
	}

	@Override
	public void addDataset(DatasetEntity relation) {
		delegate.addDataset(relation);
	}

}
