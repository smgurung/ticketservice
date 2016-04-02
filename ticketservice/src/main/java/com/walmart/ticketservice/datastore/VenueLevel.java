package com.walmart.ticketservice.datastore;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to represent a VenueLevel
 * 
 * @author sgurung
 *
 */
public class VenueLevel {
	
	private static AtomicInteger nextId = new AtomicInteger();
	
	private Integer id;
	
	private String name = null;
	
	private BigDecimal pricePerSeat = null;
	
	private Integer numRows;
	
	private Integer numSeatsInRow;	

	private Set<Seat> seats = null;
	
	/**
	 * Default constructor
	 */
	public VenueLevel() {
		this.id = nextId.incrementAndGet();
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public VenueLevel(Integer id) {
		this.id = id;
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 * @param numRows
	 * @param numSeatsInRow
	 * @param pricePerSeat
	 */
	public VenueLevel(Integer id, String name, Integer numRows, Integer numSeatsInRow, BigDecimal pricePerSeat) {
		this.id = id;
		this.name = name;
		this.numRows = numRows;
		this.numSeatsInRow = numSeatsInRow;
		this.seats = new LinkedHashSet<Seat>();
		this.pricePerSeat = pricePerSeat;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pricePerSeat
	 */
	public BigDecimal getPricePerSeat() {
		return pricePerSeat;
	}

	/**
	 * @param pricePerSeat the pricePerSeat to set
	 */
	public void setPricePerSeat(BigDecimal pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}

	/**
	 * @return the numRows
	 */
	public Integer getNumRows() {
		return numRows;
	}

	/**
	 * @param numRows the numRows to set
	 */
	public void setNumRows(Integer numRows) {
		this.numRows = numRows;
	}

	/**
	 * @return the numSeatsInRow
	 */
	public Integer getNumSeatsInRow() {
		return numSeatsInRow;
	}

	/**
	 * @param numSeatsInRow the numSeatsInRow to set
	 */
	public void setNumSeatsInRow(Integer numSeatsInRow) {
		this.numSeatsInRow = numSeatsInRow;
	}

	/**
	 * @return the seats
	 */
	public Set<Seat> getSeats() {
		return seats;
	}

	/**
	 * @param seats the seats to set
	 */
	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	/**
	 * Add a seat to the venue level
	 * 
	 * @param seat
	 */
	public void addSeat(Seat seat) {
		seat.setVenueLevelId(this.getId());
		seats.add(seat);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {		
		String objectToString =  "[ ID=" + this.id + ",NAME=" + this.getName() + ",NUMROWS=" + this.getNumRows() + ",NUMSEATSPERROW=" + this.getNumSeatsInRow() + ",PRICEPERSEAT=" + this.getPricePerSeat() + "]";
		//objectToString += "[" + this.getSeats().toString() +  "]";
		
		return objectToString;
	}
}
