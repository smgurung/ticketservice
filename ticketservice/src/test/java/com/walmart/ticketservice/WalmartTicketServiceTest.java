package com.walmart.ticketservice;

import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.walmart.ticketservice.datastore.SeatHold;

/**
 * @author sgurung
 *
 */
public class WalmartTicketServiceTest {
	
	private WalmartTicketService service = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = new WalmartTicketService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.WalmartTicketService#numSeatsAvailable(java.util.Optional)}.
	 */
	@Test
	public final void testNumSeatsAvailable() {
		assertTrue(service.numSeatsAvailable(Optional.of(1)) == 1250);
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.WalmartTicketService#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public final void testFindAndHoldSeats() {
			
		SeatHold seatHold = service.findAndHoldSeats(2, Optional.of(1), Optional.of(2), "shova@test.com");
		assertNotNull(seatHold);
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.WalmartTicketService#reserveSeats(int, java.lang.String)}.
	 */
	@Test
	public final void testReserveSeats() {
		SeatHold seatHold = service.findAndHoldSeats(2, Optional.of(1), Optional.of(2), "shova@test.com");
		assertNotNull(service.reserveSeats(seatHold.getId(), "shova@test.com"));
	}

}
