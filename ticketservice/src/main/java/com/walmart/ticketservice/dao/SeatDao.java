package com.walmart.ticketservice.dao;

import java.util.LinkedHashSet;
import java.util.Set;

import com.walmart.ticketservice.datastore.Seat;
import com.walmart.ticketservice.datastore.VenueLevel;
import com.walmart.ticketservice.util.WalmartTicketServiceConstants;

/**
 * DAO class for Seat data
 * 
 * @author sgurung
 *
 */
public class SeatDao {
	
	/**
	 * Constructor
	 */
	public SeatDao() {
		
	}

	/**
	 * Create and initialize Seat objects in a given VenueLevel object
	 * 
	 * @param venueLevel
	 * @return Set<Seat>
	 */
	public Set<Seat> createAndInitSeats(VenueLevel venueLevel) {

		String status = WalmartTicketServiceConstants.SEAT_STATUS.VACANT.toString();
		String code = null;
		char rowCode = 'A';
		Seat seat = null;
		 
		Set<Seat> seats = new LinkedHashSet<Seat>();
		 
    	//set all seats to available i.e. VACANT (V)
    	for (int i=0;i<venueLevel.getNumRows();i++) {
    		
    		for(int j=0; j<venueLevel.getNumSeatsInRow(); j++) {
    			
    			code = venueLevel.getId() + String.valueOf(rowCode) + ((String.valueOf(j).length() == 1)? "0" + String.valueOf(j+1):String.valueOf(j+1));
        		
    			seat = new Seat(rowCode, code, status, venueLevel.getId());
    			seats.add(seat);    			
    		}	
    		
    		rowCode++;
    	}
    	
    	return seats;
    }
	
	/**
	 * Returns the seats in a given VenueLevel object
	 * 
	 * @param venueLevel
	 * @return Set<Seat>
	 */
	public Set<Seat> getSeatsByVenue(VenueLevel venueLevel) {
				
		Set<Seat> seats = new LinkedHashSet<Seat>();
		
		if (null != venueLevel) {
			seats = venueLevel.getSeats();
		}
		
		return seats;
	}
	
}
