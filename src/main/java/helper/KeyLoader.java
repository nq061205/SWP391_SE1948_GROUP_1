package helper;

import java.io.InputStream;
import java.util.Properties;

public class KeyLoader {

    private static Properties props = new Properties();

    static {
        try (InputStream input = KeyLoader.class.getClassLoader()
                .getResourceAsStream("google_key.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                System.out.println("Không tìm thấy file googlekey.properties");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static double getDouble(String key) {
        return Double.parseDouble(props.getProperty(key, "0"));
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key, "0"));
    }
}
