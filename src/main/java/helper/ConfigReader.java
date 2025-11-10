package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties config = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            config.load(fis);
            fis.close();
        } catch (IOException e) {
        }
    }

    // Hàm tiện để lấy giá trị theo key
    public static String get(String key) {
        return config.getProperty(key);
    }

    // Hàm tiện để lấy giá trị double (vì nhiều giá trị là số thực)
    public static double getDouble(String key) {
        return Double.parseDouble(config.getProperty(key));
    }

    // Hàm tiện để lấy giá trị int
    public static int getInt(String key) {
        return Integer.parseInt(config.getProperty(key));
    }
}
