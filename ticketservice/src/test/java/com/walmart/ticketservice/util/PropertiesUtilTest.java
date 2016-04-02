package com.walmart.ticketservice.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sgurung
 *
 */
public class PropertiesUtilTest {

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
	 * Test method for {@link com.walmart.ticketservice.util.PropertiesUtil#read(java.io.InputStream)}.
	 * @throws IOException 
	 */
	@Test
	public final void testReadInputStream() throws IOException {
		InputStream stubInputStream = IOUtils.toInputStream("seatHoldExpirationTime=300");
		assertNotNull(PropertiesUtil.read(stubInputStream));
	}

}
