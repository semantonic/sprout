package org.semantonic.sprout.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

public class DatasetId extends UriValue {
	
	public static DatasetId create(String value) throws InvalidUriValueException {
		final URI uri = parseToURI(value);
		final DatasetId result = new DatasetId(uri);
		return result;
	}

	public static DatasetId create(UriValue value) {
		final URI uri = value.getValue();
		final DatasetId result = new DatasetId(uri);
		return result;
	}
	
	protected DatasetId(URI value) {
		super(checkNotNull(value, "value"));
	}
	
	public boolean canEqual(Object other) {
		return other instanceof DatasetId;
	}

}
