package com.todo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyManager.class);

    public static final String GLOBAL_PROPERTIES_FILE = "todo.properties";

    private static volatile PropertyManager instance;

    private final Properties globalProperties;

    public static PropertyManager getInstance() {
        PropertyManager result = instance;
        if (result == null) {
            synchronized (PropertyManager.class) {
                result = instance;
                if (result == null) {
                    try {
                        result = instance = new PropertyManager(loadConfigFile(GLOBAL_PROPERTIES_FILE));
                    } catch (Exception e) {
                        LOGGER.error("Cannot load PropertyManager ", e);
                        throw new IllegalStateException("Cannot load PropertyManager ", e);
                    }
                }
            }
        }
        return result;
    }

    public PropertyManager(Properties properties) {
        globalProperties = properties;
    }

    public String getProperty(String key) {
        String property = globalProperties.getProperty(key);
        if (property == null) {
            throw new IllegalStateException("Undefined global property : " + key);
        }
        return property;
    }

    private static Properties loadConfigFile(String globalPropertiesFile) throws IOException {
        try (InputStream is = loadPropertyFileAsStream(globalPropertiesFile)) {
            Properties globalProperties = new Properties();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Loading todo properties file from {}", globalPropertiesFile);
            }
            globalProperties.load(is);
            return globalProperties;
        }
    }

    private static InputStream loadPropertyFileAsStream(String globalPropertiesFile) {
        InputStream is = PropertyManager.class.getClassLoader().getResourceAsStream(globalPropertiesFile);
        return Objects.requireNonNull(is, "Can't getResourceAsStream : " + globalPropertiesFile);
    }
}
