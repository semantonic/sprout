package org.semantonic.sprout.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

public class UriValueTest {

	@Test
	public void shouldCreateFromString() throws InvalidUriValueException {
		final String value = "http://sprout.org";
		final UriValue uriValue = UriValue.create(value);
		
		Assert.assertNotNull(uriValue);
		Assert.assertEquals(value, uriValue.getValueAsString());
	}
	
	@Test(expected=InvalidUriValueException.class)
	public void shouldFailCreationOnNullValue() throws InvalidUriValueException {
		UriValue.create(null);
	}

	@Test(expected=InvalidUriValueException.class)
	public void shouldFailCreationOnInvalidValue() throws InvalidUriValueException {
		UriValue.create(" a");
	}

	@Test
	public void shouldCompareEqualByValue() throws InvalidUriValueException {
		final String value = "http://sprout.org";
		final UriValue uriValue1 = UriValue.create(value);
		final UriValue uriValue2 = UriValue.create(value);
		
		Assert.assertEquals(uriValue1, uriValue2);
		Assert.assertEquals(uriValue1.hashCode(), uriValue2.hashCode());
	}

	@Test
	public void shouldCompareNotEqualByValue() throws InvalidUriValueException {
		final UriValue uriValue1 = UriValue.create("foo");
		final UriValue uriValue2 = UriValue.create("bar");
		
		Assert.assertNotEquals(uriValue1, uriValue2);
		Assert.assertNotEquals(uriValue1.hashCode(), uriValue2.hashCode());
	}

	@Test
	public void shouldCompareNotEqualByType() throws InvalidUriValueException {
		final String value = "http://sprout.org";
		final UriValue uriValue1 = UriValue.create(value);
		final ZyxTestId uriValue2 = ZyxTestId.create(value);
		
		Assert.assertNotEquals(uriValue1, uriValue2);
		Assert.assertNotEquals(uriValue1.hashCode(), uriValue2.hashCode());
	}

	
	@Test
	public void shouldCreateOptionalFromString() throws InvalidUriValueException {
		final String value = "http://sprout.org";
		final Optional<UriValue> uriValueOpt = UriValue.ofNullable(value);
		
		Assert.assertNotNull(uriValueOpt);
		Assert.assertEquals(value, uriValueOpt.get().getValueAsString());
	}
	
	@Test
	public void shouldCreateOptionalOnNullValue() throws InvalidUriValueException {
		final Optional<UriValue> uriValueOpt = UriValue.ofNullable(null);
		
		Assert.assertNotNull(uriValueOpt);
		Assert.assertFalse(uriValueOpt.isPresent());
	}
	
	@Test(expected=InvalidUriValueException.class)
	public void shouldFailOptionalCreationOnInvalidValue() throws InvalidUriValueException {
		UriValue.ofNullable(" a");
	}

	@Test
	public void shouldGetStringValueFromOptional() throws InvalidUriValueException {
		final String value = "http://sprout.org";
		final Optional<UriValue> uriValueOpt = UriValue.ofNullable(value);
		
		Assert.assertEquals(value, UriValue.asString(uriValueOpt, null));
	}
	
	@Test
	public void shouldReturnOrElseInputForStringValueOfEmptyOptional() throws InvalidUriValueException {
		final Optional<UriValue> uriValueOpt = UriValue.ofNullable(null);
		
		final String orElse = "baz";
		Assert.assertEquals(orElse, UriValue.asString(uriValueOpt, orElse));
	}
	
	// ---------------------------------------------------------
	static class ZyxTestId extends UriValue {

		public static ZyxTestId create(String value) throws InvalidUriValueException {
			final URI uri = UriValue.parseToURI(value);
			final ZyxTestId result = new ZyxTestId(uri);
			return result;
		}

		private ZyxTestId(URI uri) {
			super(checkNotNull(uri, "uri"));
		}

		@Override
		public boolean canEqual(Object other) {
			return other instanceof ZyxTestId;
		}

	}

}
