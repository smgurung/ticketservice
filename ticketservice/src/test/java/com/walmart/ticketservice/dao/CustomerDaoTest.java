package com.walmart.ticketservice.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.walmart.ticketservice.datastore.Customer;

/**
 * @author sgurung
 *
 */
public class CustomerDaoTest {
	
	private CustomerDao customerDao = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		customerDao = new CustomerDao();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.CustomerDao#saveCustomer(com.walmart.ticketservice.datastore.Customer)}.
	 */
	@Test
	public final void testSaveCustomer() {
		assertTrue(customerDao.getCustomers().size() == 0);		
		Customer customer = new Customer("shova@test.com");		
		customerDao.saveCustomer(customer);
		assertTrue(customerDao.getCustomers().size() == 1);
		
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.CustomerDao#getCustomerByEmail(java.lang.String)}.
	 */
	@Test
	public final void testGetCustomerByEmail() {
		Customer customer = new Customer("shova@test.com");		
		customerDao.saveCustomer(customer);
		assertNotNull(customerDao.getCustomerByEmail("shova@test.com"));
	}

	/**
	 * Test method for {@link com.walmart.ticketservice.dao.CustomerDao#getCustomers()}.
	 */
	@Test
	public final void testGetCustomers() {
		Customer customer = new Customer("shova@test.com");		
		customerDao.saveCustomer(customer);
		assertTrue(customerDao.getCustomers().size() > 0);
	}

}
