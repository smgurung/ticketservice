package com.walmart.ticketservice.datastore;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to represent a Seat
 * 
 * @author sgurung
 *
 */
public class Seat {
	
	private static AtomicInteger nextId = new AtomicInteger();
	
	private Integer id;
	
	private String code = null;
	
	private Character rowCode = null;
	
	private String status = null;
	
	private Integer venueLevelId;
	
	/**
	 * Default constructor 
	 */
	public Seat() {
		this.id = nextId.incrementAndGet();
	}
	
	/**
	 * Constructor 
	 * 
	 * @param rowCode
	 * @param code
	 * @param status
	 * @param venueLevelId
	 */
	public Seat(Character rowCode, String code, String status,  Integer venueLevelId) {
		this.id = nextId.incrementAndGet();
		this.rowCode = rowCode;
		this.code = code;
		this.status = status;
		this.venueLevelId = venueLevelId;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the venueLevelId
	 */
	public Integer getVenueLevelId() {
		return venueLevelId;
	}

	/**
	 * @param venueLevelId the venueLevelId to set
	 */
	public void setVenueLevelId(Integer venueLevelId) {
		this.venueLevelId = venueLevelId;
	}

	/**
	 * @return the rowCode
	 */
	public Character getRowCode() {
		return rowCode;
	}

	/**
	 * @param rowCode the rowCode to set
	 */
	public void setRowCode(Character rowCode) {
		this.rowCode = rowCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {		
		return "[ID=" + this.id + ",CODE=" + this.getCode() + ",STATUS=" + this.getStatus() + ",ROWCODE=" + this.getRowCode() + ",VENUELEVELID=" + this.getVenueLevelId()+ "]";
	}
}
