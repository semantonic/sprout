package org.semantonic.sprout.boundary.ui

import javax.inject.Inject
import org.semantonic.sprout.boundary.ApiUriBuilderHolder

class PageHeaderExt {

	@Inject	extension HtmlExt htmlExt
	
	@Inject	ApiUriBuilderHolder uriBuilderHolder

	def String pageHeader() '''
		«FOR section : Section.values SEPARATOR "|"»
			«section.getLocation(uriBuilderHolder.builder).link(section.title)»
		«ENDFOR»
	'''

}
