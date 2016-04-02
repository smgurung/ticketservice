package com.walmart.ticketservice.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sgurung
 *
 */
public class WalmartTicketServiceUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.util.WalmartTicketServiceUtil#generateUniqueCode()}.
	 */
	@Test
	public final void testGenerateUniqueCode() {
		String confirmationCode = WalmartTicketServiceUtil.generateUniqueCode();		
		assertNotNull(confirmationCode);
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.util.WalmartTicketServiceUtil#calculateTotalPrice(int, java.math.BigDecimal)}.
	 */
	@Test
	public final void testCalculateTotalPrice() {
		BigDecimal total = WalmartTicketServiceUtil.calculateTotalPrice(3, new BigDecimal(100.00));
		assertTrue(300 == total.intValue());
	}

}
