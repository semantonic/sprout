package org.semantonic.sprout.boundary.ui

import java.util.List
import javax.inject.Inject
import org.semantonic.sprout.boundary.ApiUriBuilderHolder
import org.semantonic.sprout.boundary.DatasetDTO

class DatasetTemplate {

	@Inject extension DomainExt domainExt
	@Inject extension HtmlExt htmlExt
	@Inject extension PageHeaderExt headerExt
	@Inject ApiUriBuilderHolder uriBuilderHolder

	def datasetOverviewPage(List<DatasetDTO> datasets) {
		'''
			«uriBuilderHolder.builder.buildAddNewDatasetHtmlFormURI().link("add dataset")»
			«datasets.map [ dataset |
				'''
					«dataset.asLink»
				'''
			].ul»
		'''.html(Section.Datasets.title, pageHeader)
	}

	def addNewDatasetForm() {
		'''
			<form action="«Section.Datasets.getLocation(uriBuilderHolder.builder)»" method="post">
			  <fieldset>
			    <legend>Add New Dataset:</legend>
			    
			    «DatasetDTO.P_ID.toFirstUpper»:<br>
			    <input type="text" name="«DatasetDTO.P_ID»" size="64" value="http://sprout.org/dataset/abcd1234"><br><br>
			    
			    «DatasetDTO.P_NAME.toFirstUpper»:<br>
			    <input type="text" name="«DatasetDTO.P_NAME»" size="64" value="foo i'm called"><br><br>
			    
			    «DatasetDTO.P_LINK.toFirstUpper»:<br>
			    <input type="text" name="«DatasetDTO.P_LINK»" size="64" value="some_link"><br><br>
			    
			    <input type="submit" value="Submit">
			  </fieldset>
			</form>
		'''.html(Section.Datasets.title, pageHeader)
	}

}
