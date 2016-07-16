package org.semantonic.sprout.entity;

public class InvalidPersistentDataException extends RuntimeException {
	private static final long serialVersionUID = 8596104618582527016L;

	public InvalidPersistentDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPersistentDataException(String message) {
		super(message);
	}

}
