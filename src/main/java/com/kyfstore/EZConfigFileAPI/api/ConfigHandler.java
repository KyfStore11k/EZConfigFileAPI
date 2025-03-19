package com.kyfstore.EZConfigFileAPI.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ConfigHandler is a utility class for managing YAML configuration files in Paper plugins.
 * It provides methods to load, save, get, set, and modify configuration values.
 */
public class ConfigHandler {

    private final JavaPlugin plugin;
    private final String configFileName;
    private ConfigData configData;

    /**
     * Constructor that initializes the ConfigHandler.
     *
     * @param plugin The main JavaPlugin instance.
     * @param configFileName The name of the configuration file to manage.
     */
    public ConfigHandler(JavaPlugin plugin, String configFileName) {
        this.plugin = plugin;
        this.configFileName = configFileName;
        this.configData = new ConfigData(plugin.getDataFolder(), configFileName);
    }

    /**
     * Gets the current configuration (FileConfiguration object).
     *
     * @return The current configuration.
     */
    public FileConfiguration getConfig() {
        return configData.getConfig();
    }

    /**
     * Loads the configuration file.
     * If the file does not exist, it will create a new one using default values.
     */
    public void loadConfig() {
        configData.loadConfig();
    }

    /**
     * Saves the current configuration to the file.
     */
    public void saveConfig() {
        configData.saveConfig();
    }

    /**
     * Saves the default configuration (if the file doesn't already exist).
     */
    public void saveDefaultConfig() {
        configData.saveDefaultConfig();
    }

    /**
     * Sets a value in the configuration.
     *
     * @param path The path to the value in the config.
     * @param value The value to set.
     */
    public void set(String path, Object value) {
        configData.set(path, value);
        saveConfig();  // Automatically saves after setting the value
    }

    /**
     * Gets a value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The value at the specified path.
     */
    public Object get(String path) {
        return configData.get(path);
    }

    /**
     * Gets a string value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The string value at the specified path.
     */
    public String getString(String path) {
        return configData.getString(path);
    }

    /**
     * Gets an integer value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The integer value at the specified path.
     */
    public int getInt(String path) {
        return configData.getInt(path);
    }

    /**
     * Gets a boolean value from the configuration.
     *
     * @param path The path to the value in the config.
     * @return The boolean value at the specified path.
     */
    public boolean getBoolean(String path) {
        return configData.getBoolean(path);
    }

    /**
     * Reloads the configuration from the file.
     */
    public void reloadConfig() {
        configData.reloadConfig();
    }
}
