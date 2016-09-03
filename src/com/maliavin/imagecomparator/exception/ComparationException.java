package com.maliavin.imagecomparator.exception;

/**
 * Custom comparation runtime exception
 * 
 * @author Dmitriy
 *
 */
public class ComparationException extends RuntimeException{

	private static final long serialVersionUID = 6698885594535316755L;
	
	/**
	 * Initializes newly created ComparationException object with message
	 * 
	 * @param message
	 */
	public ComparationException(String message) {
		super(message);
	}

	/**
	 * Initializes newly created ComparationException object with cause exception
	 * 
	 * @param cause
	 */
	public ComparationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Initializes newly created ComparationException object with message and cause exception
	 * 
	 * @param message
	 * @param cause
	 */
	public ComparationException(String message, Throwable cause) {
		super(message, cause);
	}

}
