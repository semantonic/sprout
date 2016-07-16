package org.semantonic.sprout.boundary;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semantonic.sprout.entity.DatasetId;

@RunWith(Parameterized.class)
public class ApiUriBuilderTest {

	private final ApiUriBuilder builder;
	private final Method method;
	private final Object[] params;

	public ApiUriBuilderTest(Method m, Object[] params) {
		this.method = m;
		this.params = params;
		this.builder = ApiUriBuilder.fromDummyBase();
	}

	@Test
	public void shouldInvokeBuilderMethod() throws Exception {
		URI location = (URI) method.invoke(builder, params);
		Assert.assertNotNull(String.format("%s returned null", method), location);
	}

	@Parameterized.Parameters
	public static List<Object[]> data() throws Exception {
		final List<Object[]> data = new ArrayList<Object[]>();
		
		for (Method m : ApiUriBuilder.class.getMethods()) {
			if (URI.class.equals(m.getReturnType())) {
				if (m.getParameterTypes().length == 0) {
					data.add(new Object[] { m, new Object[] {} });

				} else if (Arrays.equals(new Class[] { DatasetId.class }, m.getParameterTypes())) {
					data.add(new Object[] { m, new Object[] { DatasetId.create("foo") } });

				} else {
					throw new IllegalStateException();
				}
				
			} else if (m.isDefault()) {
				throw new IllegalStateException();
				
			} else {
				// ignore static methods
			}
		}
		return data;
	}
}
