package com.walmart.ticketservice;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

import com.walmart.ticketservice.datastore.SeatHold;
import com.walmart.ticketservice.util.WalmartTicketServiceConstants;
import com.walmart.ticketservice.util.WalmartTicketServiceConstants.Command;

/**
 * Walmart Ticket Service Application
 *
 * @author sgurung
 */
public class WalmartTicketServiceApplication 
{
	// Create a single shared Scanner for keyboard input
    private static Scanner scanner = new Scanner( System.in );
	
    public static void main( String[] args )
    {  
        Console console = System.console();
        if (console != null)
        {
        	console.printf("\n\nWelcome to the Walmart Ticket Service System.\n\n");     ;
            
        	WalmartTicketService service = new WalmartTicketService();
        	
        	int userChoice = 0;
        	
        	while (userChoice != WalmartTicketServiceConstants.Command.EXIT.getCode()) {
        	    userChoice = menu(console);   
        	    executeOption(console, service, userChoice);
	        }  
        	
        	scanner.close();
        } else {
            throw new RuntimeException("\nNo Console available");
        }
        
    }
    

    /**
     * Display the menu options and accept user input
     * 
     * @param console
     * @return selection User selected option
     */
    public static int menu(final Console console) {

    	console.printf("\n");
        console.printf("\n===================================================================================");
        console.printf("\n|                           WALMART TICKET SERVICE SYSTEM                           |");
        console.printf("\n====================================================================================");        
        console.printf("\n" + "MENU OPTIONS:");
        console.printf("\n   " + WalmartTicketServiceConstants.Command.NUM_SEATS_AVAILABLE.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.FIND_AND_HOLD_SEATS.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.RESERVE_SEATS.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.DISPLAY_SEATS_IN_VENUE_LEVEL.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.DISPLAY_ALL_VENUE_LEVELS.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.DISPLAY_ALL_SEAT_HOLDS.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.DISPLAY_ALL_SEAT_RESERVATIONS.message());
    	console.printf("\n   " + WalmartTicketServiceConstants.Command.EXIT.message());
    	
    	console.printf("\nPlease enter your option: ");
       
        return readInt();    
    }
    
    /**
     * Execute the user selected option and display the results to the console
     * 
     * @param console
     * @param app
     * @param userOption
     */
    public static void executeOption(final Console console, WalmartTicketService app, int userOption) {
    	
    	Command command = Command.get(userOption);
    	console.printf("\n------------------------------------------------------------------------------------");
    	console.printf("\n" + command.message().toUpperCase());
    	console.printf("\n------------------------------------------------------------------------------------\n");

        
        switch(command) {    		
    		case NUM_SEATS_AVAILABLE:    			
    			console.printf("\n\nEnter venueLevelId = ");       			
    			int venueLevelId = readInt();
    			
    			console.printf("\n\nNumber of seats available in Venue Level with ID " + venueLevelId + " = " + app.numSeatsAvailable(Optional.of(venueLevelId)));
    			break;
    		case FIND_AND_HOLD_SEATS:    			
    			console.printf("\n\nEnter Number of Seats to Hold = ");
    			int numSeats = readInt();  		
    			
    			console.printf("\nEnter Minimum Venue Level ID = ");
    			int minLevel = readInt();  
    			
    			console.printf("\nEnter Maximum Venue Level ID = ");
    			int maxLevel = readInt();  		
    			
    			console.printf("\nEnter Customer Email = ");
    			String email = null;
    			
    			if (scanner.hasNext()) {
    				email = scanner.next();
    			}
    			
    			SeatHold seatHold = app.findAndHoldSeats(numSeats, Optional.of(minLevel), Optional.of(maxLevel), email);  
    			 
    			if (seatHold != null) {
    				console.printf("\n\nCompleted seat hold request, seatHoldId = " + seatHold.getId() + ".");	
    			} else {
    				console.printf("\n\nUnable to complete seat hold request.");	
    			} 			
    			break;
    		case RESERVE_SEATS:
    			console.printf("\n\nEnter seatHoldId = ");
    			int seatHoldId = readInt();  		
    			
    			console.printf("\nEnter Customer Email = ");
    			String customerEmail = null;
    		
    			if (scanner.hasNext()) {
    				customerEmail = scanner.next();
    			}
    			
    			String confirmationCode = app.reserveSeats(seatHoldId, customerEmail);
    			
    			if (confirmationCode != null) {
    				console.printf("\n\nRequested seats have been reserved for customer with email, " + customerEmail + ". Confirmation Code = " + confirmationCode);        			
    			} else {
    				console.printf("\n\nUnable to complete seat reservation request.");	
    			}   			
    			break;
    		case DISPLAY_SEATS_IN_VENUE_LEVEL:
    			console.printf("\n\nEnter venueLevelId = ");       			
    			int venueLevel = readInt();
    			app.displaySeats(Optional.of(venueLevel));
    			break;
    		case DISPLAY_ALL_VENUE_LEVELS:
    			app.displayVenueLevels();
    			break;
    		case DISPLAY_ALL_SEAT_HOLDS:
    			app.displaySeatHolds();
    			break;
    		case DISPLAY_ALL_SEAT_RESERVATIONS:
    			app.displaySeatReservations();
    			break;
    		case EXIT:
    			console.printf("\nExiting application...");
    			System.exit(0);
    			break;
    		default:
    			console.printf("\n\nThe selected option is invalid. Please try again.");
    			break;
    			
    	}
    }
    
    /**
     * Read a numeric value from the console
     * 
     * @return value
     */
    private static int readInt() {
    	while (!scanner.hasNextInt()) {
		   System.out.println("Please enter a numeric value.");
		   scanner.nextLine();
		}
			
		int value = scanner.nextInt();
		return value;
    }
        
}



