package org.semantonic.sprout.helper.test;

import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTestHelper {
	
	public static final ObjectMapper mapper = new ObjectMapper();

	public static <T> void assertDeSerializationRoundtrip(Class<T> dtoClass, String expectedJSON) throws Exception {
		final T dto = mapper.readValue(expectedJSON, dtoClass);
		final String actualJSON = mapper.writeValueAsString(dto);
		
		final String expectedJsonOnOneLine = expectedJSON
				.replace("\n", "").replace("\t", "")
				.replace("\": ", "\":");
		Assert.assertEquals(expectedJsonOnOneLine, actualJSON);
	}
	
}
