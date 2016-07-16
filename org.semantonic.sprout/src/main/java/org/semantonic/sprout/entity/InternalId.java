package org.semantonic.sprout.entity;

import static com.google.common.base.Preconditions.checkNotNull;

public class InternalId {

	// never null
	protected final Long value; // relevant for relational JPA persistence and
								// REST resource location url

	private InternalId(Long value) {
		this.value = value;
	}

	public static InternalId create(Long value) {
		final InternalId result = new InternalId(checkNotNull(value, "value"));
		return result;
	}

	public Long getValue() {
		return value;
	}

	public void applyTo(DatasetEntity dataset) {
		dataset.presetInternalId(this);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
