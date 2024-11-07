package com.spring.hexa.exception;

public class InvalidCredentialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mssg;
	public InvalidCredentialsException(String mssg) {
		super();
		this.mssg = mssg;

}
	public String getMessage() {
		return mssg;
	}

}
