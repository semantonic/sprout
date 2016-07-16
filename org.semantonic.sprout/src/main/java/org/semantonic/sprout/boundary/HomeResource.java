package org.semantonic.sprout.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.semantonic.sprout.boundary.ui.PageHeaderExt;

@Path("")
public class HomeResource {

	@Inject	PageHeaderExt headerExt;
	
	@Inject
	public HomeResource(@Context final UriInfo uriInfo, ApiUriBuilderHolder holder) {
		holder.initBuilder(uriInfo);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHomeAsHtml() {
		return headerExt.pageHeader();
	}
//	run JettyLauncher -> http://localhost:9080/webapi/
}
