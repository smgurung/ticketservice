package com.walmart.ticketservice.util;

/**
 * Defines constants used in the Walmart Ticket Service application
 * 
 * @author sgurung
 *
 */
public final class WalmartTicketServiceConstants {	
	
	public static enum Command {
		NUM_SEATS_AVAILABLE(0,"0. Get the number of seats available in a venue"),
		FIND_AND_HOLD_SEATS(1,"1. Find and hold Seats"),
		RESERVE_SEATS(2,"2. Reserve seats"),
        DISPLAY_SEATS_IN_VENUE_LEVEL(3,"3. Display seats in a venue level"),
        DISPLAY_ALL_VENUE_LEVELS(4,"4. Display all venue levels"),
        DISPLAY_ALL_SEAT_HOLDS(5, "5. Display all seat holds"),
        DISPLAY_ALL_SEAT_RESERVATIONS(6, "6. Display all seat reservations"),
        EXIT(7,"7. Exit");

        private final String message;
        private final int code;

        public static Command get(int code) {
            for(Command c : Command.values()) {
                if(code==c.code) {
                    return c;
                }
            }
            return null;
        }

        Command(int code, String message) {
            this.code= code;
            this.message = message;
        }
        public int getCode() { return this.code; }
        public String message() { return this.message; }
    }
	
	/**
     * Seat Statuses
     *
     */
    public static enum SEAT_STATUS {
        VACANT {
            public String toString() {
                return "V";
            }
        },
        HOLD {
            public String toString() {
                return "H";
            }
        },
        RESERVED {
            public String toString() {
                return "R";
            }
        };
    }
	
	/**
	 * Private constructor so that the caller is prevented from creating
	 * objects of this class
	 */
	private WalmartTicketServiceConstants() {
		//This prevents even the native class from 
	    //calling this constructor as well :
	    throw new AssertionError();
	}
}
