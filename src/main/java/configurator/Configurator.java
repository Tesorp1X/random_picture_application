package configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configurator {

    private String CONNECT_URL;

    private String CONNECT_USER;

    private String CONNECT_PASSWORD;


    public Configurator(String pathname) {

        Properties props = new Properties();

        try {

            props.load(new FileInputStream(new File(pathname)));

            CONNECT_URL = props.getProperty("CONNECT_URL");

            CONNECT_USER = props.getProperty("CONNECT_USER");

            CONNECT_PASSWORD = props.getProperty("CONNECT_PASSWORD");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public String getUrl() {

        return CONNECT_URL;
    }

    public String getUser() {

        return CONNECT_USER;
    }

    public String getPassword() {

        return CONNECT_PASSWORD;
    }

}
