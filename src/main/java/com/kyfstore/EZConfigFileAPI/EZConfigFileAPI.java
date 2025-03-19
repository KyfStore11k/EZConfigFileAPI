package com.kyfstore.EZConfigFileAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides the core functionality for the EZConfigFileAPI.
 * It contains constants and logging utilities to support the operation of the API.
 *
 * <p>The class defines a static logger instance for logging purposes and a constant identifier for the API.</p>
 *
 * <p>Usage:</p>
 * <pre>
 *     EZConfigFileAPI.LOGGER.info("Logging information from EZConfigFileAPI.");
 * </pre>
 *
 * <p>Constants:</p>
 * <ul>
 *     <li><strong>IDENTIFER</strong> - The identifier string for the API ("ezconfigfileapi").</li>
 *     <li><strong>LOGGER</strong> - The logger instance used for logging messages.</li>
 * </ul>
 */
public class EZConfigFileAPI {

    /**
     * The identifier string for the EZConfigFileAPI.
     * This is used to tag logs and uniquely identify the API in log files.
     */
    public final static String IDENTIFER = "ezconfigfileapi";

    /**
     * The logger instance used for logging purposes.
     * It uses SLF4J for logging messages related to the EZConfigFileAPI.
     * <p>This logger can be used to log info, debug, error, and other levels of logs.</p>
     */
    public final static Logger LOGGER = LoggerFactory.getLogger(IDENTIFER);
}
