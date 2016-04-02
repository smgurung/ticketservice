package com.walmart.ticketservice.datastore;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to represent a SeatRepresentation
 * 
 * @author sgurung
 *
 */
public class SeatReservation {

	private static AtomicInteger nextId = new AtomicInteger();
	
	private Integer id;
	
	private long reservationTimestamp = 0L;
	
	private Customer customer = null;
	
	private String confirmationCode = null;
		
	private Set<Integer> seatIds = new HashSet<Integer>();	
	
	private BigDecimal totalPrice = null;
	    
	/**
	 * Constructor
	 */
	public SeatReservation() {
		this.id = nextId.incrementAndGet();
	}	
	
	/**
	 * Constructor
	 * 
	 * @param customer
	 * @param reservationTimestamp
	 * @param confirmationCode
	 * @param totalPrice
	 * @param seatIds
	 */
	public SeatReservation(Customer customer, long reservationTimestamp, String confirmationCode, BigDecimal totalPrice, Set<Integer> seatIds) {
		this.id = nextId.incrementAndGet();
		this.customer = customer;
		this.reservationTimestamp = reservationTimestamp;
		this.confirmationCode = confirmationCode;
		this.totalPrice = totalPrice;
		this.seatIds = seatIds;
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
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the reservationTimestamp
	 */
	public long getReservationTimestamp() {
		return reservationTimestamp;
	}

	/**
	 * @param reservationTimestamp the reservationTimestamp to set
	 */
	public void setReservationTimestamp(long reservationTimestamp) {
		this.reservationTimestamp = reservationTimestamp;
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
	 * @return the confirmationCode
	 */
	public String getConfirmationCode() {
		return confirmationCode;
	}

	/**
	 * @param confirmationCode the confirmationCode to set
	 */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
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
	 * @return the totalPrice
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {		
		String objectToString =  "[ID=" + this.id + ",CUSTOMER=" + this.getCustomer().toString() + ",RESERVETIMESTAMP=" 
														+ this.getReservationTimestamp() + ",TOTALPRICE=" 
															+ this.getTotalPrice().toString() + ",CONFIRMATIONCODE=" 
																+ this.getConfirmationCode() + ",SEATIDS=" + "[";
		objectToString += this.getSeatIds().toString()  +  "]";
		
		return objectToString;
	}
}
