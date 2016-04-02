package com.walmart.ticketservice.exception;

/**
 * @author sgurung
 *
 */
public class WalmartTicketServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7000705200920578985L;

	/**
	 * 
	 */
	public WalmartTicketServiceException() {
	}

	/**
	 * @param arg0
	 */
	public WalmartTicketServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public WalmartTicketServiceException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public WalmartTicketServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public WalmartTicketServiceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
