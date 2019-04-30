package com.loujie.www.util.properties;

import java.io.IOException;
import java.util.Properties;

/**
 * 对properties配置文件的加载
 *
 * @author loujie
 */
public class PropertiesUtils {

    // 配置文件的名称,是可以配置更改的
    static String DEFAULT_CONFIG_NAME = "config.properties";

    // 采用的了内部静态了,可以实现懒加载
    static class Config {
        static final Properties config = new Properties();

        static {
            reload();
        }

        // 重新加载
        static void reload() {
            try {
                config.clear();
                config.load(PropertiesUtils.class.getResourceAsStream("/" + DEFAULT_CONFIG_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取key的value
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        return Config.config.getProperty(key, defaultValue);
    }

    /**
     * 获取key的value
     *
     * @param key 键
     * @return
     */
    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * 设置key的value
     *
     * @param key
     * @param value
     * @return
     */
    public static void setProperty(String key, String value) {
        Config.config.setProperty(key, value);
    }

    /**
     * 重新设置configName
     *
     * @param configName
     */
    public static void setConfigName(String configName) {
        DEFAULT_CONFIG_NAME = configName;
    }

    /**
     * 重新加载配置文件到内存中
     */
    public static void reload() {
        Config.reload();
    }
}
