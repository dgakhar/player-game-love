package com.comeon.group.exception;

public class PlayerNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotExistException(String message) {
		super(message);
	}
}
