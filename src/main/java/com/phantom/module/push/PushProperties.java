package com.phantom.module.push;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author 张志凯 https://github.com/Law-God/phantom-module-push
 * phantom-module-push
 * com.phantom.module.push.PushProperties
 * 2016-12-22 14:50
 */
public class PushProperties {

    // ------------------------------------------------------- Static Variables

    private static final Log log = LogFactory.getLog(PushProperties.class);

    private static Properties properties = null;


    static {
        loadProperties();
    }


    // --------------------------------------------------------- Public Methods
    /**
     * Return specified property value.
     */
    public static String getProperty(String name) {

        return properties.getProperty(name);

    }

    /**
     * Return specified property value.
     *
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public static String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    // --------------------------------------------------------- Public Methods
    /**
     * Load properties.
     */
    private static void loadProperties() {
        InputStream is = null;
        try {
            is = PushProperties.class.getResourceAsStream
                    ("/com/phantom/module/push/push.properties");
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (is != null) {
            try {
                properties = new Properties();
                properties.load(is);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            finally
            {
                try {
                    is.close();
                } catch (IOException ioe) {
                    log.warn("Could not close catalina.properties", ioe);
                }
            }
        }

        if (is == null) {
            // Do something
            log.error("Failed to load push.properties");
            // That's fine - we have reasonable defaults.
            properties=new Properties();
        }
    }

}
