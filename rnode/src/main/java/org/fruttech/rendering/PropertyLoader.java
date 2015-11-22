package org.fruttech.rendering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

public class PropertyLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    public static Properties loadProperties(String propertiesFilePath) {
        Properties properties = new Properties();
        loadProperties(properties, propertiesFilePath);

        return properties;
    }

    public static void loadProperties(Properties properties, String propertiesFilePath) {
        logger.info("Loading properties: {}", propertiesFilePath);
        ClassLoader loader = PropertyLoader.class.getClassLoader();
        URL url = loader.getResource(propertiesFilePath);
        try {
            assert url != null;
            properties.load(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Object key : properties.keySet()) {
            logger.info(String.format("  %s = %s", key, properties.get(key)));
        }
    }

    public static ResourceBundle getBundle(String baseName, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        return createUtf8PropertyResourceBundle(bundle);
    }

    private static ResourceBundle createUtf8PropertyResourceBundle(ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }

        return new Utf8PropertyResourceBundle((PropertyResourceBundle)bundle);
    }

    private static class Utf8PropertyResourceBundle extends ResourceBundle {
        PropertyResourceBundle bundle;

        private Utf8PropertyResourceBundle(PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        /* (non-Javadoc)
        * @see java.util.ResourceBundle#getKeys()
        */
        public Enumeration<String> getKeys() {
            return bundle.getKeys();
        }

        /* (non-Javadoc)
        * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
        */
        protected Object handleGetObject(String key) {
            String value = bundle.getString(key);
            if (value == null) {
                return null;
            }
            try {
                return new String(value.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }

}
