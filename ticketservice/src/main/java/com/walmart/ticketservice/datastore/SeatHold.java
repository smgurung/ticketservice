package com.walmart.ticketservice.datastore;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to represent a SeatHold
 * 
 * @author sgurung
 *
 */
public class SeatHold {	

	private static AtomicInteger nextId = new AtomicInteger();
	
	private Integer id;
		
	private Customer customer = null;
	
	private VenueLevel venueLevel = null;
		
	private Set<Integer> seatIds = new HashSet<Integer>();	
	
	private final ScheduledExecutorService scheduler;
	
    private volatile boolean expired = false;
         
	/**
	 * Constructor
	 * 
	 * @param customer
	 * @param venueLevel
	 * @param seatIds
	 * @param scheduler
	 * @param expireTime
	 */
	public SeatHold(Customer customer, VenueLevel venueLevel, Set<Integer> seatIds, ScheduledExecutorService scheduler, int expireTime) {
		this.id = nextId.incrementAndGet();
		this.customer = customer;
		this.venueLevel = venueLevel;
		this.seatIds = seatIds;
		this.scheduler = scheduler;
		this.setValidity(expireTime);
	}	    

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	

	/**
	 * @return the venueLevel
	 */
	public VenueLevel getVenueLevel() {
		return venueLevel;
	}

	/**
	 * @param venueLevel the venueLevel to set
	 */
	public void setVenueLevel(VenueLevel venueLevel) {
		this.venueLevel = venueLevel;
	}

	/**
	 * @return the seatIds
	 */
	public Set<Integer> getSeatIds() {
		return seatIds;
	}

	/**
	 * @param seatIds the seatIds to set
	 */
	public void setSeatIds(Set<Integer> seatIds) {
		this.seatIds = seatIds;
	}
	
	/**
	 * Set the validity 
	 * 
	 * @param seconds
	 */
	public void setValidity(final int seconds) {
        scheduler.schedule(new Runnable() {
            public void run() {
                expired = true;
            }
        }, seconds, TimeUnit.SECONDS);
    }
	
	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		return expired;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {		
		String objectToString =  "[ID=" + this.id + ",CUSTOMER=" + this.customer.toString() + ",ISEXPIRED=" + this.expired + ",SEATIDS=" + "[";
		objectToString += this.getSeatIds().toString()  +  "]";
		return objectToString;
	}
}
