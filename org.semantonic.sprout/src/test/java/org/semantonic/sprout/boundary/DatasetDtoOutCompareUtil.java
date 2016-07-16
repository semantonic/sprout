package org.semantonic.sprout.boundary;

import java.net.URI;
import java.util.function.Function;

import org.junit.Assert;
import org.semantonic.sprout.entity.DatasetEntity;
import org.semantonic.sprout.entity.UriValue;

public class DatasetDtoOutCompareUtil {

	public static void assertContentIsEqual(DatasetEntity expected, DatasetDTO actual, ApiUriBuilder uriBuilder) {
		assertContentIsEqual(expected, actual, (dataset) -> uriBuilder.buildGetDatasetByIdURI(dataset.getId()));
	}

	private static void assertContentIsEqual(DatasetEntity expected, DatasetDTO actual,
			Function<DatasetEntity, URI> uriResolver) {
		Assert.assertNotNull(expected);
		Assert.assertNotNull(actual);

		Assert.assertEquals(expected.getId().getValueAsString(), actual.id);
		Assert.assertEquals(expected.getName().orElse(null), actual.name);
		Assert.assertEquals(UriValue.asString(expected.getLink(), null), actual.link);

		final URI expectedHref = uriResolver.apply(expected);
		final String actualHref = actual.href;
		Assert.assertEquals(expectedHref.toString(), actualHref);
	}

}
