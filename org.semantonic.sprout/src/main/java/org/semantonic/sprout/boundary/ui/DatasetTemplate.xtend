package org.semantonic.sprout.boundary.ui

import java.util.List
import javax.inject.Inject
import org.semantonic.sprout.boundary.DatasetDTO

class DatasetTemplate {

	@Inject	extension DomainExt domainExt
	@Inject	extension HtmlExt htmlExt
	@Inject	extension PageHeaderExt headerExt
	
	def datasetOverviewPage(List<DatasetDTO> datasets) {
		datasets.map [ dataset |
			'''
				«dataset.asLink»
			'''
		].ul.html(Section.Datasets.title, pageHeader)
	}	

}
