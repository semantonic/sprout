package org.semantonic.sprout.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class UriValues {

	@Deprecated
	// TODO: replace this with a more permanent solution 
	public static String random() {
		return random("http://randomid.sprout.org/_TEST_/");
	}
	
	@Deprecated
	public static String random(String prefix) {
		checkNotNull(prefix);

		final UUID uuid = UUID.randomUUID();

		final ByteBuffer buffer = ByteBuffer.allocate(2 * Long.BYTES);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());

		final String base64handle = Base64.getUrlEncoder().encodeToString(buffer.array());

		String result = prefix + base64handle;
		return result;
	}
	
}
