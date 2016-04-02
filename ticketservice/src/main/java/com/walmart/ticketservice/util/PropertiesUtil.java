package com.walmart.ticketservice.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * Utility class to work with {@link Properties}.
 * 
 * @author sgurung
 *
 */

public final class PropertiesUtil {

    /**
     * Prevent instantiation.
     */
    private PropertiesUtil() {
    }

    /**
     * Read an input stream to load a {@link Properties} instance.
     * 
     * @param is
     *            the input stream
     * @return the Properties
     * @throws IOException
     *             on error reading the {@link InputStream}
     */
    public static Properties read(InputStream is) throws IOException {
        Reader reader = null;
        try {
            reader = new InputStreamReader(is);

            Properties properties = new Properties();
            properties.load(reader);

            return properties;
        } finally {
            close(reader);
        }
    }

    /**
     * Read a {@link File} to load a {@link Properties} instance.
     * 
     * @param file
     *            the file to read
     * @return the Properties
     * @throws IOException
     *             on error reading the {@link File}
     */
    public static Properties read(File file) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return read(is);
        } finally {
            close(is);
        }
    }

    /**
     * Close a Closeable instance
     * 
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Nothing to do, closing...
            }
        }
    }
}




