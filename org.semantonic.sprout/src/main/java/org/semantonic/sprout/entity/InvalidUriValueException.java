package org.semantonic.sprout.entity;

import java.net.URISyntaxException;

public class InvalidUriValueException extends Exception {

	private static final long serialVersionUID = 1173188861214329013L;
	
	public InvalidUriValueException(URISyntaxException ex) {
		super("Invalid URI value", ex);
	}

	public InvalidUriValueException(String message) {
		super(message);
	}

}
