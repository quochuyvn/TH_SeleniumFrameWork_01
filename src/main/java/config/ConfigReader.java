package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    /*
     * ======================
     * CORE GETTERS
     * ======================
     */

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        return (systemValue != null && !systemValue.isBlank())
                ? systemValue
                : properties.getProperty(key);
    }

    public static String getRequired(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing required config key: " + key);
        }
        return value;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /*
     * ======================
     * ENV URL
     * ======================
     */

    public static String getBaseUrl() {
        String env = getRequired(ConfigKeys.ENV);

        return switch (env.toLowerCase()) {
            case "dev" -> getRequired(ConfigKeys.BASE_URL_DEV);
            case "staging" -> getRequired(ConfigKeys.BASE_URL_STAGING);
            case "prod" -> getRequired(ConfigKeys.BASE_URL_PROD);
            default -> throw new RuntimeException("Unsupported env: " + env);
        };
    }
}
