package configurator;

import java.util.Properties;

/**
 * Use to set of connection data from environmental variables.
 * File must contain those fields: CONNECT_URL, CONNECT_USER, CONNECT_PASSWORD.
 * @author Tesorp1X
 */
public class Configurator {

    private final String CONNECTION_URL;

    private final String CONNECTION_USER;

    private final String CONNECTION_PASSWORD;


    /**
     * ENVs should be CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD.
     */
    public Configurator() throws ConfiguratorException {

        Properties props = new Properties();

        try {

            CONNECTION_URL = System.getenv("CONNECTION_URL");

            CONNECTION_USER = System.getenv("CONNECTION_USER");

            CONNECTION_PASSWORD = System.getenv("CONNECTION_PASSWORD");


        } catch (IllegalArgumentException | NullPointerException e) {

            System.err.println("ERROR OCCURRED : " + e.getMessage());
            e.printStackTrace();
            throw new ConfiguratorException(e);
        }


    }

    //Getters

    public String getUrl() {

        return CONNECTION_URL;
    }

    public String getUser() {

        return CONNECTION_USER;
    }

    public String getPassword() {

        return CONNECTION_PASSWORD;
    }

}