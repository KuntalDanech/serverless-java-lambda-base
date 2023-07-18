package com.danech.exception;

/**
 * 
 * @author Kuntal Danech
 *
 */
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public BusinessException(){
		super();
	}
	
	public BusinessException(String message){
		super(message);
	}
	
}
