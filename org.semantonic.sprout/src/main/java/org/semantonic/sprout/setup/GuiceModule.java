package org.semantonic.sprout.setup;

import org.semantonic.sprout.boundary.ApiUriBuilderHolder;
import org.semantonic.sprout.boundary.DatasetConverter;
import org.semantonic.sprout.boundary.ui.DatasetTemplate;
import org.semantonic.sprout.boundary.ui.DomainExt;
import org.semantonic.sprout.boundary.ui.HtmlExt;
import org.semantonic.sprout.boundary.ui.PageHeaderExt;
import org.semantonic.sprout.entity.DatasetRepository;
import org.semantonic.sprout.entity.DatasetRepositoryJPA;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletScopes;

public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GuiceFilter.class);
		bindPersistFilter();

		bindRelationRepository();
		
		bind(DatasetConverter.class);
//		bind(com.fasterxml.jackson.databind.ObjectMapper.class);
		
		bind(ApiUriBuilderHolder.class).in(ServletScopes.REQUEST);
		
		bind(DatasetTemplate.class);
		
		bind(PageHeaderExt.class);
		bind(HtmlExt.class);
		bind(DomainExt.class);

		// TODO: http://code.google.com/p/google-guice/wiki/JPA -> comment about
		// bindIntercepter() to autoclear jpa session
	}
	
	protected void bindPersistFilter() {
		bind(PersistFilter.class);
	}

	protected void bindRelationRepository() {
		bind(DatasetRepository.class).to(DatasetRepositoryJPA.class).in(ServletScopes.REQUEST);		
	}
	
}
