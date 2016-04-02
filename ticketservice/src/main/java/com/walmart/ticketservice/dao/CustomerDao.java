package com.walmart.ticketservice.dao;

import java.util.HashMap;
import java.util.Map;

import com.walmart.ticketservice.datastore.Customer;

/**
 * DAO class for Customer data
 * 
 * @author sgurung
 *
 */
public class CustomerDao {
	
	/**
	 * Map containing customer data with email as the key and 
	 * Customer object as the value.
	 * 
	 */
	private Map<String, Customer> customers = null;

	/**
	 * Constructs a CustomerDao
	 */
	public CustomerDao() {
		customers = new HashMap<String, Customer>();
	}
	
	/**
	 * Add a new customer
	 * 
	 * @param customer
	 */
	public void saveCustomer(Customer customer) {
		
		if (null!= customer) {
			customers.put(customer.getEmail(), customer);
		}
	}
	
	/**
	 * Return a customer with a given email
	 * 
	 * @param email
	 * @return a Customer object with the given email or null
	 */
	public Customer getCustomerByEmail(String email) {
		
		if (null != email && customers.containsKey(email)) {
			return customers.get(email);
		} else {
			return null;
		}
	}

	/**
	 * @return the customers
	 */
	public Map<String, Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(Map<String, Customer> customers) {
		this.customers = customers;
	}
	
	

}
