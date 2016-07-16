package org.semantonic.sprout.entity;

public class InternalIdSource {

	private long id = 1L;

	public InternalId nextId() {
		return InternalId.create(this.id++);
	}

}
