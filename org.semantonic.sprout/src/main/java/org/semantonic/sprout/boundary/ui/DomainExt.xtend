package org.semantonic.sprout.boundary.ui

import org.semantonic.sprout.boundary.DatasetDTO

class DomainExt {

	extension HtmlExt htmlExt = new HtmlExt

	def asLink(DatasetDTO it) '''«href.link(toString)»'''

//	def asTitle(DatasetDTO it) '''dataset: «asText»'''

//	def asText(DatasetDTO it) '''«name» («link.link(link)»)'''{
//		String.format("%s ", name)
//	}

//	// ----------------------------------------
//	def CharSequence ul(Map<String, Object> body) {
//		if (body.empty) {
//			"<i>empty</i>"
//		} else {
//			body.entrySet.map['''«key»: «value.valueAsText»'''].ul
//		}
//	}
//
//	private def dispatch valueAsText(Map<String, Object> value) {
//		value.ul
//	}
//
//	private def dispatch valueAsText(Object value) {
//		value.toString
//	}
	
}
