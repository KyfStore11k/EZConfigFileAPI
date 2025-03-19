package com.kyfstore.EZConfigFileAPI.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Properties;

public class ConfigData {

    private final File configFile;
    private FileConfiguration config;
    private Properties properties;
    private String header;

    /**
     * Constructor that initializes the ConfigData.
     *
     * @param dataFolder      The plugin's data folder where the config file is stored.
     * @param configFileName  The name of the configuration file to manage.
     */
    public ConfigData(File dataFolder, String configFileName) {
        this.configFile = new File(dataFolder, configFileName);
        this.header = "";
    }

    /**
     * Gets the current configuration.
     *
     * @return The FileConfiguration object that represents the loaded config.
     */
    public FileConfiguration getConfig() {
        if (configFile.getName().endsWith(".yml")) {
            return config;
        }
        return null;
    }

    /**
     * Loads the configuration file based on the file type.
     * If the file doesn't exist, it will create a new one.
     */
    public void loadConfig() {
        try {
            if (configFile.getName().endsWith(".yml")) {
                config = YamlConfiguration.loadConfiguration(configFile);
            } else if (configFile.getName().endsWith(".properties")) {
                properties = new Properties();
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    properties.load(fis);
                }
            } else if (configFile.getName().endsWith(".conf")) {
                properties = new Properties();
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    properties.load(fis);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the configuration to the file.
     */
    public void saveConfig() {
        try {
            if (configFile.getName().endsWith(".yml")) {
                if (header != null && !header.isEmpty()) {
                    config.options().header(header);  // Add the custom header comment
                }
                config.save(configFile);
            } else if (configFile.getName().endsWith(".properties")) {
                try (FileOutputStream fos = new FileOutputStream(configFile)) {
                    if (header != null && !header.isEmpty()) {
                        properties.store(fos, header);  // Add the header comment for properties file
                    } else {
                        properties.store(fos, null);  // No header if empty
                    }
                }
            } else if (configFile.getName().endsWith(".conf")) {
                try (FileOutputStream fos = new FileOutputStream(configFile)) {
                    if (header != null && !header.isEmpty()) {
                        properties.store(fos, header);  // Add the header comment for conf file
                    } else {
                        properties.store(fos, null);  // No header if empty
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the default configuration (if the file doesn't already exist).
     * It uses the resources from the plugin JAR.
     */
    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
            plugin.saveResource(configFile.getName(), false);
        }
    }

    /**
     * Reloads the configuration from the file.
     */
    public void reloadConfig() {
        loadConfig();
    }

    /**
     * Sets a value in the configuration.
     *
     * @param path   The path to the value in the config.
     * @param value  The value to set.
     */
    public void set(String path, Object value) {
        if (configFile.getName().endsWith(".yml")) {
            config.set(path, value);
        } else if (configFile.getName().endsWith(".properties")) {
            properties.setProperty(path, value.toString());
        } else if (configFile.getName().endsWith(".conf")) {
            properties.setProperty(path, value.toString());
        }
    }

    /**
     * Gets a value from the configuration.
     *
     * @param path   The path to the value in the config.
     * @return       The value at the specified path.
     */
    public Object get(String path) {
        if (configFile.getName().endsWith(".yml")) {
            return config.get(path);
        } else if (configFile.getName().endsWith(".properties")) {
            return properties.getProperty(path);
        } else if (configFile.getName().endsWith(".conf")) {
            return properties.getProperty(path);
        }
        return null;
    }

    /**
     * Gets a string value from the configuration.
     *
     * @param path   The path to the value in the config.
     * @return       The string value at the specified path.
     */
    public String getString(String path) {
        if (configFile.getName().endsWith(".yml")) {
            return config.getString(path);
        } else if (configFile.getName().endsWith(".properties")) {
            return properties.getProperty(path);
        } else if (configFile.getName().endsWith(".conf")) {
            return properties.getProperty(path);
        }
        return null;
    }

    /**
     * Gets an integer value from the configuration.
     *
     * @param path   The path to the value in the config.
     * @return       The integer value at the specified path.
     */
    public int getInt(String path) {
        if (configFile.getName().endsWith(".yml")) {
            return config.getInt(path);
        } else if (configFile.getName().endsWith(".properties")) {
            return Integer.parseInt(properties.getProperty(path, "0"));
        } else if (configFile.getName().endsWith(".conf")) {
            return Integer.parseInt(properties.getProperty(path, "0"));
        }
        return 0;
    }

    /**
     * Gets a boolean value from the configuration.
     *
     * @param path   The path to the value in the config.
     * @return       The boolean value at the specified path.
     */
    public boolean getBoolean(String path) {
        if (configFile.getName().endsWith(".yml")) {
            return config.getBoolean(path);
        } else if (configFile.getName().endsWith(".properties")) {
            return Boolean.parseBoolean(properties.getProperty(path, "false"));
        } else if (configFile.getName().endsWith(".conf")) {
            return Boolean.parseBoolean(properties.getProperty(path, "false"));
        }
        return false;
    }

    /**
     * Sets a custom header comment for the configuration file.
     *
     * @param header The custom header text.
     */
    public void setHeader(String header) {
        this.header = header;
    }
}
