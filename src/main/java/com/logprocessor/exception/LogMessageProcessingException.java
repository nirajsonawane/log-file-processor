package com.logprocessor.exception;

public class LogMessageProcessingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogMessageProcessingException(String message) {
		super(message);
	}

	public LogMessageProcessingException(Throwable t) {
		super(t);
	}

	public LogMessageProcessingException(String message, Throwable t) {
		super(message, t);
	}

}
