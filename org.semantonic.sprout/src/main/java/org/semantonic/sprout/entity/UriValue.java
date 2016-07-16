package org.semantonic.sprout.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

public class UriValue {

	protected final URI value;

	public static UriValue create(String value) throws InvalidUriValueException {
		final URI uri = parseToURI(value);
		final UriValue result = new UriValue(uri);
		return result;
	}
	
	public static Optional<UriValue> ofNullable(String value) throws InvalidUriValueException {
		if (value == null) {
			return Optional.empty();
			
		} else {
			return Optional.of(create(value));
		}
	}
	
	public static String asString(Optional<UriValue> optUriValue, String orElse) {
		return optUriValue.isPresent() ? optUriValue.get().getValueAsString() : orElse;
	}

	protected static URI parseToURI(String value) throws InvalidUriValueException {
		if (value == null) {
			throw new InvalidUriValueException("<null>");
		}

		try {
			final URI uri = new URI(value);
			return uri;

		} catch (URISyntaxException ex) {
			throw new InvalidUriValueException(ex);
		}
	}

	protected UriValue(URI value) {
		this.value = checkNotNull(value, "value");
	}

	public URI getValue() {
		return value;
	}

	public String getValueAsString() {
		return value.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof UriValue)) {
			return false;
		}

		final UriValue that = (UriValue) other;
		final boolean canEqual = this.canEqual(that) && that.canEqual(this); 
		if ( ! canEqual) {
			return false;
		}

		return Objects.equals(this.value, that.value);
	}

	public boolean canEqual(Object other) {
		return other instanceof UriValue;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = 31 * hash + value.hashCode();
		hash = 31 * hash + getClass().hashCode();

		return hash;
	}

	@Override
	public String toString() {
		return getValueAsString();
	}

}
