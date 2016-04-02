package com.walmart.ticketservice.datastore;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to represent a customer
 * 
 * @author sgurung
 *
 */
public class Customer {

	private static AtomicInteger nextId = new AtomicInteger();
	
	private int id;
		
	private String email = null;
	
	/**
	 * Constructor
	 */
	public Customer() {
		this.id = nextId.incrementAndGet();
	}
	
	/**
	 * Constructor
	 * 
	 * @param email
	 */
	public Customer(String email) {
		this.id = nextId.incrementAndGet();
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {		
		return "[ID=" + this.id + ",EMAIL=" + this.getEmail() + "]";
	}
	
}
