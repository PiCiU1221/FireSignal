package firesignal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final String PROPERTIES_FILE_PATH = "config/config.properties";

    public static String getProperty(String propertyName) {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
