package org.semantonic.sprout.entity;

import org.semantonic.sprout.setup.GuiceModule;

/**
 * Use proxy repositories in IntegrationTests to be able to replace/clear its
 * delegate for each testcase
 */
public class RepoProxies {
	private final DatasetRepositoryProxy relationRepoProxy = new DatasetRepositoryProxy();

	public void clearDelegates() {
		this.relationRepoProxy.delegate = null;
	}

	public void setRelationRepository(DatasetRepository relationRepo) {
		this.relationRepoProxy.delegate = relationRepo;
	}

	public GuiceModule createCustomModule() {
		return new GuiceModule() {
			@Override
			protected void bindPersistFilter() {
				// no persistence in ServiceIntegrationTests
			}

			@Override
			protected void bindRelationRepository() {
				bind(DatasetRepository.class).toInstance(relationRepoProxy);
			};

		};
	}

}