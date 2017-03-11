package com.liashenko.departments.services.dbService;

import org.apache.logging.log4j.LogManager;

/**
 * Represents Exceptions thrown by the Data Access Layer.
 */
public class DataAccessLayerException extends RuntimeException {
    private static final org.apache.logging.log4j.Logger rootLogger = LogManager.getRootLogger();

    public DataAccessLayerException() {
    }

    public DataAccessLayerException(String message) {
        super(message);
    }

    public DataAccessLayerException(Throwable cause) {
        super(cause);
        rootLogger.trace("DataAccessLayerException: ", cause);
    }

    public DataAccessLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
