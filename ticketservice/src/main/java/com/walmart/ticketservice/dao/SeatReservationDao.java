package com.walmart.ticketservice.dao;

import java.util.HashMap;
import java.util.Map;

import com.walmart.ticketservice.datastore.SeatReservation;

/**
 * DAO for SeatReservation data
 * 
 * @author sgurung
 *
 */
public class SeatReservationDao {

	private Map<Integer, SeatReservation> seatReservations = null;

	/**
	 * Constructor
	 */
	public SeatReservationDao() {
		seatReservations = new HashMap<Integer, SeatReservation>();
	}
	
	/**
	 * Persist a give SeatReservation object
	 * 
	 * @param seatReservation
	 */
	public void saveSeatReservation(SeatReservation seatReservation) {
		
		if (null != seatReservation) {			
			seatReservations.put(seatReservation.getId(), seatReservation);
		}
	}
	
	/**
	 * Delete a SeatReservation object associated with a given seatReservationId
	 * 
	 * @param seatReservationId
	 */
	public void deleteSeatReservation(Integer seatReservationId) {
		
		if (null != seatReservationId) {
			seatReservations.remove(seatReservationId);
		}
	}
	
	/**
	 * Returns a SeatReservation object association with a given seatReservationId
	 * 
	 * @param seatReservationId
	 * @return SeatReservation
	 */
	public SeatReservation retrieveSeatReservationById(Integer seatReservationId) {
		
		SeatReservation seatReservation = null;
		
		if (null != seatReservationId) {
			seatReservation = seatReservations.get(seatReservationId);
		}
		
		return seatReservation;
	}

	/**
	 * @return the seatReservations
	 */
	public Map<Integer, SeatReservation> getSeatReservations() {
		return seatReservations;
	}

	/**
	 * @param seatReservations the seatReservations to set
	 */
	public void setSeatReservations(Map<Integer, SeatReservation> seatReservations) {
		this.seatReservations = seatReservations;
	}


}
