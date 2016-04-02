package com.walmart.ticketservice.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.walmart.ticketservice.datastore.SeatHold;
import com.walmart.ticketservice.util.WalmartTicketServiceConstants;

/**
 * DAO class for SeatHold data
 * 
 * @author sgurung
 *
 */
public class SeatHoldDao {
	
	private Map<Integer, SeatHold> seatHolds = null;
	
	private VenueLevelDao venueLevelDao = null;

	/**
	 * Constructor
	 */
	public SeatHoldDao() {
		seatHolds = new HashMap<Integer, SeatHold>();
		venueLevelDao = new VenueLevelDao();
	}
	
	/**
	 * Persist a give SeatHold object
	 * 
	 * @param seatHold
	 */
	public void saveSeatHold(SeatHold seatHold) {
		
		if (null != seatHold) {
			seatHolds.put(seatHold.getId(), seatHold);
		}
	}
	
	/**
	 * Delete a SeatHold object associated with a given seatHoldId
	 * 
	 * @param seatHoldId
	 */
	public void deleteSeatHold(Integer seatHoldId) {
		
		if (null != seatHoldId) {
			seatHolds.remove(seatHoldId);
		}
	}
	
	/**
	 * Returns a SeatHold object associated with a given seatHoldId
	 * 
	 * @param seatHoldId
	 * @return SeatHold
	 */
	public SeatHold retrieveSeatHoldById(Integer seatHoldId) {
		
		SeatHold seatHold = null;
		
		if (null != seatHoldId) {
			seatHold = seatHolds.get(seatHoldId);
		}
		
		return seatHold;
	}
	
	/**
	 * Delete all expired SeatHold objects
	 */
	public void deleteExpiredSeatHolds() {
		
		Iterator<Map.Entry<Integer, SeatHold>> iter = seatHolds.entrySet().iterator();
		
		while (iter.hasNext()) {
		    Map.Entry<Integer, SeatHold> entry = iter.next();
		    
		    SeatHold seatHold = entry.getValue();
		   
		    if(seatHold.isExpired()){
		    	
		    	venueLevelDao.updateSeatStatusInVenueLevel(seatHold.getVenueLevel(), seatHold.getSeatIds(), WalmartTicketServiceConstants.SEAT_STATUS.VACANT.toString());
		    	
		        iter.remove();
		    }
		}
	}

	/**
	 * @return the seatHolds
	 */
	public Map<Integer, SeatHold> getSeatHolds() {
		return seatHolds;
	}

	/**
	 * @param seatHolds the seatHolds to set
	 */
	public void setSeatHolds(Map<Integer, SeatHold> seatHolds) {
		this.seatHolds = seatHolds;
	}
	
	

}
