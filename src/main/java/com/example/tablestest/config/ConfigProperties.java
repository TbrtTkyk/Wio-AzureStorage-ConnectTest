package com.example.tablestest.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//config.propertiesの値を取得するクラス
public class ConfigProperties {
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            InputStream propertyStream = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties");
            if (propertyStream != null) {
                prop.load(propertyStream);
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("\nFailed to load config.properties file.");
            throw new RuntimeException();
        }
    }

    public static String getStorageConnectionString() {
        if (prop == null) {
            throw new RuntimeException();
        }
        return prop.getProperty("StorageConnectionString");
    }

    public static String getWioAccessTokenTemperature() {
        if (prop == null) {
            throw new RuntimeException();
        }
        return prop.getProperty("WioAccessTokenTemperature");
    }
}
