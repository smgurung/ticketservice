package com.walmart.ticketservice;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.walmart.ticketservice.dao.CustomerDao;
import com.walmart.ticketservice.dao.SeatHoldDao;
import com.walmart.ticketservice.dao.SeatReservationDao;
import com.walmart.ticketservice.dao.VenueLevelDao;
import com.walmart.ticketservice.datastore.Customer;
import com.walmart.ticketservice.datastore.Seat;
import com.walmart.ticketservice.datastore.SeatHold;
import com.walmart.ticketservice.datastore.SeatReservation;
import com.walmart.ticketservice.datastore.VenueLevel;
import com.walmart.ticketservice.util.ConfigLookUp;
import com.walmart.ticketservice.util.WalmartTicketServiceConstants;
import com.walmart.ticketservice.util.WalmartTicketServiceUtil;

/**
 * Class that handles the functionalities of the Walmart Ticket Service Application
 * 
 * @author sgurung
 *
 */
public class WalmartTicketService implements TicketService {
	
	private static Logger logger = LogManager.getLogger(WalmartTicketService.class.getName());
	
	private VenueLevelDao venueLevelDao = null;
	private CustomerDao customerDao = null;
	private SeatHoldDao seatHoldDao = null;
	private SeatReservationDao seatReservationDao = null;
	private ScheduledExecutorService scheduler = null;
	private int seatHoldExpirationTime = 0;           
	
	/**
	 * Constructor
	 */
	public WalmartTicketService() {
		venueLevelDao = new VenueLevelDao();
		customerDao = new CustomerDao();
		seatHoldDao = new SeatHoldDao();
		seatReservationDao = new SeatReservationDao();
		scheduler = Executors.newScheduledThreadPool(5);
		
		// Read the seatHoldExpirationTime from the properties file
		seatHoldExpirationTime = Integer.parseInt(ConfigLookUp.getInstance().getConfigProperties().getProperty(ConfigLookUp.SEAT_HOLD_EXPIRATION_TIME));
	}

	
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.TicketService#numSeatsAvailable(java.util.Optional)
	 */
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		
		int numSeats = 0;
		
		seatHoldDao.deleteExpiredSeatHolds();
		
		if (venueLevel.isPresent()){
			// Get number of seats available in the given venue level
			VenueLevel level = venueLevelDao.find(venueLevel.get());
			
			if (level != null) {		
				logger.info("Found Venue Level with ID = " + venueLevel.get() + ". Processing request...");				
				numSeats = getNumberSeatsAvailableByVenueLevel(level);
			}
		} else {
			
			logger.info("Could not find Venue Level. Returning total number of seats available in all levels...");
			System.out.println("Could not find Venue Level. Returning total number of seats available in all levels...");
			
			// Get number of seats available in all venue levels if a specific venue is not provided
			List<VenueLevel> venueLevels = venueLevelDao.getVenueLevels();
			
			int venueLevelsSize = (null != venueLevels) ? venueLevels.size() : 0;
			
			for(int i=0; i<venueLevelsSize; i++) {
				numSeats += getNumberSeatsAvailableByVenueLevel(venueLevels.get(i));
			}
		}
			
