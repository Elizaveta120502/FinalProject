package com.epam.jwd.exception;

import com.epam.jwd.logger.LoggerProvider;

public class NotSuchElementException {


    public NotSuchElementException(String message) {
        LoggerProvider.getLOG().error("No such element in database");
    }
}
