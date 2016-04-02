package com.walmart.ticketservice.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.walmart.ticketservice.datastore.Seat;
import com.walmart.ticketservice.datastore.VenueLevel;
import com.walmart.ticketservice.util.WalmartTicketServiceConstants;

/**
 * @author sgurung
 *
 */
public class VenueLevelDaoTest {

	private VenueLevelDao venueLevelDao = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		venueLevelDao = new VenueLevelDao();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.VenueLevelDao#getVenueLevels()}.
	 */
	@Test
	public final void testGetVenueLevels() {		
		List<VenueLevel> venueLevels = venueLevelDao.getVenueLevels();
		assertNotNull(venueLevels);
		assertTrue(4 == venueLevels.size());
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.VenueLevelDao#find(int)}.
	 */
	@Test
	public final void testFind() {
		VenueLevel venueLevel = venueLevelDao.find(1);
		assertNotNull(venueLevel);
		assertTrue("Orchestra".compareToIgnoreCase(venueLevel.getName()) == 0);
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.VenueLevelDao#updateSeatStatusInVenueLevel(com.walmart.ticketservice.datastore.VenueLevel, java.util.Set, java.lang.String)}.
	 */
	@Test
	public final void testUpdateSeatStatusInVenueLevel() {
		VenueLevel venueLevel = venueLevelDao.find(1);		
		
		Iterator<Seat> iter = venueLevel.getSeats().iterator();
		Seat firstSeat = iter.next();
		assertTrue(firstSeat.getStatus().compareToIgnoreCase(WalmartTicketServiceConstants.SEAT_STATUS.VACANT.toString()) == 0);
		
		Set<Integer> seatIds = new HashSet<Integer>();
		seatIds.add(firstSeat.getId());
		
		venueLevelDao.updateSeatStatusInVenueLevel(venueLevel, seatIds, WalmartTicketServiceConstants.SEAT_STATUS.RESERVED.toString());
		assertTrue(firstSeat.getStatus().compareToIgnoreCase(WalmartTicketServiceConstants.SEAT_STATUS.RESERVED.toString()) == 0);
		
	}

}
