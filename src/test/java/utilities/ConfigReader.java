package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Cannot load config.properties", e);
        }
    }

    /**
     * Ưu tiên System Property (-D), nếu không có thì lấy từ config.properties
     */
    public static String get(String key, String defaultValue) {
        return System.getProperty(key,
                properties.getProperty(key, defaultValue));
    }
}

