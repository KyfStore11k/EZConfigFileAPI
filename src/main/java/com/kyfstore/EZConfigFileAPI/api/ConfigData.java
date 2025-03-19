package com.kyfstore.EZConfigFileAPI.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * ConfigData is a class responsible for managing a YAML configuration file.
 * It handles loading, saving, reloading, and modifying values in the configuration file.
 */
public class ConfigData {

    private final File configFile;
    private FileConfiguration config;

    /**
     * Constructor that initializes the ConfigData.
     *
     * @param dataFolder The plugin's data folder where the config file is stored.
     * @param configFileName The name of the configuration file to manage.
     */
    public ConfigData(File dataFolder, String configFileName) {
        this.configFile = new File(dataFolder, configFileName);
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Gets the current configuration.
     *
     * @return The FileConfiguration object that represents the loaded config.
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Loads the configuration from the file.
     * If the file doesn't exist, it will create a new one using default values.
     */
    public void loadConfig() {
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        } else {
            // If file doesn't exist, save the default configuration
            saveDefaultConfig();
        }
    }

    /**
     * Saves the current configuration to the file.
     */
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the default configuration if the file doesn't exist.
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
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Sets a value in the configuration.
     *
     * @param path The path to the value in the config.
     * @param value The value to set.
     */
    public void set(String path, Object value) {
        config.set(path, value);
    }

    /**
     * Gets a value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The value at the specified path.
     */
    public Object get(String path) {
        return config.get(path);
    }

    /**
     * Gets a string value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The string value at the specified path.
     */
    public String getString(String path) {
        return config.getString(path);
    }

    /**
     * Gets an integer value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The integer value at the specified path.
     */
    public int getInt(String path) {
        return config.getInt(path);
    }

    /**
     * Gets a boolean value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The boolean value at the specified path.
     */
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }
}
