package com.epam.jwd.exception;



public class CouldNotInitializeConnectionPoolError extends Error {

    private static final long serialVersionUID = 8553014030613840383L;

    public CouldNotInitializeConnectionPoolError(String message, Throwable cause) {
        super(message, cause);
    }
}



