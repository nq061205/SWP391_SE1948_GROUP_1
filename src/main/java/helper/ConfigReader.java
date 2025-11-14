package helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties config = new Properties();

    static {
        try {
            InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");

            if (is != null) {
                config.load(is);
                is.close();
                config.forEach((key, value)
                        -> System.out.println("  " + key + " = " + value)
                );

            } else {
                System.err.println("ERROR: config.properties not found in classpath!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        String value = config.getProperty(key);
        if (value == null) {
            System.err.println("WARNING: Key '" + key + "' not found in config.properties");
        }
        return value;
    }

    public static double getDouble(String key) {
        String value = config.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            System.err.println("ERROR: Key '" + key + "' not found or empty in config.properties");
            throw new RuntimeException("Config key '" + key + "' not found");
        }

        try {
            double result = Double.parseDouble(value.trim());
            System.out.println("Reading config: " + key + " = " + result);
            return result;
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Cannot parse '" + value + "' as double for key '" + key + "'");
            throw e;
        }
    }

    public static int getInt(String key) {
        String value = config.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            System.err.println("ERROR: Key '" + key + "' not found or empty in config.properties");
            throw new RuntimeException("Config key '" + key + "' not found");
        }

        try {
            int result = Integer.parseInt(value.trim());
            System.out.println("Reading config: " + key + " = " + result);
            return result;
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Cannot parse '" + value + "' as int for key '" + key + "'");
            throw e;
        }
    }
}
