package org.semantonic.sprout.boundary;

import static org.semantonic.sprout.boundary.BoundaryConstants.DATASET_ID;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.semantonic.sprout.boundary.ui.DatasetTemplate;
import org.semantonic.sprout.entity.DatasetEntity;
import org.semantonic.sprout.entity.DatasetId;
import org.semantonic.sprout.entity.DatasetRepository;
import org.semantonic.sprout.entity.InvalidUriValueException;
import org.semantonic.sprout.helper.MethodMarker;
import org.semantonic.sprout.helper.MethodMarkers;

@Path("datasets")
public class DatasetsResource {
	
	public static final MethodMarkers methodMarkers = MethodMarkers.of(DatasetsResource.class);

	@Inject
	private DatasetRepository datasetRepo;

	@Inject
	private DatasetConverter datasetConverter;
	
	@Inject
	private DatasetTemplate template;
	
	private final ApiUriBuilder uriBuilder;
	
	@Inject
	public DatasetsResource(@Context final UriInfo uriInfo, ApiUriBuilderHolder holder) {
		this.uriBuilder = holder.initBuilder(uriInfo).getBuilder();
	}
	
	@GET @GetDatasets
	@Produces(MediaType.APPLICATION_JSON)
	public List<DatasetDTO> getDatasets() {
		final List<DatasetEntity> datasets = this.datasetRepo.getAllDatasets();

		final List<DatasetDTO> result = datasets.stream()
				.map(ds -> datasetConverter.convertToDTO(ds, uriBuilder))
				.collect(Collectors.toList());
		
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getDatasetsAsHtml() {
		final List<DatasetDTO> datasets = getDatasets();
		return template.datasetOverviewPage(datasets).toString();
	}
	
	@GET @GetDatasetById
	@Path("{" + DATASET_ID + "}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDatasetById(@PathParam(DATASET_ID) String id) {
		DatasetId dsId;
		try {
			dsId = DatasetId.create(id);
		} catch (InvalidUriValueException e) {
			// TODO proper input validation handling
			throw new RuntimeException(e);
		}
		final Optional<DatasetEntity> dataset = this.datasetRepo.getDatasetById(dsId);
		
		if (dataset.isPresent()) {
			final DatasetDTO result = datasetConverter.convertToDTO(dataset.get(), uriBuilder);
			return Response.ok(result).build();
			
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST @AddNewDataset
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewDataset(DatasetDTO in) {
		// TODO: input-validation
		
		final DatasetEntity newDataset = this.datasetConverter.createEntityForDataset(in);		
		this.datasetRepo.addDataset(newDataset);
		
		final URI location = uriBuilder.buildGetDatasetByIdURI(newDataset.getId());
		return Response.created(location).build();
	}
	
	@GET @AddNewDatasetHtmlForm
	@Path("addNewDatasetHtmlForm")
	@Produces(MediaType.TEXT_HTML)
	public String addNewDatasetHtmlForm() {
		return template.addNewDatasetForm().toString();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addNewDatasetFormSubmit(MultivaluedMap<String, String> formParams) {
		DatasetDTO in = new DatasetDTO(
				null,
				formParams.getFirst(DatasetDTO.P_ID),
				formParams.getFirst(DatasetDTO.P_NAME),
				formParams.getFirst(DatasetDTO.P_LINK)
				);
		
		Response r = addNewDataset(in);
		return Response.seeOther(r.getLocation()).build();
	}
	
	// -------------------------------
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@MethodMarker
	public @interface GetDatasetById {}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@MethodMarker
	public @interface AddNewDataset {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@MethodMarker
	public @interface AddNewDatasetHtmlForm {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@MethodMarker
	public @interface GetDatasets {}
	
}
