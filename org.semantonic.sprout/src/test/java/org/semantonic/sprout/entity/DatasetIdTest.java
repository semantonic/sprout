package org.semantonic.sprout.entity;

import org.junit.Assert;
import org.junit.Test;
import org.semantonic.sprout.entity.UriValueTest.ZyxTestId;

public class DatasetIdTest {

	@Test
	public void shouldCreateFromString() throws InvalidUriValueException {
		final String value = "http://sprout.org/dataset/abcd1234";
		final DatasetId id = DatasetId.create(value);

		Assert.assertNotNull(id);
		Assert.assertEquals(value, id.getValueAsString());
	}

	@Test(expected = InvalidUriValueException.class)
	public void shouldFailCreationOnNullStringParam() throws InvalidUriValueException {
		DatasetId.create((String) null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailCreationOnNullUriValueParam() {
		DatasetId.create((UriValue) null);
	}

	@Test(expected = InvalidUriValueException.class)
	public void shouldFailCreationOnInvalidValue() throws InvalidUriValueException {
		DatasetId.create(" a");
	}

	@Test
	public void shouldCompareEqualByValue() throws InvalidUriValueException {
		final String value = "http://sprout.org/dataset/abcd1234";
		final DatasetId id1 = DatasetId.create(value);
		final DatasetId id2 = DatasetId.create(value);

		Assert.assertEquals(id1, id2);
		Assert.assertEquals(id1.hashCode(), id2.hashCode());
	}

	@Test
	public void shouldCompareNotEqualByValue() throws InvalidUriValueException {
		final DatasetId id1 = DatasetId.create("foo");
		final DatasetId id2 = DatasetId.create("bar");

		Assert.assertNotEquals(id1, id2);
		Assert.assertNotEquals(id1.hashCode(), id2.hashCode());
	}

	@Test
	public void shouldCompareNotEqualByType() throws InvalidUriValueException {
		final String value = "http://sprout.org";
		final DatasetId id1 = DatasetId.create(value);
		final UriValue uriValue1 = UriValue.create(value);
		final ZyxTestId uriValue2 = ZyxTestId.create(value);

		Assert.assertNotEquals(id1, uriValue1);
		Assert.assertNotEquals(id1.hashCode(), uriValue1.hashCode());

		Assert.assertNotEquals(id1, uriValue2);
		Assert.assertNotEquals(id1.hashCode(), uriValue2.hashCode());
	}

}
