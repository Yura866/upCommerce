package com.bcn.startupers.upcommerce.exception;


public class UserAlreadyExistException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 9060866786597650520L;

	public UserAlreadyExistException() {
        super("Email address already in use, please provide another one");
    }

}
