package com.kyfstore.EZConfigFileAPI.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles configuration file loading, saving, hot reloading, and ensuring file existence.
 */
public class ConfigHandler {
    private final Plugin plugin;
    private File configFile;
    private FileConfiguration config;
    private boolean hotReloadingEnabled = false;
    private ExecutorService fileWatcherExecutor;
    private WatchService watchService;

    /**
     * Creates a new ConfigHandler for the specified configuration file.
     *
     * @param plugin The plugin instance.
     * @param fileName The name of the configuration file (e.g., "config.yml").
     */
    public ConfigHandler(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        ensureConfigExists(); // Ensure config file exists before proceeding
        loadConfig();
    }

    /**
     * Ensures the configuration file exists. If it does not, it will create a new one.
     */
    private void ensureConfigExists() {
        if (!configFile.exists()) {
            try {
                plugin.getLogger().info("Config file not found, creating: " + configFile.getName());
                plugin.saveResource(configFile.getName(), false);
            } catch (IllegalArgumentException e) {
                try {
                    configFile.getParentFile().mkdirs(); // Ensure the directory exists
                    configFile.createNewFile(); // Create an empty file
                } catch (IOException ioException) {
                    plugin.getLogger().severe("Failed to create config file: " + ioException.getMessage());
                }
            }
        }
    }

    /**
     * Loads the configuration file.
     */
    public void loadConfig() {
        ensureConfigExists();
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Saves the configuration file.
     */
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config file: " + e.getMessage());
        }
    }

    /**
     * Reloads the configuration file.
     */
    public void reloadConfig() {
        loadConfig();
        plugin.getLogger().info("Configuration reloaded successfully.");
    }

    /**
     * Enables hot reloading of the config file.
     */
    public void enableHotReloading() {
        if (hotReloadingEnabled) return;

        hotReloadingEnabled = true;
        fileWatcherExecutor = Executors.newSingleThreadExecutor();

        fileWatcherExecutor.execute(() -> {
            try {
                watchService = FileSystems.getDefault().newWatchService();
                Path configDir = configFile.getParentFile().toPath();
                configDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (hotReloadingEnabled) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        Path changed = (Path) event.context();

                        if (kind == StandardWatchEventKinds.ENTRY_MODIFY && changed.toString().equals(configFile.getName())) {
                            plugin.getLogger().info("Config file changed, reloading...");
                            reloadConfig();
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                plugin.getLogger().severe("Error in file watcher: " + e.getMessage());
            }
        });

        plugin.getLogger().info("Hot reloading enabled for " + configFile.getName());
    }

    /**
     * Disables hot reloading of the config file.
     */
    public void disableHotReloading() {
        hotReloadingEnabled = false;
        if (fileWatcherExecutor != null) {
            fileWatcherExecutor.shutdownNow();
        }
        try {
            if (watchService != null) {
                watchService.close();
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error closing file watcher: " + e.getMessage());
        }
        plugin.getLogger().info("Hot reloading disabled.");
    }

    /**
     * Gets the configuration file.
     *
     * @return The FileConfiguration object.
     */
    public FileConfiguration getConfig() {
        ensureConfigExists(); // Ensure the file exists before returning it
        return config;
    }

    /**
     * Sets a new file for the configuration handler.
     *
     * @param fileName The new file name (e.g., "custom.yml").
     */
    public void setFile(String fileName) {
        disableHotReloading(); // Stop watching previous file
        this.configFile = new File(plugin.getDataFolder(), fileName);
        ensureConfigExists(); // Ensure new file exists before using it
        loadConfig();
        enableHotReloading(); // Start watching new file
    }

    /**
     * Sets a custom header at the top of the configuration file.
     *
     * @param header The header text to be written as a comment.
     */
    public void setHeader(String header) {
        String[] lines = header.split("\n");
        StringBuilder comment = new StringBuilder();
        for (String line : lines) {
            comment.append("# ").append(line).append("\n");
        }
        config.options().header(comment.toString());
        saveConfig();
    }
}
