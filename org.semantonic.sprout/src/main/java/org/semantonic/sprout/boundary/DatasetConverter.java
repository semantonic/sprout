package org.semantonic.sprout.boundary;

import java.net.URI;
import java.util.Optional;

import org.semantonic.sprout.entity.DatasetEntity;
import org.semantonic.sprout.entity.DatasetId;
import org.semantonic.sprout.entity.InvalidUriValueException;
import org.semantonic.sprout.entity.UriValue;

public class DatasetConverter {

	public DatasetDTO convertToDTO(DatasetEntity in, ApiUriBuilder uriBuilder) {
		final URI href = uriBuilder.buildGetDatasetByIdURI(in.getId());
		final DatasetDTO out = DatasetDTO.create(href, in.getId(), in.getName(), in.getLink());

		return out;
	}

	public DatasetEntity createEntityForDataset(DatasetDTO in) {
		try {
			final DatasetId id = DatasetId.create(in.id);
			final Optional<String> name = Optional.ofNullable(in.name);
			final Optional<UriValue> link = UriValue.ofNullable(in.link);

			final DatasetEntity out = new DatasetEntity(id, name, link);
			return out;
			
		} catch (InvalidUriValueException e) {

			// TODO: ValidationMessage, ValidationException
			throw new RuntimeException();
		}
	}

}
