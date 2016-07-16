package org.semantonic.sprout.boundary;

import static org.semantonic.sprout.boundary.DatasetDTO.P_HREF;
import static org.semantonic.sprout.boundary.DatasetDTO.P_ID;
import static org.semantonic.sprout.boundary.DatasetDTO.P_LINK;
import static org.semantonic.sprout.boundary.DatasetDTO.P_NAME;

import java.net.URI;
import java.util.Optional;

import org.semantonic.sprout.entity.DatasetId;
import org.semantonic.sprout.entity.UriValue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ P_ID, P_HREF, P_NAME, P_LINK })
@JsonInclude(Include.NON_NULL)
public class DatasetDTO {

	public static final String P_HREF = "href";
	public static final String P_ID = "id";
	public static final String P_NAME = "name";
	public static final String P_LINK = "link";

	@JsonProperty(P_HREF)
	public final String href;
	@JsonProperty(P_ID)
	public final String id;
	@JsonProperty(P_NAME)
	public final String name;
	@JsonProperty(P_LINK)
	public final String link;

	public static DatasetDTO create(URI href, DatasetId id, Optional<String> name, Optional<UriValue> link) {
		final String linkValue = link.map(l -> l.getValueAsString()).orElse(null);
		final String hrefValue = href.toString();
		final DatasetDTO result = new DatasetDTO(hrefValue, id.getValueAsString(), name.orElse(null), linkValue);
		return result;
	}

	// TODO: does 'protected' also work here?
	public DatasetDTO(@JsonProperty(P_HREF) String href, @JsonProperty(P_ID) String id,
			@JsonProperty(P_NAME) String name, @JsonProperty(P_LINK) String link) {

		this.href = href;
		this.id = id;
		this.name = name;
		this.link = link;
	}

	// TODO: hashCode, equals

	@Override
	public String toString() {
		return String.format("DatasetDTO: %s [%s]", name, id);
	}

}
