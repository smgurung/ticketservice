package com.walmart.ticketservice.util;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 
 * Utility class for the WalmartTicketService application
 * 
 * @author sgurung
 *
 */
public class WalmartTicketServiceUtil {

	/**
	 * Constructor
	 */
	public WalmartTicketServiceUtil() {
	}

	/**
	 * Generate a unique code
	 * 
	 * @return code
	 */
	public static String generateUniqueCode() {		
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Calculate the total price given the number of seats and price per seat
	 * 
	 * @param numSeats
	 * @param pricePerSeat
	 * @return totalPrice
	 */
	public static BigDecimal calculateTotalPrice(int numSeats, BigDecimal pricePerSeat) {
		BigDecimal total = pricePerSeat.multiply(new BigDecimal(numSeats));
		return total;
	}
}
