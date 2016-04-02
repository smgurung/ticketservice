package com.walmart.ticketservice.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.walmart.ticketservice.datastore.Seat;
import com.walmart.ticketservice.datastore.VenueLevel;

/**
 * DAO class VenueLevel data
 * 
 * @author sgurung
 *
 */
public class VenueLevelDao {

	private List<VenueLevel> venueLevels = new ArrayList<VenueLevel>();
	
	private SeatDao seatDao = null;
	
	/**
	 * Constructor
	 */
	public VenueLevelDao() {
		seatDao = new SeatDao();
		this.createAndInitVenueLevels();		
	}
	
	/**
	 * Create and initialize VenueLevel objects
	 */
	private void createAndInitVenueLevels() {
		
		VenueLevel orchestra = new VenueLevel(1, "Orchestra", 25, 50, new BigDecimal(100));
		orchestra.setSeats(seatDao.createAndInitSeats(orchestra));
		
		VenueLevel main = new VenueLevel(2, "Main", 20, 100, new BigDecimal(75));
		main.setSeats(seatDao.createAndInitSeats(main));
		
		VenueLevel balcony1 = new VenueLevel(3, "Balcony1", 15, 100, new BigDecimal(50));
		balcony1.setSeats(seatDao.createAndInitSeats(balcony1));
		
		VenueLevel balcony2 = new VenueLevel(4, "Balcony2", 15, 100, new BigDecimal(40));
		balcony2.setSeats(seatDao.createAndInitSeats(balcony2)); 
		 
//		VenueLevel orchestra = new VenueLevel(1, "Orchestra", 5, 5, new BigDecimal(100));
//		orchestra.setSeats(seatDao.createAndInitSeats(orchestra));
//		
//		VenueLevel main = new VenueLevel(2, "Main", 3, 8, new BigDecimal(75));
//		main.setSeats(seatDao.createAndInitSeats(main));
//		
//		VenueLevel balcony1 = new VenueLevel(3, "Balcony1", 2, 10, new BigDecimal(50));
//		balcony1.setSeats(seatDao.createAndInitSeats(balcony1));
//		
//		VenueLevel balcony2 = new VenueLevel(4, "Balcony2", 2, 10, new BigDecimal(40));
//		balcony2.setSeats(seatDao.createAndInitSeats(balcony2));
		
		venueLevels.add(orchestra);
		venueLevels.add(main);
		venueLevels.add(balcony1);
		venueLevels.add(balcony2);		
	}
			
	/**
	 * @return the venueLevels
	 */
	public List<VenueLevel> getVenueLevels() {
		return venueLevels;
	}

	/**
	 * @param venueLevels the venueLevels to set
	 */
	public void setVenueLevels(List<VenueLevel> venueLevels) {
		this.venueLevels = venueLevels;
	}

	/**
	 * Retrieve a VenueLevel object with given venueLevelId
	 * @param venueLevelId
	 * @return VenueLevel
	 */
	public VenueLevel find(int venueLevelId) {
		
		VenueLevel venueLevel = null;
		
		for (VenueLevel level: venueLevels) {
			if (level.getId() == venueLevelId) {
				venueLevel = level;
				break;
			}
		}
		
		return venueLevel;
	}
	    
	/**
	 * @return the seatDao
	 */
	public SeatDao getSeatDao() {
		return seatDao;
	}

	/**
	 * @param seatDao the seatDao to set
	 */
	public void setSeatDao(SeatDao seatDao) {
		this.seatDao = seatDao;
	}
    
    /**
     * Update the seat status of given Seat objects in a given VenueLevel
     * 
     * @param venueLevel
     * @param seatIds
     * @param seatStatus
     */
    public void updateSeatStatusInVenueLevel(VenueLevel venueLevel, Set<Integer> seatIds, String seatStatus) {
    	
    	if (venueLevel != null && seatIds != null && seatIds.size() > 0) {
    		
    		for (Seat s: venueLevel.getSeats()) {
    			
    			if (seatIds.contains(s.getId())) { 
    				s.setStatus(seatStatus);
    			}
    		}
    	}
    }

}
