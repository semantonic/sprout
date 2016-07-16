package org.semantonic.sprout.entity;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class DatasetRepositoryJPA implements DatasetRepository {

	@Inject
	Provider<EntityManager> em;

	private static <T> Optional<T> singleOptionalResult(TypedQuery<T> q) {
		try {
			final T queryResult = q.getSingleResult();
			return Optional.of(queryResult);

		} catch (NoResultException ex) {
			return Optional.empty();
		}		
	}
	
	// TODO: remove, temp only
	@Transactional
	public List<DatasetEntity> getAllDatasets() {
		final TypedQuery<DatasetEntity> q = this.em.get().createQuery(
				"SELECT o FROM " + DatasetEntity.class.getName() + " AS o", DatasetEntity.class);
		final List<DatasetEntity> queryResult = q.getResultList();
		return queryResult;
	}

	@Transactional
	public Optional<DatasetEntity> getDatasetById(DatasetId id) {
		final TypedQuery<DatasetEntity> q = this.em.get().createQuery(
				"SELECT o FROM " + DatasetEntity.class.getName() + " AS o WHERE o.id = '" + id.getValue() + "'", DatasetEntity.class);

		return singleOptionalResult(q);
	}
	
	@Override
	public Optional<DatasetEntity> getDatasetByInternalId(InternalId internalId) {
		final TypedQuery<DatasetEntity> q = this.em.get().createQuery(
				"SELECT o FROM " + DatasetEntity.class.getName() + " AS o WHERE o.internalId = '" + internalId.getValue() + "'", DatasetEntity.class);

		return singleOptionalResult(q);
	}

	@Transactional
	public void addDataset(DatasetEntity relation) {
		em.get().persist(relation);
	}

}
