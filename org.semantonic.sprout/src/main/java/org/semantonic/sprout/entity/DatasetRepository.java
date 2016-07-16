package org.semantonic.sprout.entity;

import java.util.List;
import java.util.Optional;

public interface DatasetRepository {

	// TODO: use paging
	public List<DatasetEntity> getAllDatasets();

	public Optional<DatasetEntity> getDatasetById(DatasetId id);

	// TODO: to-be-removed .. or why do we need this one?
	public Optional<DatasetEntity> getDatasetByInternalId(InternalId internalId);

	public void addDataset(DatasetEntity dataset);

}
