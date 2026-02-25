package com.comeon.group.exception;

public class GameNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameNotExistException(String message) {
		super(message);
	}
}