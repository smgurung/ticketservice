package com.walmart.ticketservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that reads the config.properties file and obtains the properties
 * defined in the file.
 * 
 * @author sgurung
 *
 */
public class ConfigLookUp {
	
	private static Logger logger = LogManager.getLogger(ConfigLookUp.class);

	private Properties configProperties = null;
	 
	private static ConfigLookUp instance;

    private static final String CONFIG_PROPERTIES_FILE = "/config.properties";
    
    public static final String SEAT_HOLD_EXPIRATION_TIME = "seatHoldExpirationTime";

    /**
     * Return the only instance of ConfigLookUp 
     * 
     * @return instance
     */
    public static synchronized ConfigLookUp getInstance() {
        if (instance == null) {
            try {
                instance = new ConfigLookUp();
            } catch (IOException e) {
                logger.error(
                        "Unable to create an instace of ConfigLookUp",
                        e);
            }
        }
        return instance;
    }   

    /**
     * If file has been modified, then reload it again
     * 
     * @return
     * @return
     */
    public static void ReloadInstance() {
        instance = null;
    }

    /**
     * Private Constructor
     * 
     * @throws IOException
     */
    private ConfigLookUp() throws IOException {
        initConfigProperties();
    }
    
    /**
     * Read the properties file and initialize the configProties
     * 
     * @throws IOException
     */
    private void initConfigProperties() throws IOException {
      
        InputStream is = ConfigLookUp.class.getResourceAsStream(CONFIG_PROPERTIES_FILE);
         
        // initialize using the configProperties
        this.configProperties = PropertiesUtil.read(is);
      
    }

	/**
	 * @return the configProperties
	 */
	public Properties getConfigProperties() {
		return configProperties;
	}
   

}
