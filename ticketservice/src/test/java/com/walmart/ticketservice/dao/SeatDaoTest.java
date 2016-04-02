package com.walmart.ticketservice.dao;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.walmart.ticketservice.datastore.Seat;
import com.walmart.ticketservice.datastore.VenueLevel;

/**
 * @author sgurung
 *
 */
public class SeatDaoTest {
	
	private VenueLevelDao venueLevelDao = null;
	private SeatDao seatDao = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		venueLevelDao = new VenueLevelDao();
		seatDao = new SeatDao();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.SeatDao#getSeatsByVenue(com.walmart.ticketservice.datastore.VenueLevel)}.
	 */
	@Test
	public final void testGetSeatsByVenue() {
		
		VenueLevel venueLevel = venueLevelDao.find(1);
		assertNotNull(venueLevel);
		
		Set<Seat> seats = seatDao.getSeatsByVenue(venueLevel);
		assertNotNull(seats);
		
	}

}
