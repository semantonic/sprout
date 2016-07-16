package org.semantonic.sprout.boundary;

import static org.semantonic.sprout.helper.test.JsonTestHelper.assertDeSerializationRoundtrip;
import static org.semantonic.sprout.helper.test.TestdataReader.readJsonForThisMethod;

import org.junit.Test;

public class JsonDeSerializationTest {

	@Test
	public void shouldDeSerializeDatasetDTO() throws Exception {
		final String expected = readJsonForThisMethod();
		assertDeSerializationRoundtrip(DatasetDTO.class, expected);
	}

}