    	return numSeats;    	
	}

	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.TicketService#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)
	 */
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) {
		
		seatHoldDao.deleteExpiredSeatHolds();
		
		int minLevelToHoldSeat = (minLevel.isPresent() && (minLevel.get().intValue() > 0)) ? minLevel.get().intValue() : 0;
		int maxLevelToHoldSeat = (maxLevel.isPresent() && (maxLevel.get().intValue() > 0)) ? maxLevel.get().intValue() : 0;
				
		if(numSeats == 0 || customerEmail == null
							|| !EmailValidator.getInstance().isValid(customerEmail)
								|| (minLevelToHoldSeat == 0 && maxLevelToHoldSeat == 0)) {
			logger.error("Could not process request. Invalid seat hold request provided.");	
			System.out.println("\nERROR: Could not process request. Invalid seat hold request provided.");			
			return null;
		}
		
		if (minLevelToHoldSeat > maxLevelToHoldSeat) {
			maxLevelToHoldSeat = minLevelToHoldSeat;
		}
		
		SeatHold seatHold = null;
		Set<Integer> heldSeatIds = new HashSet<Integer>();
				
		Customer customer = customerDao.getCustomerByEmail(customerEmail);
		if (customer == null) {
			logger.info("Customer does not exist in the system. New Customer with email, " + customerEmail + ", will be added to the database. Processing request...");
			System.out.println("\nCustomer does not exist in the system. New Customer with email, " + customerEmail + ", will be added to the database. Processing request...");
			
			customer = new Customer(customerEmail);
			customerDao.saveCustomer(customer);
		} else {
			logger.info("Found an existing Customer with email, " + customerEmail + ". Processing request...");			
		}
		
		// Find and hold seats within minLevel and maxLevel
		for (int i=minLevelToHoldSeat; i<=maxLevelToHoldSeat; i++) {
			
			VenueLevel venueLevel = venueLevelDao.find(i);
			
			if (venueLevel != null) {
				
				// Maximum number of seats that a customer can hold = number of seats in a row in a VenueLevel 
				if (numSeats > venueLevel.getNumSeatsInRow()) {
					System.out.println("\nCannot hold more than " + venueLevel.getNumSeatsInRow() + " in VenueLevel '" + venueLevel.getName());	
					continue;
				}
				
				heldSeatIds = findAndHoldSeats(venueLevel, numSeats);
								
				if (heldSeatIds.size() > 0) { 
					// Create the SeatHold and exit loop when seats are held successfully 
					seatHold = new SeatHold(customer, venueLevel, heldSeatIds, scheduler, seatHoldExpirationTime);	
					seatHoldDao.saveSeatHold(seatHold);
					
					break;
				}
			}
		}
				
		return seatHold;
	}

	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.TicketService#reserveSeats(int, java.lang.String)
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {
				
		String confirmationCode = null;
		
		// Remove expired SeatHold objects
		seatHoldDao.deleteExpiredSeatHolds();
		
		SeatHold seatHold = seatHoldDao.retrieveSeatHoldById(seatHoldId);
		if (seatHold == null) {
			logger.error("SeatHold with id, " + seatHoldId + ", not found in the system. Exiting request...");
			System.out.println("\nERROR: SeatHold with id, " + seatHoldId + ", not found in the system. Exiting request...");
			return confirmationCode;
		} 
		
		Customer customer = customerDao.getCustomerByEmail(customerEmail);
		if (customer == null) {
			logger.info("Customer with email, " + customerEmail + ", not found in the system. Exiting request...");
			System.out.println("\nERROR: Customer with email, " + customerEmail + ", not found in the system. Exiting request...");
			return confirmationCode;
		}
		
		logger.info("SeatHold with id, " + seatHoldId + ", and customer with email, " + customerEmail + ", found in the system. Processing request...");
		
		// Create SeatReservation object to hold the reservation details
		
		BigDecimal totalPrice = WalmartTicketServiceUtil.calculateTotalPrice(seatHold.getSeatIds().size(), seatHold.getVenueLevel().getPricePerSeat());
		confirmationCode = WalmartTicketServiceUtil.generateUniqueCode();
				
		SeatReservation seatReservation = new SeatReservation(customer, System.currentTimeMillis(), confirmationCode, totalPrice, seatHold.getSeatIds());
		seatReservationDao.saveSeatReservation(seatReservation);	
		
		// Delete the SeatHold object 
		seatHoldDao.deleteSeatHold(seatHoldId);
		
		// Update the seat status associated with the SeatReservation object to RESERVED
		venueLevelDao.updateSeatStatusInVenueLevel(seatHold.getVenueLevel(), seatHold.getSeatIds(), WalmartTicketServiceConstants.SEAT_STATUS.RESERVED.toString());

		return confirmationCode;
	}
	
	/**
	 * Returns the number of seats available in a given VenueLevel
	 * 
	 * @param level
	 * @return int Number of seats available
	 */
	public int getNumberSeatsAvailableByVenueLevel(VenueLevel level) {
		
		int numSeats = 0;
		
		if (null != level) {	
			
	    	for (Seat s : level.getSeats()) {
	    		if (WalmartTicketServiceConstants.SEAT_STATUS.VACANT.toString().equals(s.getStatus())) {
	    			numSeats++;
	    		}
	    	}
		}
		
		return numSeats;		
	}
	
    /**
     * Returns the number of seats available in a given row of a given VenueLevel 
     * 
     * @param level
     * @param rowCode
     * @return int Number of seats available
     */
    private int getNumSeatsAvailableByVenueLevelAndRow(VenueLevel level, char rowCode) {
    	
    	int numAvailable = 0;
    	
    	for (Seat s : level.getSeats()) {
    		
    		if (s.getRowCode().equals(rowCode) && WalmartTicketServiceConstants.SEAT_STATUS.VACANT.toString().equals(s.getStatus())) {
				numAvailable++;
			}
    	}
    	
    	return numAvailable;
    }
    
    /**
     * Returns the index of the first available seat in a given row of a given VenueLevel
     * 
     * @param level
     * @param rowCode
     * @return int Index of the first available seat in a given row of a given VenueLevel
     */
    private int getFirstSeatAvailableIndexByVenueLevelAndRow(VenueLevel level, char rowCode) {
    	
    	int index = 0;
    	
    	for (Seat s : level.getSeats()) {
    		if (s.getRowCode().equals(rowCode) &&  WalmartTicketServiceConstants.SEAT_STATUS.VACANT.toString().equals(s.getStatus())) {
    			break;
			}
    		index++;
    	}
    	
    	return index;
    }
    
    /**
     * Find and hold a given number of seats in a given VenueLevel
     * 
     * @param level
     * @param numSeatsToHold
     * @return Set<Integer> Set of seatIds
     */
    public Set<Integer> findAndHoldSeats(VenueLevel level, int numSeatsToHold) {
    	
    	Set<Integer> seatIds = new HashSet<Integer>();
    	boolean startHoldingSeats = false;
    	int numSeats = numSeatsToHold;
    	char previousRowCode = ' ';
    	int firstAvailableIndex = -1;
    	int lastSeatToHoldIndex = -1;
    	int currentIndex = 0;
    	 
    	for (Seat s : level.getSeats()) {    		
    		
    		if (getNumSeatsAvailableByVenueLevelAndRow(level, s.getRowCode()) >= numSeats) {    
    			
    			// Find the first row that can accommodate numSeatsToHold and hold the seats in that row  	
        		
    			firstAvailableIndex = getFirstSeatAvailableIndexByVenueLevelAndRow(level, s.getRowCode());	
        		
        		// Re-calculate the lastSeatToHoldIndex when row changes
        		if (!s.getRowCode().equals(previousRowCode)) {
        			lastSeatToHoldIndex = firstAvailableIndex + numSeatsToHold ;
        			startHoldingSeats = false;
        		}
        		
        		if(firstAvailableIndex == currentIndex) {
        			startHoldingSeats = true;
        		}	
    				
    			if (startHoldingSeats && firstAvailableIndex < lastSeatToHoldIndex) {     			 
    			  
    			  s.setStatus(WalmartTicketServiceConstants.SEAT_STATUS.HOLD.toString());
    			  seatIds.add(s.getId());
    			  
    			  numSeats--;
      			  firstAvailableIndex++;
    			} 
    			    
    			// Exit the loop when the requested number of seats are held
    			if(firstAvailableIndex == lastSeatToHoldIndex) {
    				break;
    			}	
    			
    		}

    		previousRowCode = s.getRowCode();
    		currentIndex++;
    	}
    	
    	return seatIds;
    }
    
    /**
	 * Remove all the expired SeatHold objects and set the status of the 
	 * associated seats to VACANT 
	 */
	public void removeExpiredSeatHolds() {
		seatHoldDao.deleteExpiredSeatHolds();		
	}

	/**
	 * Display all seats available in a given VenueLevel
	 * 
	 * @param venueLevel 
	 */
	public void displaySeats(Optional<Integer> venueLevel) {
	    	
		 if (venueLevel.isPresent()){
			 
			VenueLevel level = venueLevelDao.find(venueLevel.get());
			
			if (null != level) {
			 
		    	System.out.println("\nSEATS IN VENUE LEVEL '" + level.getName() + "' (V=VACANT, H=HOLD, R=RESERVED)");
		    	System.out.println("------------------------------------------------------------------------------------");
		    	 
		    	int seatsPerRowCount = 0;
		    	for (Seat s : level.getSeats()) {
		    		System.out.print(s.getCode() + ":" + s.getStatus() + "   ");
		    	    
		    	    seatsPerRowCount++;
		    	    
		    	    // Start a new line for next row
		    	    if (seatsPerRowCount == level.getNumSeatsInRow()) {
		    	    	 seatsPerRowCount = 0;
		    	    	 System.out.println();
		    	    }
		    	}
		    	
		    	System.out.println("Total Available Seats = " +  getNumberSeatsAvailableByVenueLevel(level) + "\n");
			}
		 } else {
			 logger.error("\nVenue not found.");
			 System.out.println("\nVenue not found.");
		 }
	}
	
	/**
	 * Display all SeatHold objects available in the system 
	 * 
	 */
	public void displaySeatHolds() {		
				 
		System.out.println("\nSEAT HOLDS");
		System.out.println("------------------------------------------------------------------------------------");
    	    	
		Iterator<Map.Entry<Integer, SeatHold>> iter = seatHoldDao.getSeatHolds().entrySet().iterator();
		
		while (iter.hasNext()) {
		    Map.Entry<Integer, SeatHold> entry = iter.next();
		    System.out.println(entry.getValue().toString());
		}		    
	}
	
	/**
	 * Display all SeatHold objects available in the system 
	 * 
	 */
	public void displaySeatReservations() {		
				 
		System.out.println("\nSEAT RESERVATIONS");
		System.out.println("------------------------------------------------------------------------------------");
    	    	
		Iterator<Map.Entry<Integer, SeatReservation>> iter = seatReservationDao.getSeatReservations().entrySet().iterator();
		
		while (iter.hasNext()) {
		    Map.Entry<Integer, SeatReservation> entry = iter.next();
		    System.out.println(entry.getValue().toString());
		}		    
	}
	
	/**
	 * Display all VenueLevel objects available in the system
	 * 
	 */
	public void displayVenueLevels() {
		
		System.out.println("\nVENUE LEVELS");
		System.out.println("------------------------------------------------------------------------------------");
    	
    	List<VenueLevel> venueLevels = venueLevelDao.getVenueLevels();
    	
		for (int i=0; i<venueLevels.size();i++) {
			System.out.println(venueLevels.get(i).toString());
		}
		    
	}
	   
}
